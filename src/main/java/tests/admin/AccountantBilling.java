package tests.admin;

import connection.Connector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import io.restassured.response.Response;
import models.Preloader;
import models.RestController;
import models.XML.XML_DEV;
import models.browsers.Browser;
import models.dataproviders.DataProviderAdmin;
import models.dataproviders.DataProviderPortal;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.ElementWrapper;
import models.wrappers.XpathWrapper;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AdminPage;
import tests.portal.Patient;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class AccountantBilling extends DriverWaitWrapper {
    //superadmin 123 tester 123 https://dev-admin.infoclinica.ru/
    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderAdmin dataProviderAdmin;
    private API crossbrowsing;
    private AdminPage admin = new AdminPage();
    private String gBrowser;
    private Patient patientTest;
    private Actions action;
    private String date;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderAdmin = (DataProviderAdmin) DataProviderFactory.get(System.getProperty("mode"), NameProject.ADMIN);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] AccountantBilling " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderAdmin.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
        gBrowser = browser;
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        this.action = new Actions(getDRIVER());
        this.date = dateFormat.format(cal.getTime());


        patientTest = new Patient();
        patientTest.setDRIVER(getDRIVER());
        patientTest.setDataProviderPortal((DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PORTAL));
        patientTest.setPreloader(preloader);
        patientTest.setCrossbrowsing(crossbrowsing);
        patientTest.setgBrowser(gBrowser);
        patientTest.setOnline(false);
    }


    @Test(description = "Авторизация")
    public void authorization(){
        visibility(admin.input_login).sendKeys(dataProviderAdmin.getAccountantName());
        visibility(admin.input_password).sendKeys(dataProviderAdmin.getAccountantPassword());
        visibility(admin.btn_login).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Проверка журнала за вчера")
    public void checkJournal(){
        preloader.waitForPageLoaded();
        invisible(admin.spinner, 10);
        visibility(admin.btn_tarificationJournal).click();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String date = dateFormat.format(cal.getTime());
        if(!exist(admin.btn_site.getReplaceContext(dataProviderAdmin.getSitename()),5)) throw new SkipException("Skipping this exception");
        while (!exist(XpathWrapper.contains(date, "вчерашний день"), 10)){
            XpathWrapper btn_site = admin.btn_site.getReplaceContext(dataProviderAdmin.getSitename());
            visibility(btn_site).click();
            action.doubleClick(visibility(btn_site).getElement()).perform();
            if(!exist(XpathWrapper.contains(date, "вчерашний день"), 10)) {
                invisible(admin.preloader, 10);
                visibility(admin.btn_pastmonth).click();
            }
            else break;
        }
        ElementWrapper yesterday = visibility(XpathWrapper.contains(date, "вчерашний день"));
        yesterday.click();
        action.doubleClick(yesterday.getElement()).perform();
        invisible(admin.preloader,10);
        preloader.waitForPageLoaded();
        invisible(admin.spinner, 10);
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

    @Test(alwaysRun = true, dependsOnMethods = "checkJournal", description = "Открыть новую вкладку")
    public void newTab(){
        String a = "window.open('" + patientTest.getDataProviderPortal().getUrl() + "','_blank');";  // replace link with your desired link
        try {
            ((JavascriptExecutor)getDRIVER()).executeScript(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        tabs2 = new ArrayList<String> (getDRIVER().getWindowHandles());
        if(gBrowser.equals("safari")) Collections.reverse(tabs2);
        switchToWindow(tabs2.get(1));
    }

    @Test(dependsOnMethods = "newTab", description = "Запись на прием")
    public void appoint(){
        patientTest.authorization();
        patientTest.selectClinic();
        patientTest.selectSpecialty();
        patientTest.selectDoctor();
        patientTest.selectDayAndTime();
    }

    @Test(dependsOnMethods = "appoint", description = "Проверка записи")
    public void checkAppoint(){
        switchToWindow(tabs2.get(0));
        visibility(admin.btn_appointsJournal).click();
        visibility(admin.btn_update).click();
        XpathWrapper btn_site = admin.btn_site.getReplaceContext(dataProviderAdmin.getSitename());
        visibility(btn_site).click();
        action.doubleClick(visibility(btn_site).getElement()).perform();
        while (!exist(XpathWrapper.contains(date, "сегодняшняя дата"), 10)){
            visibility(admin.btn_pastmonth);
            visibility(btn_site).click();
            action.doubleClick(visibility(btn_site).getElement()).perform();
        }
        visibility(XpathWrapper.contains(date, "сегодняшняя дата")).click();

        preloader.waitForPageLoaded();
        invisible(admin.spinner, 10);
        List<ElementWrapper> list = getElements(admin.btn_appoint.getReplaceContext(patientTest.getDate()));
        boolean found = false;
        int i = 0;
        while (!found && i < list.size()){//Поиск элемента с необходимым временем
            if(list.get(i).getText().contains(patientTest.getTime()) && list.get(i).getText().contains("Выполнено")){
                found = true;
            }
            else {i++;}
        }
        Assert.assertTrue(found);
    }

    @Test(alwaysRun = true, dependsOnMethods = "checkAppoint", description = "Запись по API")
    public void restAppoint(){
        RestController restController = new RestController();
        try {
            restController.getClientSecret();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        restController.generateToken();

        Response response = restController.postXml(/*patientTest.getDataProviderPortal().getUrl() +*/ "/api/xml", new XML_DEV().addappoint.replace("?", "20200831").replace("990000066",restController.getClientid()));
        response.then().assertThat().body("ACK.SCHEDULE_REC_RESERVE_OUT.CHECKDATA.CHECKCODE", containsString("2"))
                .assertThat().body("ACK.SCHEDULE_REC_RESERVE_OUT.CHECKDATA.CHECKLABEL", containsString("Время занято"))
                .assertThat().body("ACK.SCHEDULE_REC_RESERVE_OUT.SPRESULT", containsString("0"));
//        response.then().assertThat().body("ACK.SCHEDULE_REC_RESERVE_OUT.SCHED_INFO.FNAME", containsString("Онлайн клиника"))
//        .assertThat().body("ACK.SCHEDULE_REC_RESERVE_OUT.SCHED_INFO.DNAME",containsString("Веселый Роман Сергеевич"))
//        .assertThat().body("ACK.SCHEDULE_REC_RESERVE_OUT.SCHED_INFO.DEPNAME",containsString("Уролог"))
//        .assertThat().body("ACK.SCHEDULE_REC_RESERVE_OUT.SPCOMMENT",containsString("Запись на прием успешно зарезервирована"));
//        String id = response.body().xmlPath().getString("ACK.SCHEDULE_REC_RESERVE_OUT.SCHED_INFO.SCHEDID");
//        Response response1 = restController.post("https://dev-demo.infoclinica.ru/api/xml", new XMLList().removeappoint.replace("?",id));
//        response1.then().assertThat().body("ACK.SCHEDULE_REC_REMOVE_OUT.SPCOMMENT",containsString("Запись пациента на прием удалена"));
    }

    @Test(dependsOnMethods = "restAppoint", description = "Проверка записи по API")
    public void checkRestAppoint(){
        visibility(admin.btn_appointsJournal).click();
        visibility(admin.btn_update).click();

        for(int i = 0; i < 2; i++){
            invisible(new XpathWrapper("//*[contains(@id,'loadmask')]","Прелоадер 2"), 10);
            invisible(admin.preloader,10);
            visibility(admin.input_search).sendKeys(dataProviderAdmin.getSitename());
            visibility(admin.btn_site.getReplaceContext("Мобильное приложение Инфоклиника.RU")).click();
            invisible(new XpathWrapper("//*[contains(@id,'loadmask')]","Прелоадер 2"), 10);
            invisible(admin.preloader,10);
            XpathWrapper btn_site = admin.btn_site.getReplaceContext(dataProviderAdmin.getSitename()).addToXpath("[2]");
            visibility(btn_site).click();
            action.doubleClick(visibility(btn_site).getElement()).perform();
            if(!exist(XpathWrapper.contains(date, "сегодняшний день"), 10)) {
                invisible(admin.preloader, 10);
                visibility(admin.btn_pastmonth);
            }
            else break;
        }
        ElementWrapper today = visibility(XpathWrapper.contains(date, "сегодняшний день"));
        today.click();
        action.doubleClick(today.getElement()).perform();
        invisible(admin.preloader,10);

        List<ElementWrapper> list = getElements(admin.btn_appoint.getReplaceContext("31.08.2020"));
        boolean found = false;
        int i = 0;
        while (!found && i < list.size()){//Поиск элемента с необходимым временем
            if(list.get(i).getText().contains("08:30 - 09:00") && list.get(i).getText().contains("К сожалению, выбранное время было недавно занято. Пожалуйста, выберите другое время.")){
//                Assert.assertTrue(list.get(i).getText().contains("К сожалению, выбранное время было недавно занято. Пожалуйста, выберите другое время."));
                found = true;
            }
            else {i++;}
        }
        Assert.assertTrue(found);
    }

    @Test(alwaysRun = true, dependsOnMethods = "checkRestAppoint", description = "Отправка уведомления")
    public void sendNotification() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, UnsupportedEncodingException {
        /*
        * Не отправляет без сертификата .p12
        * Чтобы переконвертировать из crt и key
        * openssl pkcs12 -export -out infodoctor.p12 -in infodoctor.crt -inkey infodoctor.key
        * Пароль qweqwe
        */
        RestController restController = new RestController();
        restController.getClientSecret();
        restController.generateToken();
        Response response = restController.get(/*patientTest.getDataProviderPortal().getUrl() +*/ "/user-info?pcode="+restController.getClientid(), "Authorization:Bearer " + restController.getAccessToken());
//        Response response = restController.postWithCert("https://dev-api.infoclinica.ru/api/notification/send", new XMLList().sendNotificationJson);
        String notificationId = new JSONObject(new JSONObject(response.body().asString()).get("data").toString()).get("notificationId").toString();
        restController.post2(patientTest.getDataProviderPortal().getUrl() + "api/notification/send",new XML_DEV().sendNotificationJson2.replace("*",String.valueOf(java.util.UUID.randomUUID())).replace("?",notificationId));
    }

    @Test(dependsOnMethods = "sendNotification", description = "Открыть журнал уведомлений")
    public void openNotificationJournal(){
        XpathWrapper btn_journal = admin.btn_tarificationJournal.addToXpath("[3]");
        visibility(btn_journal).click();
//        visibility(admin.btn_update).click();
        XpathWrapper todayXpath = new XpathWrapper("","");
        for (int i = 0; i < 2; i++) {//Проверяем только этот и предыдущий месяц
            XpathWrapper btn_site = admin.btn_site.getReplaceContext(dataProviderAdmin.getSitename()).addToXpath("[last()]");
//            btn_site.addToXpath(System.getProperty("mode").toLowerCase().equals("dev") ? "[last()]" : "[last()-1]");
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
    }

    @Test(dependsOnMethods = "openNotificationJournal", description = "Проверка уведомления")
    public void checkNotification(){
        List<ElementWrapper> list = getElements(admin.btn_appoint.getReplaceContext(date));
        int i = 0;
        while (i < list.size() && !list.get(i).getText().contains("Выполнено")){i++;}
        Assert.assertTrue(list.get(i).getText().contains("Выполнено"));
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }

    public DataProviderAdmin getDataProviderAdmin() {
        return dataProviderAdmin;
    }

    public void setDataProviderAdmin(DataProviderAdmin dataProviderAdmin) {
        this.dataProviderAdmin = dataProviderAdmin;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Patient getPatientTest() {
        return patientTest;
    }

    public void setPatientTest(Patient patientTest) {
        this.patientTest = patientTest;
    }
}
