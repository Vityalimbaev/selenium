package models.browsers;

import org.openqa.selenium.firefox.FirefoxOptions;

public class Firefox extends Browser {

    public Firefox(String version, String platform) {
        super("Firefox", version, platform);
        permissions();
    }

    public Firefox(String version) {
        super("Firefox", version, "Windows 10");
        permissions();
    }

    private void permissions(){
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("media.navigator.permission.disabled", true);
        super.getCapabilities().setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
    }
}
