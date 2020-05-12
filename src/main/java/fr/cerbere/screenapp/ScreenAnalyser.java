package fr.cerbere.screenapp;

import com.google.gson.internal.$Gson$Preconditions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenAnalyser {

    private GraphicsDevice screen;

    private BufferedImage screenShot;

    private Robot robotForScreen;

    public ScreenAnalyser(GraphicsDevice screen) throws AWTException {
        this.screen = screen;
        robotForScreen = new Robot(screen);
    }

    public BufferedImage takeScreen() {
        BufferedImage screenShot = robotForScreen.createScreenCapture(screen.getDefaultConfiguration().getBounds());
        this.screenShot = screenShot;
        return screenShot;
    }

    /*
     * Where bi is your image, (x0,y0) is your upper left coordinate, and (w,h)
     * are your width and height respectively
     */
    public int[] fullShotAnalyse() {
        int[] ret = new int[3];
        int x1 = screenShot.getWidth();
        int y1 = screenShot.getHeight();
        long sumr = 0, sumg = 0, sumb = 0;
        for (int x = 0; x < x1; x++) {
            for (int y = 0; y < y1; y++) {
                Color pixel = new Color(screenShot.getRGB(x, y));
                sumr += pixel.getRed();
                sumg += pixel.getGreen();
                sumb += pixel.getBlue();
            }
        }
        int num = x1 * y1;
        ret[0] = (int) (sumr/num);
        ret[1] = (int) (sumg/num);
        ret[2] = (int) (sumb/num);
        return ret;
    }
}
