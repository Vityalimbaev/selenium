package tests.portal;

import connection.MongoConnector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.enums.NameProject;
import models.Preloader;
import models.browsers.Browser;
import connection.Connector;
import models.enums.Status;
import models.wrappers.ElementWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import pages.Portal;
import models.wrappers.DriverWaitWrapper;
import models.dataproviders.DataProviderPortal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patient extends DriverWaitWrapper {
    private MongoConnector mongoConnector;
    private String date;
    private String time;
    private String serviceName;
    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderPortal dataProviderPortal;
    private API crossbrowsing;
    private Portal portal = new Portal();
    private boolean online;
    private String gBrowser;
    private boolean confirmModal = true;


    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderPortal = (DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PORTAL);
//        mongoConnector = new MongoConnector("192.168.201.65", 27017);
//        mongoConnector.setWebSite("Инфоклиника.RU");
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "[" + System.getProperty("mode") + "] Patient " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderPortal.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
        online = true;
        gBrowser = browser;
    }

    @Test(description = "Авторизация")
    public void authorization(){
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        visibility(portal.input_login).sendKeys(dataProviderPortal.getUser_login());
        visibility(portal.input_password).sendKeys(dataProviderPortal.getUser_password());
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
//        if(mongoConnector.getApplicationProperties().get("confirmPersonalData").equals("allEnabled")) {
            visibility(portal.checkbox_agree).click();
//        }
        visibility(portal.btn_login).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Выбор клиники")
    public void selectClinic(){
        //jse.waitForPageLoaded();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        XpathWrapper btn = online ? XpathWrapper.contains("Запись на онлайн прием","кнопка выбора клиники") : XpathWrapper.contains(dataProviderPortal.getClinic(),"кнопка выбора клиники");
        jsScroll(visibility(btn));
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        preloader.waitForPageLoaded();
        visibility(btn).click();
        //Assert.assertNotNull(visibility(XpathWrapper.contains("Терапевт","кнопка выбора специальности")),"Не выбрана клиника");
    }
//
    @Test(dependsOnMethods = "selectClinic", description = "Выбор специализации")
    public void selectSpecialty(){
        //jse.waitForPageLoaded();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        preloader.waitForPageLoaded();
        XpathWrapper btn = XpathWrapper.contains(online ? dataProviderPortal.getSpecialty_online() : dataProviderPortal.getSpecialty(),"кнопка выбора специальности");
        jsScroll(visibility(btn));
        visibility(btn).click();
//        visibility(XpathWrapper.contains("Уролог","кнопка выбора специальности")).click();
        //Assert.assertNotNull(visibility(XpathWrapper.contains("Медицинский Иван Иванович","выбор врача")),"Не выбрана специализация");
    }
//
    @Test(dependsOnMethods = "selectSpecialty", description = "Выбор врача")
    public void selectDoctor() {
        //jse.waitForPageLoaded();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        XpathWrapper btn = XpathWrapper.contains(online ? dataProviderPortal.getName_doctor_online() : dataProviderPortal.getName_doctor(),"выбор врача");
        jsScroll(visibility(btn));
        preloader.waitForPageLoaded();
        visibility(btn).click();
        Assert.assertNotNull(visibility(portal.btn_nextMonth), "Не выбран врач");
    }
//
    @Test(dependsOnMethods = "selectDoctor", description = "Выбор даты и времени")
    public void selectDayAndTime(){
        invisible(portal.preloaderDesctop, 30);
        visibility(portal.anyday);
        if(exist(portal.btn_day, 3)){
            List<ElementWrapper> list = getElements(portal.freeday);
            boolean appointed = false;
            for (ElementWrapper day: list) {
                jsScroll(day);
                preloader.isLoadingDesktopPortal();
                invisible(portal.preloaderDesctop, 30);
                preloader.waitForPageLoaded();
                day.click();
                preloader.isLoadingDesktopPortal();
                invisible(portal.preloaderDesctop, 30);
                if(!exist(portal.onlyOneDay_info, 3)){//Есть ли табличка "Запись только на 1 день"
                    ElementWrapper timeBtn = visibility(portal.btn_time);
                    time = timeBtn.getElement().getText();
                    char[] timechar = time.toCharArray();
                    timechar[2] = ':';// 10 00 - 10 30 to 10:00 - 10:30
                    timechar[10] = ':';
                    time = String.valueOf(timechar);
                    jsScroll(timeBtn);
                    preloader.isLoadingDesktopPortal();
                    invisible(portal.preloaderDesctop, 30);
                    preloader.waitForPageLoaded();
                    timeBtn.click();
                    if(exist(XpathWrapper.contains("Выбор планируемых услуг", "Выбор планируемых услуг"), 10)){
                        visibility(portal.chosenService.addToXpath("[last()-1]")).click();
                        serviceName = visibility(portal.chosenService.addToXpath("[last()-1]")).getText();
                        preloader.waitForPageLoaded();
                        visibility(portal.btn_submit).click();
                    }
                    if(confirmModal) {
                        visibility(portal.submit_modal, 90);
                        preloader.waitForPageLoaded();
                        visibility(portal.btn_submit).click();
                    }
                    if(exist(portal.onlyOneDay_info, 3) || exist(portal.appointDenied, 3) || exist(XpathWrapper.contains("Найдена постановка на диспансерный учет с диагнозом", "есть диагноз"), 3)){
                       preloader.waitForPageLoaded();
                        visibility(portal.btn_closeOneDay).click();
                        boolean a = invisible(portal.appointDenied, 30) || invisible(portal.onlyOneDay_info, 30);
                    }
                    else{
                        appointed = true;
                        break;
                    }
                }
                preloader.isLoadingDesktopPortal();
                invisible(portal.preloaderDesctop, 30);
            }
            if(!appointed) {
                preloader.waitForPageLoaded();
                visibility(portal.btn_nextMonth).click();
                preloader.isLoadingDesktopPortal();
                invisible(portal.preloaderDesctop, 30);
                selectDayAndTime();
            }
        }
        else {
            visibility(portal.btn_nextMonth).click();
            preloader.isLoadingDesktopPortal();
            invisible(portal.preloaderDesctop, 30);
            selectDayAndTime();
        }
        if(exist(portal.firstColumnAppointInfo, 4)) {
            date = visibility(portal.date.getReplaceContext(visibility(portal.firstColumnAppointInfo).getText().contains("Клиника") ? "3" : "2")).getText();
        }
    }

    @Test(dependsOnMethods = "selectDayAndTime", description = "Оплата")
    public void payment(){
        if(exist(portal.btn_payment, 4)) jsClick(visibility(portal.btn_payment));
        preloader.waitForPageLoaded();
        visibility(XpathWrapper.contains("Закрыть", "кнопка закрыть")).click();
        preloader.waitForPageLoaded();
//        jsClick(visibility(portal.btn_submitContract));
//        switchToFrame(visibility(portal.frame_payment, 120));
//        visibility(portal.input_cardnumber).sendKeys("5555555555554477");
//        visibility(portal.input_cardexpire).sendKeys("1121");
//        visibility(portal.input_cardcvc).sendKeys("000");
////        visibility(portal.btn_submitPayment).click();
//        jsClick(visibility(portal.btn_submitPayment));
//        switchToParentFrame();
////        preloader.isLoadingDesktopPortal();
//        visibility(portal.input_sms).sendKeys("123");
//        preloader.waitForPageLoaded();
//        visibility(portal.btn_3dSecureConfirm).click();
////        preloader.isLoadingDesktopPortal();
//        invisible(portal.preloaderKassa, 20);
    }

    @Test(dependsOnMethods = "payment", description = "Открыть новую вкладку")
    public void newTab(){
        preloader.waitForPageLoaded();
        String tab = new ArrayList<String> (getDRIVER().getWindowHandles()).get(0);
        String a = "window.open('" + dataProviderPortal.getClone_url() + "lpu/login','_blank');";  // replace link with your desired link
        try {
            ((JavascriptExecutor)getDRIVER()).executeScript(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        tabs2 = new ArrayList<String> (getDRIVER().getWindowHandles());
        preloader.waitForPageLoaded();

        for(String tabs : new ArrayList<String> (getDRIVER().getWindowHandles())){
            if(!tabs.equals(tab)){
                switchToWindow(tabs);
            }
        }
//        if(gBrowser.equals("safari")) Collections.reverse(tabs2);
//        switchToWindow(tabs2.get(0));
    }

    @Test(dependsOnMethods = "newTab", description = "Авторизация доктора")
    public void doctorAuthorization(){
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        preloader.waitForPageLoaded();
        visibility(portal.input_login).sendKeys(dataProviderPortal.getDoctor_login());
        visibility(portal.input_password).sendKeys(dataProviderPortal.getDoctor_pass());
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        preloader.waitForPageLoaded();
        jsClick(visibility(portal.btn_lpuLogin));
    }

    @Test(dependsOnMethods = "doctorAuthorization", description = "Доктор выбирает пациента")
    public void doctorSelectPatient(){
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        while (!visibility(portal.lpuDate).getText().contains(date)){
            preloader.isLoadingDesktopPortal();
            invisible(portal.preloaderDesctop, 30);
            preloader.waitForPageLoaded();
            visibility(portal.btn_next).click();
            preloader.isLoadingDesktopPortal();
            invisible(portal.preloaderDesctop, 30);
        }
        XpathWrapper timeBtn = portal.btn_lpuTime.getReplaceContext(time);
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        jsScroll(visibility(timeBtn));
        preloader.waitForPageLoaded();
        visibility(timeBtn).click();
        preloader.waitForPageLoaded();
        visibility(portal.btn_onlineAppoint).click();
        preloader.isLoadingDesktopPortal();
        if(exist(XpathWrapper.contains("Согласен", "кнопка согласия с договором"), 4)) visibility(XpathWrapper.contains("Согласен", "кнопка согласия с договором")).click();
    }

    @Test(dependsOnMethods = "doctorSelectPatient", description = "Видеосвязь доктора")
    public void doctorConnectTrueConf(){
       // if(gBrowser.equals("safari") || gBrowser.equals("MicrosoftEdge")) throw new SkipException("Skipping this exception");
        preloader.waitForPageLoaded();
        visibility(portal.btn_submitContract).click();
        switchToFrame(visibility(portal.frame_trueConf));
        preloader.waitForPageLoaded();
        visibility(portal.btn_connect).click();
        switchToDefaultContent();
        switchToWindow(tabs2.get(0));
    }

    @Test(dependsOnMethods = "doctorConnectTrueConf", description = "Пациент выбирает доктора")
    public void patientSelectDoctor(){
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        List<ElementWrapper> list = getElements(portal.patientDate.getReplaceContext(date));
        while (list.size() < 1){//Поиск страницы с необходимой датой
            preloader.isLoadingDesktopPortal();
            invisible(portal.preloaderDesctop, 30);
            //ElementWrapper nextBtn = visibility(Portal.btn_nextPage);
            jsScroll(visibility(portal.btn_nextPage));
            invisible(portal.preloaderDesctop, 30);
            preloader.waitForPageLoaded();
            visibility(portal.btn_nextPage).click();
            preloader.isLoadingDesktopPortal();
            invisible(portal.preloaderDesctop, 30);
            list = getElements(portal.patientDate.getReplaceContext(date));
        }
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        String t = time.replace(':',' ');
        boolean found = false;
        int i = 0;
        while (!found && i < list.size()){//Поиск элемента с необходимым временем
            if(list.get(i).getElement().getText().contains(t)){
                visibility(list.get(i).getXpathWrapper(), 30);
                jsScroll(list.get(i));
                preloader.waitForPageLoaded();
                list.get(i).getElement().click();
                found = true;
            }
            else {i++;}
        }
        if(!found){
            preloader.isLoadingDesktopPortal();
            invisible(portal.preloaderDesctop, 30);
            jsScroll(visibility(portal.btn_nextPage));
            preloader.waitForPageLoaded();
            invisible(portal.preloaderDesctop, 30);
            preloader.waitForPageLoaded();
            visibility(portal.btn_nextPage).click();
            patientSelectDoctor();
        }
//        list.stream().filter(elementWrapper -> elementWrapper.getElement().getText().contains(t)).forEach(ElementWrapper::click);
    }

    @Test(dependsOnMethods = "patientSelectDoctor", description = "Видеосвязь пациента")
    public void patientTrueConf(){
        preloader.waitForPageLoaded();
        visibility(portal.btn_onlineAppoint).click();
        preloader.waitForPageLoaded();
        visibility(portal.btn_submitContract).click();
        preloader.isLoadingDesktopPortal();
        switchToFrame(visibility(portal.frame_trueConf));
        if(exist(portal.btn_hangUp, 5)){
            preloader.waitForPageLoaded();
            jsClick(visibility(portal.btn_hangUp));
        }
        else {
            preloader.waitForPageLoaded();
            visibility(portal.btn_connect).click();
            jsClick(visibility(portal.btn_hangUp));
        }
//        if(exist(portal.btn_connect, 10)){
//            visibility(portal.btn_connect).click();
//        }
//        visibility(Portal.btn_participants).click();
//        jsClick(visibility(portal.btn_hangUp));
//        visibility(Portal.btn_hangUp).click();
    }

    @Test(dependsOnMethods = "patientTrueConf", description = "Доктор выходит из видео")
    public void doctorDisconnect(){
        switchToWindow(tabs2.get(1));
        switchToFrame(visibility(portal.frame_trueConf));
        preloader.waitForPageLoaded();
        jsClick(visibility(portal.btn_hangUp));
//        visibility(Portal.btn_hangUp).click();
        crossbrowsing.setScore(Status.PASS.label);
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }

    public void setDataProviderPortal(DataProviderPortal dataProviderPortal) {
        this.dataProviderPortal = dataProviderPortal;
    }

    public void setPreloader(Preloader preloader) {
        this.preloader = preloader;
    }

    public void setCrossbrowsing(API crossbrowsing) {
        this.crossbrowsing = crossbrowsing;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setgBrowser(String gBrowser) {
        this.gBrowser = gBrowser;
    }

    public DataProviderPortal getDataProviderPortal() {
        return dataProviderPortal;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setConfirmModal(boolean confirmModal) {
        this.confirmModal = confirmModal;
    }
}
