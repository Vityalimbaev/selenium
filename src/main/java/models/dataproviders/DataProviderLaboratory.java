package models.dataproviders;

public class DataProviderLaboratory extends DataProviderAbstract{

    private String username;
    private String userYOB;
    private String pcode;

    public DataProviderLaboratory(String url, String user_login, String user_password, String username, String userYOB, String pcode) {
        super(url, user_login, user_password);
        this.username = username;
        this.userYOB = userYOB;
        this.pcode = pcode;
    }

    public String getUsername() {
        return username;
    }

    public String getUserYOB() {
        return userYOB;
    }

    public String getPcode() {
        return pcode;
    }
}
