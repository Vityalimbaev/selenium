package models.dataproviders;

public class DataProviderAdmin extends DataProviderAbstract{
    private String sitename;
    private String accountantName;
    private String accountantPassword;
    private String adminName;
    private String adminPassword;

    public DataProviderAdmin(String url, String user_login, String user_password, String sitename, String accountantName, String accountantPassword, String adminName, String adminPassword) {
        super(url, user_login, user_password);
        this.sitename = sitename;
        this.accountantName = accountantName;
        this.accountantPassword = accountantPassword;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getAccountantName() {
        return accountantName;
    }

    public void setAccountantName(String accountantName) {
        this.accountantName = accountantName;
    }

    public String getAccountantPassword() {
        return accountantPassword;
    }

    public void setAccountantPassword(String accountantPassword) {
        this.accountantPassword = accountantPassword;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
