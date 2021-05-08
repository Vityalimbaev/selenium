package models.dataproviders;

public class DataProviderAPI extends DataProviderAbstract{
    private String grant_type;
    private String client_id;
    private String scope;
    private String secretKey;
    private String hostSecretKey;

    public DataProviderAPI(String url, String user_login, String user_password, String grant_type, String client_id, String scope, String secretKey, String hostSecretKey) {
        super(url, user_login, user_password);
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.scope = scope;
        this.secretKey = secretKey;
        this.hostSecretKey = hostSecretKey;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getScope() {
        return scope;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getHostSecretKey() {
        return hostSecretKey;
    }

    public void setHostSecretKey(String hostSecretKey) {
        this.hostSecretKey = hostSecretKey;
    }
}
