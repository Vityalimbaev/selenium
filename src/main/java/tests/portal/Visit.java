package tests.portal;

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
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Portal;
import pages.Promo;

import java.util.ArrayList;

public class Visit extends DriverWaitWrapper {
    private MongoConnector mongoConnector;
    private String date;
    private String time;
    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderPortal dataProviderPortal;
    private API crossbrowsing;
    private Portal portal = new Portal();
    private Patient patientTest;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderPortal = (DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PORTAL);
//        mongoConnector = new MongoConnector("192.168.201.65", 27017);
//        mongoConnector.setWebSite("Инфоклиника.RU");
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] Visit " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderPortal.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));

        patientTest = new Patient();
        patientTest.setDRIVER(getDRIVER());
        patientTest.setDataProviderPortal(dataProviderPortal);
        patientTest.setPreloader(preloader);
        patientTest.setCrossbrowsing(crossbrowsing);
        patientTest.setOnline(false);
    }

    @Test(description = "Авторизация")
    public void authorization(){
        patientTest.authorization();
    }

    @Test(dependsOnMethods = "authorization", description = "Выбор клиники")
    public void selectClinic(){
//        preloader.isLoadingDesktopPortal();
//        invisible(portal.preloaderDesctop, 30);
//        XpathWrapper btn = XpathWrapper.contains(dataProviderPortal.getClinic(),"кнопка выбора клиники");
//        jsScroll(visibility(btn));
//        visibility(btn).click();
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

    @Test(dependsOnMethods = "selectDayAndTime", description = "Проверка записи и выбранных услуг")
    public void checkService(){
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        visibility(XpathWrapper.contains("Мои записи", "кнопка мои записи")).click();
        preloader.waitForPageLoaded();
        patientTest.patientSelectDoctor();
        if(patientTest.getServiceName() != null){
            Assert.assertTrue(visibility(portal.services.addToXpath("[last()]")).getText().contains(patientTest.getServiceName()));
        }
    }

    @Test(dependsOnMethods = "checkService", description = "Отмена записи")
    public void abortAppoint(){
        visibility(portal.btn_cancel).click();
        if(exist(portal.btn_submit, 30)){
            visibility(portal.btn_submit).click();
        }
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }

    public Patient getPatientTest() {
        return patientTest;
    }

    public void setPatientTest(Patient patientTest) {
        this.patientTest = patientTest;
    }

    public void setPreloader(Preloader preloader) {
        this.preloader = preloader;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }
}
