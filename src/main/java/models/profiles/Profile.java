package models.profiles;

public class Profile {
    private String site;
    private String username;
    private String doctor;
    private String password;

    public Profile(String site, String username, String doctor, String password) {
        this.site = site;
        this.username = username;
        this.doctor = doctor;
        this.password = password;
    }

    public String getSite() {
        return site;
    }

    public String getUsername() {
        return username;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "site=" + site +
                ", username='" + username + '\'' +
                ", doctor=" + doctor +
                ", password=" + password +
                '}';
    }
}
