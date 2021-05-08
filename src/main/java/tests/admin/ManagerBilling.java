package tests.admin;

import connection.Connector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderAdmin;
import models.dataproviders.DataProviderPortal;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.ElementWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AdminPage;
import tests.portal.Patient;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ManagerBilling extends DriverWaitWrapper {
    //superadmin 123 tester 123 https://dev-admin.infoclinica.ru/
    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderAdmin dataProviderAdmin;
    private API crossbrowsing;
    private AdminPage admin = new AdminPage();
    private AccountantBilling accountantBilling;
    private Actions action;
    private String date;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderAdmin = (DataProviderAdmin) DataProviderFactory.get(System.getProperty("mode"), NameProject.ADMIN);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] ManagerBilling " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderAdmin.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
        action = new Actions(getDRIVER());
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        date = dateFormat.format(cal.getTime());

        accountantBilling = new AccountantBilling();
        accountantBilling.setDRIVER(getDRIVER());
        accountantBilling.setAction(action);
        accountantBilling.setDataProviderAdmin(dataProviderAdmin);
        accountantBilling.setDate(date);
        Patient patientTest = new Patient();
        patientTest.setDRIVER(getDRIVER());
        patientTest.setDataProviderPortal((DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PORTAL));
        patientTest.setPreloader(preloader);
        patientTest.setCrossbrowsing(crossbrowsing);
        patientTest.setgBrowser(browser);
        patientTest.setOnline(false);
        accountantBilling.setPatientTest(patientTest);
    }


    @Test(description = "Авторизация")
    public void authorization(){
        visibility(admin.input_login).sendKeys(dataProviderAdmin.getUser_login());
        visibility(admin.input_password).sendKeys(dataProviderAdmin.getUser_password());
        visibility(admin.btn_login).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Выбор клиники")
    public void selectClinic(){
        preloader.waitForPageLoaded();
        invisible(admin.spinner, 10);
        visibility(admin.btn_searchClinic).click();
        visibility(XpathWrapper.contains(dataProviderAdmin.getSitename(),"название сайта")).click();
        if(exist(admin.btn_submitDisabled, 10)) visibility(admin.btn_cancel).click();
        else visibility(admin.btn_submit).click();
    }

    @Test(dependsOnMethods = "selectClinic", description = "Проверка журнала")
    public void openJournal(){
        visibility(admin.btn_tarificationJournal).click();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String date = dateFormat.format(cal.getTime());
        ElementWrapper yesterday;
        while (!exist(XpathWrapper.contains(date, "вчерашний день"), 10)){
            invisible(admin.preloader,10);
            visibility(admin.btn_pastmonth).click();
        }
        yesterday = visibility(XpathWrapper.contains(date, "вчерашний день"));
        yesterday.click();
        Actions action = new Actions(getDRIVER());
        action.doubleClick(yesterday.getElement()).perform();
        invisible(admin.preloader,10);
        List<ElementWrapper> list = getElements(admin.btn_time);
        boolean complete = false;
        for(ElementWrapper element : list){
            if(element.getElement().getText().contains("Выполнено")) {
                complete = true;
                break;
            }
        }
        Assert.assertTrue(complete);
    }

    @Test(alwaysRun = true, dependsOnMethods = "openJournal", description = "Отправка уведомления")
    public void sendNotification() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnsupportedEncodingException {
        accountantBilling.sendNotification();
    }

    @Test(dependsOnMethods = "sendNotification", description = "Проверка уведомления")
    public void checkNotification(){
        preloader.waitForPageLoaded();
        invisible(admin.loadmask, 10);
        visibility(XpathWrapper.contains("Журнал отправки сообщений","Журнал отправки сообщений").addToXpath("[last()]")).click();
        XpathWrapper todayXpath = new XpathWrapper("","");
        for (int i = 0; i < 2; i++) {//Проверяем только этот и предыдущий месяц
            visibility(admin.btn_update.addToXpath("[last()]")).click();
            XpathWrapper btn_site = admin.btn_site.getReplaceContext(dataProviderAdmin.getSitename()).addToXpath("[last()]");
//            XpathWrapper btn_site = admin.btn_site.getReplaceContext(dataProviderAdmin.getSitename()).addToXpath(System.getProperty("mode").toLowerCase().equals("dev") ? "[last()]" : "[last()-1]");
            preloader.waitForPageLoaded();
            invisible(admin.loadmask, 10);
            visibility(btn_site).click();
            action.doubleClick(visibility(btn_site).getElement()).perform();
            todayXpath = XpathWrapper.contains(date, "сегодняшний день").addToXpath("[last()]");
            if(!exist(todayXpath, 10)) {
                visibility(admin.btn_pastmonth).click();
                invisible(admin.preloader, 10);
            }
            else break;
        }
        ElementWrapper today = visibility(todayXpath);
        today.click();
        action.doubleClick(today.getElement()).perform();
        invisible(admin.preloader,10);

        accountantBilling.checkNotification();
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
