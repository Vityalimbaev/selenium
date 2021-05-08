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

import java.util.ArrayList;

public class Buyer extends DriverWaitWrapper {

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
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] Buyer " + browser);
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

    @Test(dependsOnMethods = "authorization", description = "Заказ услуги из прейскуранта")
    public void openPricelist(){
        visibility(XpathWrapper.contains("Прейскурант","кнопка прейскуранта")).click();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        XpathWrapper btn = XpathWrapper.contains(dataProviderPortal.getSpecialty(),"кнопка выбора специальности");
        visibility(btn).click();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        visibility(XpathWrapper.contains("Купить","кнопка покупки")).click();
        visibility(XpathWrapper.contains("Оформить заказ", "Кнопка оформления заказа")).click();
    }

    @Test(dependsOnMethods = "openPricelist", description = "Оплата")
    public void payment(){
        patientTest.payment();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        Assert.assertTrue(getDRIVER().getCurrentUrl().contains("payments"));
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
