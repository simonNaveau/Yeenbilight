import com.mollin.yapi.YeelightDevice;
import com.mollin.yapi.enumeration.YeelightEffect;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;

public class LightManager {

    private YeelightDevice lit;

    private YeelightDevice plafonier;

    private boolean litConnectionStatus = false;

    private boolean plafonierConnectionStatus = false;

    public LightManager() throws YeelightResultErrorException, YeelightSocketException {
        System.out.println("### Starting configuration ###");
        lit = new YeelightDevice("192.168.1.36");
        lit.setPower(true);
        lit.setEffect(YeelightEffect.SMOOTH);
        lit.setDuration(300);
        setBrightnessLit(10);
        System.out.println("Here in constructor");
        plafonier = new YeelightDevice("192.168.1.34");
        plafonier.setPower(true);
        plafonier.setEffect(YeelightEffect.SMOOTH);
        plafonier.setDuration(300);
        setBrightnessPlafonier(10);
        System.out.println("### Configuration ended successfully ###");
    }

    public void startConnectionLit() {
        System.out.println("### Starting connection Lit ###");
        try {
            lit.setMusic(1, "192.168.1.24", 54321);
            litConnectionStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("### Connection Lit ended successfully ###");
    }

    public void setBrightnessLit(int newValue) throws YeelightResultErrorException, YeelightSocketException {
        lit.setBrightness(newValue);
    }

    public void powerOnLit() throws YeelightResultErrorException, YeelightSocketException {
        lit.setPower(true);
    }

    public void powerOffLit() throws YeelightResultErrorException, YeelightSocketException {
        lit.setPower(false);
    }

    public void startConnectionPlafonier() {
        System.out.println("### Starting connection Plafonier ###");
        try {
            plafonier.setMusic(1, "192.168.1.24", 54322);
            plafonierConnectionStatus = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("### Connection Plafonier ended successfully ###");
    }

    public void setBrightnessPlafonier(int newValue) throws YeelightResultErrorException, YeelightSocketException {
        plafonier.setBrightness(newValue);
    }

    public void powerOnPlafonier() throws YeelightResultErrorException, YeelightSocketException {
        plafonier.setPower(true);
    }

    public void powerOffPlafonier() throws YeelightResultErrorException, YeelightSocketException {
        plafonier.setPower(false);
    }
}
