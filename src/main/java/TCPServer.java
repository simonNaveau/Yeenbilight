import com.mollin.yapi.command.YeelightCommand;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;
import fr.cerbere.screenapp.ScreenAnalyser;
import fr.cerbere.screenapp.Utils;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static com.mollin.yapi.utils.YeelightUtils.clampAndComputeRGBValue;

class TCPServer extends Thread {

    /**
     * Socket writer
     */
    private static  BufferedWriter socketWriterLit;

    private static  BufferedWriter socketWriterPlafonier;

    public static int[] color;

    public ScreenAnalyser monitorL;

    public ScreenAnalyser monitorR;

    public String screenToCheck;

    public TCPServer(String serverIp, int serveurPort, String screenToCheck) throws AWTException, IOException {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        monitorL = new ScreenAnalyser(screens[0]);
        monitorR = new ScreenAnalyser(screens[1]);

        InetAddress addr = InetAddress.getByName(serverIp);//Local PC address
        ServerSocket sockLit = new ServerSocket(serveurPort, 50, addr);
        System.out.println("The socket start at : "+sockLit.getInetAddress()+" : "+sockLit.getLocalPort());

        ServerSocket sockPlafonier = new ServerSocket(serveurPort+1, 50, addr);
        System.out.println("The socket start at : "+sockPlafonier.getInetAddress()+" : "+sockPlafonier.getLocalPort());

        Socket socketLit = sockLit.accept();
        //Socket socketPlafonier = sockPlafonier.accept();
        socketLit.setSoTimeout(1000);
        //socketPlafonier.setSoTimeout(1000);
        socketWriterLit = new BufferedWriter(new OutputStreamWriter(socketLit.getOutputStream()));
        //socketWriterPlafonier = new BufferedWriter(new OutputStreamWriter(socketPlafonier.getOutputStream()));
        this.screenToCheck = screenToCheck;
    }

    public void run() {
        int[] tmpColor;
        YeelightCommand command;
        String jsonCommand;

        if(screenToCheck.equals("left")){
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
        } else {
            while (true) {
                try {
                    monitorR.takeScreen();
                    tmpColor = monitorR.fullShotAnalyse();
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
    }

    /**
     * Send datas on the socket
     * @param datas Datas to send
     * @throws YeelightSocketException when socket error occurs
     */
    public void send(String datas) throws YeelightSocketException {
        try {
            socketWriterLit.write(datas);
            socketWriterLit.flush();
//            socketWriterPlafonier.write(datas);
//            socketWriterPlafonier.flush();
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
    public YeelightCommand setRGB(int r, int g, int b) throws YeelightResultErrorException, YeelightSocketException {
        int rgbValue = clampAndComputeRGBValue(r, g, b);
        return new YeelightCommand("set_rgb", rgbValue, "smooth", 500);
    }
}