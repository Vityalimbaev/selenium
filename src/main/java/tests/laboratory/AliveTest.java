package tests.laboratory;

import connection.Connector;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderLaboratory;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Laboratory;

public class AliveTest extends DriverWaitWrapper {

    private DataProviderLaboratory dataProviderLab;
    private Preloader preloader;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderLab = (DataProviderLaboratory) DataProviderFactory.get(System.getProperty("mode"), NameProject.LABORATORY);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] Lab " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderLab.getUrl());
        preloader = new Preloader(getDRIVER());
    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(Laboratory.input_login).sendKeys(dataProviderLab.getUser_login());
        visibility(Laboratory.input_password).sendKeys(dataProviderLab.getUser_password());
        visibility(Laboratory.btn_login).click();
//        preloader.isLoading();
        invisible(Laboratory.preloader, 90);
        visibility(Laboratory.btn_login).click();//Для подразделения
        invisible(Laboratory.preloader, 90);
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            found = (exist(Laboratory.btn_journal, 10));
            getDRIVER().navigate().refresh();
            if(found) break;
        }
        Assert.assertTrue(found);
    }


}
