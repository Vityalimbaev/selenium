package tests.mobile;

import connection.Connector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderPortal;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.ElementWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.MobilePortal;
import pages.Portal;
import tests.portal.Patient;

import java.util.ArrayList;
import java.util.List;

public class Visit extends DriverWaitWrapper {
    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderPortal dataProviderPortal;
    private API crossbrowsing;
    private Portal portal = new Portal();
    private MobilePortal mobilePortal = new MobilePortal();
    private Patient patientTest;
    private boolean online;
    private String date;
    private String time;
    private String services;

    @Parameters(value = {"browser", "version", "platform", "platformVersion", "device"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform, String platformVersion, String device) throws Exception {
        dataProviderPortal = (DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PORTAL);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform, device, platformVersion);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] MobileVisit " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderPortal.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
        online = false;
    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(portal.input_login).sendKeys(dataProviderPortal.getUser_login());
        visibility(portal.input_password).sendKeys(dataProviderPortal.getUser_password());
        jsClick(new ElementWrapper(getDRIVER().findElement(By.xpath(mobilePortal.checkbox.getXpath_String())), mobilePortal.checkbox));
        visibility(XpathWrapper.contains("Войти","Кнопка входа")).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Выбор клиники")
    public void selectClinic(){
        invisible(mobilePortal.preloader, 30);
        XpathWrapper btn = online ? XpathWrapper.contains("Запись на онлайн прием","кнопка выбора клиники") : XpathWrapper.contains(dataProviderPortal.getClinic(),"кнопка выбора клиники");
        jsScroll(visibility(btn));
        invisible(mobilePortal.preloader, 30);
        visibility(btn).click();
    }
    //
    @Test(dependsOnMethods = "selectClinic", description = "Выбор специализации")
    public void selectSpecialty(){
        invisible(mobilePortal.preloader, 30);
        XpathWrapper btn = XpathWrapper.contains(online ? dataProviderPortal.getSpecialty_online() : dataProviderPortal.getSpecialty(),"кнопка выбора специальности");
        jsScroll(visibility(btn));
        visibility(btn).click();
    }
    //
    @Test(dependsOnMethods = "selectSpecialty", description = "Выбор врача")
    public void selectDoctor() {
        invisible(mobilePortal.preloader, 30);
        XpathWrapper btn = XpathWrapper.contains(online ? dataProviderPortal.getName_doctor_online() : dataProviderPortal.getName_doctor(),"выбор врача");
        jsScroll(visibility(btn));
        visibility(btn).click();
    }

    @Test(dependsOnMethods = "selectDoctor", description = "Выбор даты и времени")
    public void selectDateAndTime(){
        invisible(mobilePortal.preloader, 30);
        boolean appointed = false;
        int i = 1;
        while (!appointed) {
            visibility(mobilePortal.date.addToXpath("["+i+"]")).click();
            invisible(mobilePortal.preloader, 50);
            visibility(mobilePortal.time).click();
            invisible(mobilePortal.preloader, 30);
//            if (exist(mobilePortal.checkbox.addToXpath("[last()]"), 4)) {
//                visibility(mobilePortal.checkbox.addToXpath("[last()]")).click();
            if(exist(XpathWrapper.contains("Рентген", "Рентген"), 4)){
                visibility(XpathWrapper.contains("Рентген", "услуга рентгена")).click();
                visibility(XpathWrapper.contains("Выбрать услуги", "кнопка Выбора услуг")).click();
            }
            date = visibility(mobilePortal.appointInfo.addToXpath("[last()-2]")).getText();
            time = visibility(mobilePortal.appointInfo.addToXpath("[last()-1]")).getText();
            services = visibility(mobilePortal.appointInfo.addToXpath("[last()]")).getText();
            visibility(portal.btn_submit).click();
            invisible(mobilePortal.preloader, 30);
            i++;
            if(!exist(XpathWrapper.contains("уже записаны", "Вы уже записаны на выбранный интервал"),4)) appointed = true;
            else {
                XpathWrapper btn = XpathWrapper.contains(online ? dataProviderPortal.getName_doctor_online() : dataProviderPortal.getName_doctor(),"выбор врача");
                if(exist(btn, 4)) visibility(btn).click();
            }
        }
        visibility(XpathWrapper.contains("Оплатить", "кнопка оплаты")).click();
        visibility(XpathWrapper.contains("Согласен","кнопка согласия с договором")).click();
        invisible(mobilePortal.preloader, 30);
    }

    @Test(dependsOnMethods = "selectDateAndTime", description = "Оплата")
    public void payment(){
        switchToFrame(visibility(portal.frame_payment, 120));
        visibility(portal.input_cardnumber).sendKeys("5555555555554477");
        visibility(portal.input_cardexpire).sendKeys("1121");
        visibility(portal.input_cardcvc).sendKeys("000");
        jsClick(visibility(portal.btn_submitPayment));
        switchToParentFrame();
        visibility(portal.input_sms).sendKeys("123");
        visibility(portal.btn_3dSecureConfirm).click();
        invisible(portal.preloaderKassa, 20);
    }

    @Test(dependsOnMethods = "payment", description = "Проверка записи")
    public void checkServices(){
        visibility(mobilePortal.btn_hamburger).click();
        invisible(mobilePortal.preloader, 30);
        visibility(XpathWrapper.contains("Мои записи", "кнопка мои записи")).click();
        boolean found = false;
        while (!found) {
            invisible(mobilePortal.preloader, 30);
            List<ElementWrapper> list = getElements(mobilePortal.date);
            for (ElementWrapper element : list) {
                if (element.getText().replace(" ", "").contains(date + "," + time)) {
                    found = true;
                    element.click();
                    break;
                }
            }
            if(!found) visibility(portal.btn_next).click();
        }
        invisible(mobilePortal.preloader, 30);
        visibility(XpathWrapper.contains("Информация о записи", "кнопка Информации о записи")).click();
        invisible(mobilePortal.preloader, 30);
//        Assert.assertTrue(exist(XpathWrapper.contains(services, "выбранная услуга"), 10));
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }

    public DataProviderPortal getDataProviderPortal() {
        return dataProviderPortal;
    }

    public void setDataProviderPortal(DataProviderPortal dataProviderPortal) {
        this.dataProviderPortal = dataProviderPortal;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    public MobilePortal getMobilePortal() {
        return mobilePortal;
    }

    public void setMobilePortal(MobilePortal mobilePortal) {
        this.mobilePortal = mobilePortal;
    }
}
