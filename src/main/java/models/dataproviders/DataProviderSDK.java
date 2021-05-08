package models.dataproviders;

public class DataProviderSDK extends DataProviderAbstract{
    private String protocolId;
    private String treatcode;
    private String patientName;

    public DataProviderSDK(String url, String user_login, String user_password, String protocolId, String treatcode, String patientName) {
        super(url, user_login, user_password);
        this.protocolId = protocolId;
        this.treatcode = treatcode;
        this.patientName = patientName;
    }

    public String getProtocolId() {
        return protocolId;
    }

    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public String getTreatcode() {
        return treatcode;
    }

    public void setTreatcode(String treatcode) {
        this.treatcode = treatcode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
