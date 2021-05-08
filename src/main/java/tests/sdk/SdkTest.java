package tests.sdk;

import connection.Connector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderSDK;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.SdkPage;

import java.util.ArrayList;
import java.util.UUID;

public class SdkTest extends DriverWaitWrapper {

    private Preloader preloader;
    private ArrayList<String> tabs2;
    private DataProviderSDK dataProviderSDK;
    private SdkPage sdkPage = new SdkPage();
    private boolean isDev;
    private API crossbrowsing;
    private String gBrowser;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderSDK = (DataProviderSDK) DataProviderFactory.get(System.getProperty("mode"), NameProject.SDK);
//        mongoConnector = new MongoConnector("192.168.201.65", 27017);
//        mongoConnector.setWebSite("Инфоклиника.RU");
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "[" + System.getProperty("mode") + "] SDK " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderSDK.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
        gBrowser = browser;
        isDev = System.getProperty("mode").toLowerCase().contains("dev");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(sdkPage.btn).click();
        preloader.waitForPageLoaded();
        invisible(sdkPage.preloader, 60);
        Assert.assertTrue(exist(XpathWrapper.contains("authenticated : true", "пользователь авторизован"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "authorization", description = "Новый пациент")
    public void newPatient(){
        closeModal();
        String email = UUID.randomUUID().toString().replace("-","")+"@auto.test";
        visibility(sdkPage.input.getReplaceContext("Регистрация нового пациента").addToXpath("[5]")).sendKeys(Keys.chord(Keys.CONTROL, "a") + email);
        visibility(sdkPage.btn.addToXpath("[5]")).click();
        Assert.assertTrue(exist(XpathWrapper.contains("success : true", "пациент создан"),10));
    }

    @Test(dependsOnMethods = "newPatient", description = "Новый пациент (завершение регистрации)")
    public void completeNewPatient(){
        closeModal();
        visibility(sdkPage.input.getReplaceContext("завершение регистрации").addToXpath("[2]")).sendKeys("000000");
        visibility(sdkPage.btn.addToXpath("[6]")).click();
        Assert.assertTrue(exist(XpathWrapper.contains("запись на прием через портал работает в ограниченном режиме. Вы можете записаться на прием не более одного раза", "пациент создан"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "newPatient", description = "Изменение профиля")
    public void editProfile(){
        closeModal();
        visibility(sdkPage.input.getReplaceContext("Изменение параметров").addToXpath("[2]")).sendKeys("1234567");
        visibility(sdkPage.btn.addToXpath("[7]")).click();
        if(isDev) Assert.assertTrue(exist(XpathWrapper.contains("Измененние параметров учетной записи прошли успешно", "изменения успешны"),10));
        else Assert.assertTrue(exist(XpathWrapper.contains("Изменение номера телефона запрещено", "изменения успешны"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "editProfile", description = "Получение расписания")
    public void loadSchedule(){
        closeModal();
        visibility(sdkPage.btn.addToXpath("[8]")).click();
        Assert.assertTrue(exist(XpathWrapper.contains("success : true", "сетка получена"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "loadSchedule", description = "Запись пациента")
    public void appoint(){
        closeModal();
        visibility(sdkPage.input.getReplaceContext("Запись на прием").addToXpath("[1]")).sendKeys("08:30");
        visibility(sdkPage.input.getReplaceContext("Запись на прием").addToXpath("[2]")).sendKeys("09:00");
        visibility(sdkPage.input.getReplaceContext("Запись на прием").addToXpath("[3]")).sendKeys("20200831");
        visibility(sdkPage.input.getReplaceContext("Запись на прием").addToXpath("[4]")).sendKeys("10000007");
        visibility(sdkPage.input.getReplaceContext("Запись на прием").addToXpath("[5]")).sendKeys("1");
        visibility(sdkPage.input.getReplaceContext("Запись на прием").addToXpath("[7]")).sendKeys("10005702");
        jsClick(visibility(sdkPage.btn.addToXpath("[9]")));
        Assert.assertTrue(exist(XpathWrapper.contains("выбранное время было недавно занято", "время занято"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "appoint", description = "Мои записи")
    public void myAppoints(){
        closeModal();
        visibility(sdkPage.btn.addToXpath("[11]")).click();
        if(isDev) Assert.assertTrue(exist(XpathWrapper.contains("recordsFiltered : 10", "Записи получены"),10));
        else Assert.assertTrue(exist(XpathWrapper.contains("recordsFiltered : 40", "Записи получены"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "myAppoints", description = "Список протоколов")
    public void myProtocols(){
        closeModal();
        visibility(sdkPage.btn.addToXpath("[12]")).click();
        if(isDev) Assert.assertTrue(exist(XpathWrapper.contains("recordsFiltered : 40", "Записи получены"),10));
        else Assert.assertTrue(exist(XpathWrapper.contains("recordsFiltered : 2", "Записи получены"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "myProtocols", description = "Отображение протокола")
    public void selectProtocol(){
        closeModal();
        visibility(sdkPage.input.getReplaceContext("Отображение протокола").addToXpath("[1]")).sendKeys(dataProviderSDK.getProtocolId());
        visibility(sdkPage.input.getReplaceContext("Отображение протокола").addToXpath("[2]")).sendKeys(dataProviderSDK.getTreatcode());
        visibility(sdkPage.btn.addToXpath("[13]")).click();
        Assert.assertFalse(exist(XpathWrapper.contains("Запрашиваемая страница не найдена", "Страница не найдена"),10));
    }


    @Test(alwaysRun = true, dependsOnMethods = "selectProtocol", description = "Авторизация ESIA")
    public void esia(){
        closeModal();
        jsClick(visibility(sdkPage.btn.addToXpath("[2]")));
        Assert.assertTrue(exist(XpathWrapper.contains("authenticated : true", "пользователь авторизован"),10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "esia", description = "Авторизация сотрудника")
    public void employeeAuth(){
        closeModal();
        if(!isDev) visibility(sdkPage.input.getReplaceContext("авторизация сотрудника").addToXpath("[2]")).sendKeys(Keys.chord(Keys.BACK_SPACE, Keys.BACK_SPACE));
        jsClick(visibility(sdkPage.btn.addToXpath("[3]")));
        Assert.assertTrue(exist(XpathWrapper.contains("authenticated : true", "пользователь авторизован"),10));
    }

    @Test(dependsOnMethods = "employeeAuth", description = "Выбор пациента")
    public void selectPatient(){
        closeModal();
        jsClick(visibility(sdkPage.btn.addToXpath("[4]")));
        Assert.assertTrue(exist(XpathWrapper.contains(" \"patientName\": \""+ dataProviderSDK.getPatientName() +"\"", "пациент найден"),10));
    }


    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }

    private void closeModal(){
        try {
            jsClick(visibility(sdkPage.back));
        }catch (Exception e) {
            e.printStackTrace();
            if (exist(new XpathWrapper("//div[@class='wr-sdk-widget-modal__container']", "окно с протоколом"), 3)) {
                jsClick(visibility(new XpathWrapper("//div[@class='wr-sdk-widget-modal__container']", "окно с протоколом")));
            }
            else {
                jsClick(visibility(sdkPage.back_fail));
            }
        }
    }
}
