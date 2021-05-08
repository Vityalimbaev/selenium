package models.browsers;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class MobileChrome extends Browser{

    public MobileChrome(String version, String platform, String device, String platformVersion) {
        super("Chrome", version, platform);
        super.getCapabilities().setCapability("deviceName",device);
        super.getCapabilities().setCapability("platformVersion",platformVersion);
        super.getCapabilities().setCapability("platformName","Android");
        super.getCapabilities().setCapability("screenResolution", "");
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("w3c", false);
        super.getCapabilities().setCapability(ChromeOptions.CAPABILITY, options);
    }

}
