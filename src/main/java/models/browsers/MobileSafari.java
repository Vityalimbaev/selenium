package models.browsers;

public class MobileSafari extends Browser{

    public MobileSafari(String version, String platform, String device, String platformVersion) {
        super("Safari", version, platform);
        super.getCapabilities().setCapability("deviceName",device);
        super.getCapabilities().setCapability("platformVersion",platformVersion);
        super.getCapabilities().setCapability("platformName","iOS");
        super.getCapabilities().setCapability("screenResolution", "");
    }
}
