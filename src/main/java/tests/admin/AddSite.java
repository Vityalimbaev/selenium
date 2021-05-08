package tests.admin;

import connection.Connector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderAdmin;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AdminPage;

public class AddSite extends DriverWaitWrapper {
    private Preloader preloader;
    private DataProviderAdmin dataProviderAdmin;
    private API crossbrowsing;
    private AdminPage admin = new AdminPage();
    private Actions action;
    private String gBrowser;
    private String date;

    private String virtualAddress = "test.dev-drclinics.infoclinica.ru";
    private String virtualAddress2 = "test.dev-drclinics.infoclinica.ru:9000";
    private String serviceAddress = "http://drclinics.infoclinica.lan:8484/rest/portHl7Message";
    private String virtualAddress3 = "http://drclinics.infoclinica.lan:8802";
    private String siteName = "Test auto-test";

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderAdmin = (DataProviderAdmin) DataProviderFactory.get(System.getProperty("mode"), NameProject.ADMIN);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] AddSite " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderAdmin.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
        gBrowser = browser;
        action = new Actions(getDRIVER());
    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(admin.input_login).sendKeys(dataProviderAdmin.getAdminName());
        visibility(admin.input_password).sendKeys(dataProviderAdmin.getAdminPassword());
        visibility(admin.btn_login).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Добавление сайта")
    public void addSite(){
        preloader.waitForPageLoaded();
        invisible(admin.spinner, 10);
        visibility(XpathWrapper.contains("Виртуальные сайты", "кнопка виртуальных сайтов").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Добавить","Кнопка добавить")).click();
        visibility(admin.btn_pencil).click();
        visibility(XpathWrapper.contains("Добавить сервис","кнопка добавления сервиса")).click();
        action.doubleClick(visibility(admin.btn_input_virtualAddress).getElement()).perform();
        visibility(admin.input_virtualAddress).sendKeys(this.virtualAddress + Keys.ENTER);
        visibility(XpathWrapper.contains("Применить", "кнопка подтверждения").addToXpath("[last()]")).click();
        visibility(admin.btn_pencil).click();
        visibility(XpathWrapper.contains("Добавить сервис","кнопка добавления сервиса")).click();
        action.doubleClick(visibility(admin.btn_input_virtualAddress.addToXpath("[5]")).getElement()).perform();
        visibility(admin.input_virtualAddress.addToXpath("[last()]")).sendKeys(this.virtualAddress2 + Keys.ENTER);
        visibility(XpathWrapper.contains("Применить", "кнопка подтверждения").addToXpath("[last()]")).click();

        visibility(admin.input_serviceUrl).sendKeys(this.serviceAddress);

        visibility(admin.btn_pencil.addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Добавить сервис","кнопка добавления сервиса")).click();
        action.doubleClick(visibility(admin.btn_input_virtualAddress).getElement()).perform();
        visibility(admin.input_virtualAddress).sendKeys(this.virtualAddress3 + Keys.ENTER);
        visibility(XpathWrapper.contains("Применить", "кнопка подтверждения").addToXpath("[last()]")).click();
        visibility(admin.checkbox_info.addToXpath("[last()-1]")).click();
        visibility(admin.checkbox_info.addToXpath("[last()]")).click();
        action.doubleClick(visibility(admin.checkbox_tester).getElement()).perform();//TODO: не нажимается


        visibility(XpathWrapper.contains("Тарифные опции и модули","кнопка тарифные опции и модули")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[3]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[10]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[12]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[12]")).click();//??
        visibility(admin.checkbox_tarifOptions.addToXpath("[15]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[16]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[16]")).click();//??
        visibility(admin.checkbox_tarifOptions.addToXpath("[18]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[20]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[21]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[22]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[23]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[24]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[24]")).click();//??
        visibility(admin.checkbox_tarifOptions.addToXpath("[25]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[26]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[27]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[28]")).click();
        visibility(admin.checkbox_tarifOptions.addToXpath("[29]")).click();


        visibility(XpathWrapper.contains("Параметры персонализации","кнопка Параметры персонализации")).click();
        visibility(admin.input_internalName).sendKeys(this.siteName);
        visibility(admin.input_title).sendKeys(this.siteName);
        visibility(admin.checkbox_moduleController.addToXpath("[1]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[2]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[4]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[5]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[6]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[7]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[8]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[9]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[13]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[14]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[17]")).click();
        visibility(admin.checkbox_moduleController.addToXpath("[19]")).click();


        visibility(XpathWrapper.contains("Вперед", "кнопка перехода к параметрам авторизации")).click();
        visibility(admin.select_addPatientMode).click();
        action.sendKeys(Keys.ENTER).perform();
        visibility(admin.select_confirmPersonalData).click();
        action.sendKeys(Keys.DOWN).perform();
        action.sendKeys(Keys.DOWN).perform();
        action.sendKeys(Keys.ENTER).perform();

        visibility(XpathWrapper.contains("Вперед", "кнопка перехода к настройке отправки уведомлений")).click();
        visibility(admin.input_senderName).sendKeys(this.siteName);
        visibility(XpathWrapper.contains("проставить по умолчанию", "кнопка выставления макета по умолчанию")).click();

        visibility(XpathWrapper.contains("Аналитика и карты","кнопка аналитика и карты")).click();
        visibility(admin.checkbox_mapNanalyst).click();

        visibility(XpathWrapper.contains("Вперед", "кнопка перехода к настройке веб инфоклиники")).click();
        visibility(admin.checkbox_enableWebInfoclinic).click();
        visibility(admin.input_containerName).sendKeys(this.siteName);
        visibility(admin.input_countUsers).sendKeys("0");
        visibility(admin.input_sessionTimeout).sendKeys("60");

        if(exist(XpathWrapper.contains("Вперед", "кнопка перехода к настройке веб инфоклиники"),4)) {
            visibility(XpathWrapper.contains("Вперед", "кнопка перехода к настройке веб инфоклиники")).click();
        }
        visibility(XpathWrapper.contains("Сохранить", "Кнопка сохранения")).click();
    }

    @Test(dependsOnMethods = "addSite", description = "Проверка сайта")
    public void checkSite(){
        action.doubleClick(visibility(XpathWrapper.contains(this.siteName,"сайт в списке")).getElement()).perform();
        Assert.assertTrue(visibility(admin.input_hostNames).getAttribute("value").contains(virtualAddress + ", " + virtualAddress2));
        Assert.assertTrue(visibility(admin.input_serviceUrl).getAttribute("value").contains(serviceAddress));
        Assert.assertTrue(visibility(admin.input_serviceReportUrl).getAttribute("value").contains(virtualAddress3));
        visibility(XpathWrapper.contains("Отмена", "кнопка отмены").addToXpath("[last()]")).click();
    }

    @Test(alwaysRun = true, dependsOnMethods = "checkSite", description = "Удаление сайта")
    public void deleteSite(){
        visibility(XpathWrapper.contains(this.siteName,"сайт в списке")).click();
        visibility(XpathWrapper.contains("Удалить","Кнопка удаления")).click();
        if(exist(XpathWrapper.contains("OK", "кнопка подтверждения удаления"), 4)) {
            visibility(XpathWrapper.contains("OK", "кнопка подтверждения удаления")).click();
        }
        visibility(admin.btn_yes).click();
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
