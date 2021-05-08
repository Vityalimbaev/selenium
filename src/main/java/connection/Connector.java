package connection;

import models.browsers.Browser;
import models.profiles.Credentials;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Connector {

    private static String username = Credentials.getUSERNAME();
    private static String authkey = Credentials.getAUTHKEY();
    private RemoteWebDriver DRIVER;

    public Connector(Browser browser, Boolean isRemote) throws MalformedURLException {
        if (isRemote) {
            DRIVER = new RemoteWebDriver(new URL(
                    "http://" + username + ":" +
                            authkey + "@hub.crossbrowsertesting.com:80/wd/hub"), browser.getCapabilities());
        } else {
            DRIVER = new SafariDriver();
        }
        if(browser.getCapabilities().getPlatform() == null || (!browser.getCapabilities().getPlatform().toString().toLowerCase().equals("android") && !browser.getCapabilities().getPlatform().toString().toLowerCase().equals("ios"))) {
            DRIVER.manage().window().setSize(new Dimension(1280, 1024));
            DRIVER.manage().window().setPosition(new Point(0, 0));
        }
    }

    public RemoteWebDriver getDRIVER() {
        return DRIVER;
    }

}
