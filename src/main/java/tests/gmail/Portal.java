package tests.gmail;

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
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.Gmail;
import tests.portal.Patient;
import tests.portal.Visit;

import java.util.ArrayList;
import java.util.Collections;

public class Portal extends DriverWaitWrapper {
    private String date;
    private String time;
    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderPortal dataProviderPortal;
    private API crossbrowsing;
    private Gmail gmail = new Gmail();
    private Patient patientTest;
    private Visit visitTest;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderPortal = (DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PORTAL);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] Gmail " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get("http://www.yopmail.com/ru/");
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));

        dataProviderPortal.setUser_login("alfatelltester@yopmail.com");
        dataProviderPortal.setUser_password("qweqwe123");

        patientTest = new Patient();
        patientTest.setDRIVER(getDRIVER());
        patientTest.setDataProviderPortal(dataProviderPortal);
        patientTest.setPreloader(preloader);
        patientTest.setCrossbrowsing(crossbrowsing);
        patientTest.setOnline(false);

        visitTest = new Visit();
        visitTest.setDRIVER(getDRIVER());
        visitTest.setPatientTest(patientTest);
        visitTest.setPreloader(preloader);
        visitTest.setPortal(new pages.Portal());
    }

    @Test(description = "Авторизация")
    public void yopmailAuthorization(){
        visibility(gmail.input_login).sendKeys("alfatelltester");
        visibility(gmail.btn_next).click();
    }

    @Test(dependsOnMethods = "yopmailAuthorization", description = "Авторизация на сайте")
    public void newTab(){
        String a = "window.open('" + dataProviderPortal.getClone_url() + "','_blank');";  // replace link with your desired link
        try {
            ((JavascriptExecutor)getDRIVER()).executeScript(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        tabs2 = new ArrayList<String> (getDRIVER().getWindowHandles());
        switchToWindow(tabs2.get(1));
    }

    @Test(dependsOnMethods = "newTab", description = "Сброс пароля")
    public void forgotPassword(){
        preloader.isLoadingDesktopPortal();
        exist(new pages.Portal().preloaderDesctop, 5);
        invisible(new pages.Portal().preloaderDesctop, 30);
        visibility(XpathWrapper.contains("Забыли пароль ?", "кнопка сброса пароля")).click();
        visibility(new pages.Portal().input_forgotPassword).sendKeys("alfatelltester@yopmail.com");
        visibility(new pages.Portal().btn_submit).click();
        Assert.assertTrue(exist(XpathWrapper.contains("почтовый ящик", "ссылка отправлена"), 10));
    }

    @Test(dependsOnMethods = "forgotPassword", description = "Проверка записи")
    public void checkForgotPassword() throws InterruptedException {
        switchToWindow(tabs2.get(0));
        Thread.sleep(5 * 1000);
        visibility(gmail.btn_refresh).click();
        switchToFrame(visibility(gmail.iframe_mail));
        Assert.assertTrue(exist(XpathWrapper.contains("Смена пароля на сайте", "Смена пароля на сайте"), 10));
        switchToDefaultContent();
    }

    @Test(dependsOnMethods = "checkForgotPassword", description = "Авторизация на сайте")
    public void authorization(){
        switchToWindow(tabs2.get(1));
        patientTest.authorization();
        visibility(XpathWrapper.contains("Продолжить", "кнопка продолжить")).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Запись на прием")
    public void visit(){
        visitTest.selectClinic();
        visitTest.selectSpecialty();
        visitTest.selectDoctor();
        visitTest.selectDayAndTime();
        visitTest.checkService();
    }

    @Test(dependsOnMethods = "visit", description = "Проверка записи")
    public void checkVisit() throws InterruptedException {
        switchToWindow(tabs2.get(0));
        Thread.sleep(5 * 1000);
        visibility(gmail.btn_refresh).click();
        switchToFrame(visibility(gmail.iframe_mail));
        Assert.assertTrue(exist(XpathWrapper.contains(visitTest.getPatientTest().getDate(), "дата записи"), 10));
        Assert.assertTrue(exist(XpathWrapper.contains("Запись на прием", "запись на прием"), 10));
        switchToDefaultContent();
    }

    @Test(dependsOnMethods = "checkVisit", description = "Отмена записи на прием")
    public void cancelVisit(){
        switchToWindow(tabs2.get(1));
        visitTest.abortAppoint();
    }

    @Test(dependsOnMethods = "cancelVisit", description = "Проверка отмены записи")
    public void checkCancelVisit() throws InterruptedException {
        switchToWindow(tabs2.get(0));
        Thread.sleep(5 * 1000);
        visibility(gmail.btn_refresh).click();
        switchToFrame(visibility(gmail.iframe_mail));
        Assert.assertTrue(exist(XpathWrapper.contains(visitTest.getPatientTest().getDate(), "дата записи"), 10));
        Assert.assertTrue(exist(XpathWrapper.contains("Отмена записи на прием", "запись на прием"), 10));
        switchToDefaultContent();
    }

    @Test(dependsOnMethods = "checkCancelVisit", description = "Проверка автоматической отмены")
    public void checkAutoCancelVisit() throws InterruptedException {
        switchToWindow(tabs2.get(1));

        patientTest = new Patient();
        patientTest.setDRIVER(getDRIVER());
        patientTest.setDataProviderPortal(dataProviderPortal);
        patientTest.setPreloader(preloader);
        patientTest.setCrossbrowsing(crossbrowsing);
        patientTest.setOnline(true);
        visitTest.setPatientTest(patientTest);

        get(dataProviderPortal.getClone_url());
        visitTest.selectClinic();
        visitTest.selectSpecialty();
        visitTest.selectDoctor();
        visitTest.selectDayAndTime();
        Thread.sleep(55 * 1000);

        switchToWindow(tabs2.get(0));
        Thread.sleep(5 * 1000);
        visibility(gmail.btn_refresh).click();
        switchToFrame(visibility(gmail.iframe_mail));
        boolean a = (exist(XpathWrapper.contains(visitTest.getPatientTest().getDate(), "дата записи"), 10));
        boolean b = (exist(XpathWrapper.contains("Отмена записи на прием", "запись на прием"), 10));
        switchToDefaultContent();

        switchToWindow(tabs2.get(1));
        visitTest.abortAppoint();

        Assert.assertTrue(a);
        Assert.assertTrue(b);
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
