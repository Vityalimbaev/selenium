package factories;

import models.browsers.*;

public abstract class BrowserFactory {

    public static Browser create(String browserName, String version, String platform) throws Exception {
        Browser browser;
        //switch platform?
        switch ((browserName.toLowerCase()).replaceAll(" ", "")) {
            case "chrome":
                browser = new Chrome(version, platform);
                return browser;
            case "firefox":
                browser = new Firefox(version, platform);
                return browser;
            case "internetexplorer":
                browser = new InternetExplorer(version, platform);
                return browser;
            case "safari":
                browser = new Safari(version, platform);
                return browser;
            case "microsoftedge":
                browser = new MicrosoftEdge(version, platform);
                return browser;
            default:
                throw new Exception("Browser settings not found...");
        }
    }

    public static Browser create(String browserName, String version, String platform, String device, String platformVersion) throws Exception {
        Browser browser;
        //switch platform?
        switch ((browserName.toLowerCase()).replaceAll(" ", "")) {
            case "chrome":
                browser = new MobileChrome(version, platform, device, platformVersion);
                return browser;
            case "safari":
                browser = new MobileSafari(version, platform, device, platformVersion);
                return browser;
            default:
                throw new Exception("Browser settings not found...");
        }
    }
}
