package factories;

import models.dataproviders.*;
import models.enums.NameProject;
import org.testng.TestException;

public abstract class DataProviderFactory {
    private final static DataProviderPortal PORTAL_PROD = new DataProviderPortal("https://demo.infoclinica.ru/","demo@infoclinica.ru","12345","doctor","12345","Клиника №1","Уролог","Терапевт","Веселый Роман Сергеевич","Медицинский Иван Иванович", "http://doctor-demo.infoclinica.ru/");
    private final static DataProviderPortal PORTAL_DEV = new DataProviderPortal("https://dev-demo.infoclinica.ru/","dev@infoclinica.ru","1234567","doctor","1234567","Клиника №1","Терапевт","Уролог","Медицинский Иван Иванович","Веселый Роман Сергеевич", "http://dev-doctor-demo.infoclinica.ru/");
    private final static DataProviderLaboratory LAB_PROD = new DataProviderLaboratory("https://dev-labportal.infoclinica.ru/","sak.e","00990", "Проверочный Иван Иванович", "2011", "990000066");
    private final static DataProviderLaboratory LAB_DEV = new DataProviderLaboratory("https://dev-labportal-demo.infoclinica.ru/login", "doctor", "12345", "Проверочный Иван Иванович", "2011", "990000066");
    private final static DataProviderPortal PROMO_PROD = new DataProviderPortal("https://infoclinica.ru/","demo@infoclinica.ru","12345","doctor","12345","Запись на онлайн прием","Терапевт","","Медицинский Иван Иванович","Медицинский Иван Иванович", "http://doctor-demo.infoclinica.ru/");
    private final static DataProviderAdmin ADMIN_PROD = new DataProviderAdmin("https://admin.infoclinica.ru/","d.lukin","Safe4root123", "Инфоклиника.RU (ДЕМО)","tester-buh","Safe4root123", "superadmin", "123");
    private final static DataProviderAdmin ADMIN_DEV = new DataProviderAdmin("https://dev-admin.infoclinica.ru/","tester","123", "Инфоклиника.RU","testeraccountant","123", "superadmin", "123");
    private final static DataProviderAPI ADMINAPI_PROD = new DataProviderAPI("https://demo.infoclinica.ru","demo@infoclinica.ru","12345", "password","567d3b7fdcd497c7dfdce901","client", "0814ac0634be4a0d", "infoclinica");
    private final static DataProviderAPI ADMINAPI_DEV = new DataProviderAPI("https://dev-demo.infoclinica.ru","dev@infoclinica.ru","1234567", "password","567d3b7fdcd497c7dfdce901","client", "0814ac0634be4a0d", "infoclinica");
    private final static DataProviderAPI LABAPI_DEV = new DataProviderAPI("https://dev-labportal-demo.infoclinica.ru","nologin","nopassword", "client_credentials","5652ec01f14c62197ed8f1d6","clinic", "db4b0837-38b3-4b32-851f-a5d8e5e2cf31", "nohostsecret");
    private final static DataProviderSDK SDK_PROD = new DataProviderSDK("https://www.infoclinica.ru/sdk","nologin","nopassword", "10000004", "10012503", "Юлия Николаевна");
    private final static DataProviderSDK SDK_DEV = new DataProviderSDK("https://dev-www.infoclinica.ru/sdk","nologin","nopassword", "10010012", "10012502008", "Иван Иванович");
    private final static DataProviderWebInfoClinica WEBINFOCLINICA_PROD = new DataProviderWebInfoClinica("https://web.infoclinica.ru/","doctor","12345");
    private final static DataProviderWebInfoClinica WEBINFOCLINICA_DEV = new DataProviderWebInfoClinica("https://dev-web.infoclinica.ru/","admin","master");
    private final static DataProviderVcDemo VCDEMO_PROD = new DataProviderVcDemo("https://vc-demo.infoclinica.ru/guest/login","admin","demoserver123sds");
    private final static DataProviderVcDemo VCDEMO_DEV = new DataProviderVcDemo("https://dev-vc-demo.infoclinica.ru/","apps","system32");

    public static DataProviderAbstract get(String mode, NameProject project){
        switch (project){
            case PORTAL:{
                switch (mode.toLowerCase()){
                    case "dev": return PORTAL_DEV;
                    case "prod": return PORTAL_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case LABORATORY:{
                switch (mode.toLowerCase()){
                    case "dev": return LAB_DEV;
                    case "prod": return LAB_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case PROMO:{
                switch (mode.toLowerCase()){
                    case "prod": return PROMO_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case ADMIN:{
                switch (mode.toLowerCase()){
                    case "dev": return ADMIN_DEV;
                    case "prod": return ADMIN_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case ADMINAPI:{
                switch (mode.toLowerCase()) {
                    case "dev": return ADMINAPI_DEV;
                    case "prod": return ADMINAPI_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case SDK:{
                switch (mode.toLowerCase()) {
                    case "dev": return SDK_DEV;
                    case "prod": return SDK_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case WEBINFOCLINICA:{
                switch (mode.toLowerCase()){
                    case "dev": return WEBINFOCLINICA_DEV;
                    case "prod": return WEBINFOCLINICA_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case VCDEMO:{
                switch (mode.toLowerCase()){
                    case "dev": return VCDEMO_DEV;
                    case "prod": return VCDEMO_PROD;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            case LABPORTALAPI:{
                switch (mode.toLowerCase()){
                    case "dev": return LABAPI_DEV;
                    case "prod": return LABAPI_DEV;
                    default: throw new TestException("DataProvider not found. Because mode: " + mode + ". " + "Project: " + project);
                }
            }
            default: throw new TestException("Project profile not found. Project: " + project);
        }
    }
}
