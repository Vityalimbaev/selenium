package models.browsers;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class Chrome extends Browser {

    public Chrome(String version, String platform) {
        super("Chrome", version, platform);
        permissions();
    }

    public Chrome(String version) {
        super("Chrome", version, "Windows 10");
        permissions();
    }

    private void permissions(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("allow-file-access-from-files");
        options.addArguments("use-fake-device-for-media-stream");
        options.addArguments("use-fake-ui-for-media-stream");
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", System.getProperty("java.io.tmpdir"));
        prefs.put("profile.default_content_setting_values.media_stream_mic", 1);
        prefs.put("profile.default_content_setting_values.media_stream_camera", 1);
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        prefs.put("profile.default_content_setting_values.notifications", 1);
        options.setExperimentalOption("prefs", prefs);
        super.getCapabilities().setCapability(ChromeOptions.CAPABILITY, options);
    }
}
