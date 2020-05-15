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
        YeelightDevice lit = new YeelightDevice("192.168.1.36");
        lit.setPower(true);
        lit.setEffect(YeelightEffect.SMOOTH);
        lit.setDuration(300);
        lit.setBrightness(1);

//        YeelightDevice plafonier = new YeelightDevice("192.168.1.34");
//        plafonier.setPower(true);
//        plafonier.setEffect(YeelightEffect.SMOOTH);
//        plafonier.setDuration(300);
//        plafonier.setBrightness(10);

        System.out.println("### Configuration ended successfully ###");
        System.out.println("### Starting connection ###");
        try{
            lit.setMusic(1, "192.168.1.24", 54321);
            //plafonier.setMusic(1, "192.168.1.24", 54322);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("### Connection ended successfully ###");
    }
}
