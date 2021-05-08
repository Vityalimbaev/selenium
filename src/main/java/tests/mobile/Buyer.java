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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.MobilePortal;
import pages.Portal;
import tests.portal.Patient;

import java.util.ArrayList;

public class Buyer extends DriverWaitWrapper {
    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderPortal dataProviderPortal;
    private API crossbrowsing;
    private Portal portal = new Portal();
    private MobilePortal mobilePortal = new MobilePortal();
    private Patient patientTest;

    @Parameters(value = {"browser", "version", "platform", "platformVersion", "device"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform, String platformVersion, String device) throws Exception {
        dataProviderPortal = (DataProviderPortal) DataProviderFactory.get(System.getProperty("mode"), NameProject.PORTAL);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform, device, platformVersion);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] MobileBuyer " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderPortal.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));

    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(portal.input_login).sendKeys(dataProviderPortal.getUser_login());
        visibility(portal.input_password).sendKeys(dataProviderPortal.getUser_password());
        jsClick(new ElementWrapper(getDRIVER().findElement(By.xpath(mobilePortal.checkbox.getXpath_String())), mobilePortal.checkbox));
        visibility(XpathWrapper.contains("Войти","Кнопка входа")).click();
    }

    @Test(dependsOnMethods = "authorization", description = "Покупка услуг")
    public void addOffer(){
        invisible(mobilePortal.preloader, 10);
        visibility(mobilePortal.btn_hamburger).click();
        ElementWrapper pricelist = visibility(XpathWrapper.contains("Прейскурант", "кнопка прейскуранта"));
        jsScroll(pricelist);
        pricelist.click();
        invisible(mobilePortal.preloader, 10);
        visibility(mobilePortal.btn_pricelist_record).click();
        invisible(mobilePortal.preloader, 10);
        visibility(mobilePortal.btn_buy).click();
        invisible(mobilePortal.preloader, 10);
        visibility(XpathWrapper.contains("Оформить заказ","кнопка подтверждения заказа")).click();
        invisible(mobilePortal.preloader, 10);
        visibility(XpathWrapper.contains("Согласен","кнопка согласия с договором")).click();
        invisible(mobilePortal.preloader, 10);
    }

    @Test(dependsOnMethods = "addOffer", description = "Оплата")
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

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
