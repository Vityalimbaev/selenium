package models.browsers;

public class InternetExplorer extends Browser {

    public InternetExplorer(String version, String platform) {
        super("Internet Explorer", version, platform);
    }

    public InternetExplorer(String version) {
        super("Internet Explorer", version, "Windows 10");
    }
}
