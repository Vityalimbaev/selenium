package models.dataproviders;

public abstract class DataProviderAbstract {
    private String url;
    private String user_login;
    private String user_password;

    public DataProviderAbstract(String url, String user_login, String user_password) {
        this.url = url;
        this.user_login = user_login;
        this.user_password = user_password;
    }

    public String getUrl() {
        return url;
    }

    public String getUser_login() {
        return user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
