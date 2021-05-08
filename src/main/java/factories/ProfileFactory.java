package factories;

import models.profiles.Profile;

public abstract class ProfileFactory {
    public static Profile create() throws Exception {
        Profile profile;
        String mode = System.getProperty("mode");
        switch (mode.toLowerCase()){
            case "dev":
                profile = new Profile(
                        "dev",
                        "devuser",
                        "devpassword",
                        "dev123"
                );
                return profile;
            case "prod":
                profile = new Profile(
                        "prod",
                        "produser",
                        "prodpassword",
                        "prod123");
                return profile;
            default:
                throw new Exception("Profile '" +mode+ "' not found... Run with -Dmode=\"DEV\" or \"PROD\"");
        }
    }
}
