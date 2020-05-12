import com.mollin.yapi.YeelightDevice;
import com.mollin.yapi.enumeration.YeelightEffect;
import com.mollin.yapi.enumeration.YeelightProperty;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;
import fr.cerbere.screenapp.ScreenAnalyser;
import fr.cerbere.screenapp.Utils;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TestingPerso {

    public static void main(String[] args) throws Exception {

        System.out.println("### Starting configuration ###");
        YeelightDevice device = new YeelightDevice("192.168.1.34");
        device.setPower(true);
        device.setEffect(YeelightEffect.SMOOTH);
        device.setDuration(300);
        device.setBrightness(1);
        System.out.println("### Configuration ended successfully ###");
        System.out.println("### Starting connection ###");
        try{
            device.setMusic(1);
            System.out.println(device.getProperties().toString());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("### Connection ended successfully ###");
    }
}
