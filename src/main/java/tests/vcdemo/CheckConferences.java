package tests.vcdemo;

import connection.Connector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderPortal;
import models.dataproviders.DataProviderVcDemo;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.VcDemo;

import java.io.File;
import java.util.ArrayList;

public class CheckConferences extends DriverWaitWrapper {
    private DataProviderVcDemo dataProviderVcDemo;
    private Preloader preloader;
    private API crossbrowsing;
    private VcDemo vcDemo = new VcDemo();
    private ArrayList<String> tabs2;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderVcDemo = (DataProviderVcDemo) DataProviderFactory.get(System.getProperty("mode"), NameProject.VCDEMO);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "[" + System.getProperty("mode") + "] VC-DEMO " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderVcDemo.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(vcDemo.input_login).sendKeys(dataProviderVcDemo.getUser_login());
        visibility(vcDemo.input_password).sendKeys(dataProviderVcDemo.getUser_password());
        visibility(vcDemo.btn_login).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Проверка журнала")
    public void checkJournal() {
        visibility(XpathWrapper.contains("Записи конференций","кнопка записи конференций")).click();
        visibility(vcDemo.input_conferenceID).sendKeys("\\c\\s-98c9b4-8512dd61" + Keys.ENTER);
    }

    @Test(dependsOnMethods = "checkJournal", description = "Проверка загрузки")
    public void checkDownload(){
        visibility(vcDemo.btn_download).click();
        exist(XpathWrapper.contains("\"error\":", "ошибка"),3);
        tabs2 = new ArrayList<String> (getDRIVER().getWindowHandles());
        switchToWindow(tabs2.get(tabs2.size()-1));
        Assert.assertFalse(exist(XpathWrapper.contains("\"error\":", "ошибка"),10));
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
