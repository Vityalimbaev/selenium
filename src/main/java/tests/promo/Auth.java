package tests.promo;

import connection.Connector;
import connection.MongoConnector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderPortal;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.XpathWrapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Promo;
import tests.portal.Patient;

public class Auth extends DriverWaitWrapper {
    private MongoConnector mongoConnector;
    private Preloader preloader;
    private DataProviderPortal dataProviderPromo;
    private Patient patientTest;
    private API crossbrowsing;
    private Promo promo = new Promo();
//    private Portal loadClassPortal = new Portal(); // костыль

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderPromo = (DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PROMO);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] Promo " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderPromo.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));

        patientTest = new Patient();
        patientTest.setDRIVER(getDRIVER());
        patientTest.setDataProviderPortal(dataProviderPromo);
        patientTest.setPreloader(preloader);
        patientTest.setCrossbrowsing(crossbrowsing);
        patientTest.setgBrowser(browser);
    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(promo.btn_cabinet).click();
        switchToFrame(visibility(promo.iframe_cabinet));
        patientTest.authorization();
    }

    @Test(dependsOnMethods = "authorization", description = "Выбор клиники")
    public void selectClinic(){
        patientTest.selectClinic();
    }

    @Test(dependsOnMethods = "selectClinic", description = "Выбор специализации")
    public void selectSpecialty(){
        patientTest.selectSpecialty();
    }
    //
    @Test(dependsOnMethods = "selectSpecialty", description = "Выбор врача")
    public void selectDoctor() {
        patientTest.selectDoctor();
    }
    //
    @Test(dependsOnMethods = "selectDoctor", description = "Выбор даты и времени")
    public void selectDayAndTime(){
        patientTest.selectDayAndTime();
    }

    @Test(dependsOnMethods = "selectDayAndTime", description = "Оплата")
    public void payment(){
        patientTest.payment();
    }

    @Test(dependsOnMethods = "payment", description = "Открыть новую вкладку")
    public void newTab(){
        switchToDefaultContent();
        patientTest.newTab();
    }

    @Test(dependsOnMethods = "newTab", description = "Авторизация доктора")
    public void doctorAuthorization() {
        patientTest.doctorAuthorization();
    }

    @Test(dependsOnMethods = "doctorAuthorization", description = "Доктор выбирает пациента")
    public void doctorSelectPatient(){
        patientTest.doctorSelectPatient();
    }

    @Test(dependsOnMethods = "doctorSelectPatient", description = "Видеосвязь доктора")
    public void doctorConnectTrueConf(){
        patientTest.doctorConnectTrueConf();
    }


    @Test(dependsOnMethods = "doctorConnectTrueConf", description = "Пациент выбирает доктора")
    public void patientSelectDoctor(){
        switchToFrame(visibility(promo.iframe_cabinet));
        visibility(promo.btn_appoints).click();
        patientTest.patientSelectDoctor();
    }

    @Test(dependsOnMethods = "patientSelectDoctor", description = "Видеосвязь пациента")
    public void patientTrueConf(){
        patientTest.patientTrueConf();
    }

    @Test(dependsOnMethods = "patientTrueConf", description = "Доктор выходит из видео")
    public void doctorDisconnect(){
        patientTest.doctorDisconnect();
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
