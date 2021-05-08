package models.browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class Browser {
    private DesiredCapabilities capabilities = new DesiredCapabilities();
    public Browser(String browserName, String version, String platform){
        capabilities.setCapability("browserName", browserName);
        capabilities.setCapability("version", version);
        capabilities.setCapability("platform", platform);
        capabilities.setCapability("screenResolution", "1280x1024");
        capabilities.setCapability("record_video", "true");
    }

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    @Override
    public String toString() {
        return capabilities.toString();
    }
}
