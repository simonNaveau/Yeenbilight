package com.mollin.yapi;

import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.exception.YeelightSocketException;

public class TestingPerso {

    public static void main(String[] args) throws YeelightSocketException, YeelightResultErrorException {
        // Instantiate your device (with its IP)
        YeelightDevice device = new YeelightDevice("192.168.1.34");
        // Switch on the device
        device.setPower(true);
        // Change device color
        device.setRGB(140, 142, 155);
        // Change device brightness
        device.setBrightness(85);
    }
}
