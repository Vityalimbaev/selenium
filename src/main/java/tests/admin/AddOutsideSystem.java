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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AdminPage;

public class AddOutsideSystem extends DriverWaitWrapper {

    private Preloader preloader;
    private DataProviderAdmin dataProviderAdmin;
    private API crossbrowsing;
    private AdminPage admin = new AdminPage();
    private Actions action;
    private String gBrowser;
    private String date;

    private String systemName = "Test auto-test";

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderAdmin = (DataProviderAdmin) DataProviderFactory.get(System.getProperty("mode"), NameProject.ADMIN);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] AddOutputSystem " + browser);
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

    @Test(dependsOnMethods = "authorization", description = "Добавление внешней системы")
    public void addOutputSystem(){
        preloader.waitForPageLoaded();
        invisible(admin.spinner, 10);
        visibility(XpathWrapper.contains("Внешние системы", "кнопка внешних систем").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Добавить", "кнопка Добавить").addToXpath("[last()]")).click();
        visibility(admin.input_outputSystemName).sendKeys(systemName);
        visibility(admin.btn_newSecretKey).click();
        visibility(XpathWrapper.contains("Разрешения", "кнопка разрешения")).click();
        visibility(admin.checkbox_webInfoclinicAllows.addToXpath("[1]")).click();
        visibility(admin.checkbox_webInfoclinicAllows.addToXpath("[5]")).click();
        visibility(admin.checkbox_webInfoclinicAllows.addToXpath("[6]")).click();
        visibility(admin.checkbox_webInfoclinicWebsites).click();
        visibility(XpathWrapper.contains("Сохранить", "Кнопка сохранения")).click();
    }

    @Test(dependsOnMethods = "addOutputSystem", description = "Добавление тарифного плана")
    public void addTariffPlan(){
        visibility(XpathWrapper.contains("Тарифные планы", "кнопка тарифных планов").addToXpath("[last()-1]")).click();
        visibility(admin.input_tp_outputSystemName).click();
        while (!visibility(admin.input_tp_outputSystemName).getAttribute("value").equals(systemName)){
            action.sendKeys(Keys.DOWN).perform();
            action.sendKeys(Keys.DOWN).perform();
            action.sendKeys(Keys.ENTER).perform();
        }
        visibility(XpathWrapper.contains("Добавить", "кнопка добавить").addToXpath("[2]")).click();
//        visibility(admin.input_tp_outputSystemName).sendKeys( Keys.chord(Keys.CONTROL, Keys.valueOf("A")) + systemName + Keys.ENTER);
        visibility(admin.input_tp_outputHostId).sendKeys(Keys.DOWN, Keys.ENTER);
        visibility(admin.input_tp_info.addToXpath("[1]")).sendKeys("31.08.2020");
        visibility(admin.input_tp_info.addToXpath("[2]")).sendKeys("31.08.2030");
        visibility(admin.input_tp_info.addToXpath("[3]")).sendKeys("100");
        visibility(admin.input_tp_info.addToXpath("[4]")).sendKeys("147896325");
        visibility(admin.input_tp_info.addToXpath("[5]")).sendKeys("12345");
        visibility(XpathWrapper.contains("Добавить","кнопка добавить").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Сохранить","кнопка сохранить").addToXpath("[last()]")).click();

        preloader.waitForPageLoaded();
        invisible(admin.spinner, 10);
        action.doubleClick(visibility(XpathWrapper.contains("147896325", "строка с ИНН 147896325")).getElement()).perform();
        visibility(XpathWrapper.contains("147896325", "строка с ИНН 147896325").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Удалить","Кнопка удаления").addToXpath("[2]")).click();
        visibility(admin.btn_yes).click();
    }

    @Test(dependsOnMethods = "addTariffPlan", description = "Удаление внешней системы")
    public void deleteOutputSystem(){
        visibility(XpathWrapper.contains("Внешние системы", "кнопка внешние системы").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains(systemName, "строка с названием системы")).click();
        visibility(XpathWrapper.contains("Удалить","Кнопка удаления")).click();
        visibility(admin.btn_yes).click();
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
