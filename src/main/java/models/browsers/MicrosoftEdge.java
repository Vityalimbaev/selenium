package models.browsers;

public class MicrosoftEdge extends Browser {

    public MicrosoftEdge(String version, String platform) {
        super("MicrosoftEdge", version, platform);
    }

    public MicrosoftEdge(String version) {
        super("MicrosoftEdge", version, "Windows 10");
    }
}
