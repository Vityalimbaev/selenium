package models.dataproviders;

public class DataProviderPortal extends DataProviderAbstract{

    private String doctor_login;
    private String doctor_pass;
    private String clinic;
    private String specialty;
    private String specialty_online;
    private String name_doctor;
    private String name_doctor_online;
    private String clone_url;

    public DataProviderPortal(String url, String user_login, String user_password, String doctor_login, String doctor_pass, String clinic, String specialty, String specialty_online, String name_doctor, String name_doctor_online, String clone_url) {
        super(url, user_login, user_password);
        this.doctor_login = doctor_login;
        this.doctor_pass = doctor_pass;
        this.clinic = clinic;
        this.specialty = specialty;
        this.specialty_online = specialty_online;
        this.name_doctor = name_doctor;
        this.name_doctor_online = name_doctor_online;
        this.clone_url = clone_url;
    }

    public String getDoctor_login() {
        return doctor_login;
    }

    public String getDoctor_pass() {
        return doctor_pass;
    }

    public String getClinic() {
        return clinic;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getSpecialty_online() {
        return specialty_online;
    }

    public String getName_doctor() {
        return name_doctor;
    }

    public String getName_doctor_online() {
        return name_doctor_online;
    }

    public String getClone_url() {
        return clone_url;
    }
}
