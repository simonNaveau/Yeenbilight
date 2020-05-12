import com.mollin.yapi.command.YeelightCommand;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;
import fr.cerbere.screenapp.ScreenAnalyser;
import fr.cerbere.screenapp.Utils;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static com.mollin.yapi.utils.YeelightUtils.clampAndComputeRGBValue;

class TCPServer {

    /**
     * Socket writer
     */
    private static  BufferedWriter socketWriter;

    public static int[] color;

    public static void main(String argv[]) throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        ScreenAnalyser monitorL = new ScreenAnalyser(screens[0]);
        //ScreenAnalyser monitorR = new ScreenAnalyser(screens[1]);


        InetAddress addr = InetAddress.getByName("192.168.1.24");//Local PC address
        ServerSocket sock = new ServerSocket(54321, 50, addr);
        System.out.println("The socket start at : "+sock.getInetAddress()+" : "+sock.getLocalPort());
        Socket socket = sock.accept();
        socket.setSoTimeout(1000);
        socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        int[] tmpColor;
        YeelightCommand command;
        String jsonCommand;

        while (true) {
            try {
                monitorL.takeScreen();
                tmpColor = monitorL.fullShotAnalyse();
                if (Utils.detectChange(color, tmpColor)){
                    command = setRGB(tmpColor[0], tmpColor[1], tmpColor[2]);
                    jsonCommand = command.toJson() + "\r\n";
                    send(jsonCommand);
                    color = tmpColor;
                    TimeUnit.MILLISECONDS.sleep(150);
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Send datas on the socket
     * @param datas Datas to send
     * @throws YeelightSocketException when socket error occurs
     */
    public static void send(String datas) throws YeelightSocketException {
        try {
            socketWriter.write(datas);
            socketWriter.flush();
        } catch (Exception e) {
            throw new YeelightSocketException(e);
        }
    }

    /**
     * Change the device color
     * @param r Red value [0;255]
     * @param g Green value [0;255]
     * @param b Blue value [0;255]
     * @throws YeelightResultErrorException when command result is an error
     * @throws YeelightSocketException when socket error occurs
     */
    public static YeelightCommand setRGB(int r, int g, int b) throws YeelightResultErrorException, YeelightSocketException {
        int rgbValue = clampAndComputeRGBValue(r, g, b);
        return new YeelightCommand("set_rgb", rgbValue, "smooth", 500);
    }
}