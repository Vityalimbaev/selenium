package tests.laboratory;

import connection.Connector;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.enums.NameProject;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderLaboratory;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.Laboratory;

public class Basic extends DriverWaitWrapper {
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
    }

//    @Test(dependsOnMethods = "authorization")
//    public void findByName(){
//        visibility(Laboratory.btn_users).click();
//        visibility(Laboratory.input_searchByName).sendKeys("Широкий Семен Владимирович" + Keys.ENTER);
//        Assert.assertTrue(exist(XpathWrapper.contains("Широкий Семен Владимирович", "пользователь найден"),90));
//        visibility(Laboratory.btn_closeTab).click();
//    }

    @Test(dependsOnMethods = "authorization", description = "Открыть окно добавление записи")
    public void openAddJournalTask(){//тест упадет, если в прошлый раз была ошибка удаления
        visibility(Laboratory.btn_journal).click();
        visibility(Laboratory.btn_add).click();
        visibility(Laboratory.btn_choosePatient).click();
//        visibility(Laboratory.input_order_searchByName).sendKeys("Иванво Иван Иванович" + Keys.ENTER);
//        visibility(XpathWrapper.contains("Иванво Иван Иванович","пользователь")).click();
//        visibility(Laboratory.btn_select).click();
//        visibility(Laboratory.btn_chooseOrg).click();
//        visibility(Laboratory.btn_selectOrg).click();
//        visibility(Laboratory.btn_select).click();
//        visibility(Laboratory.btn_addService).click();
//        visibility(Laboratory.input_searchServiceByName).sendKeys("ОАК" + Keys.ENTER);
//        visibility(XpathWrapper.contains("ОАК","услуга по имени")).click();
//        visibility(Laboratory.btn_moveService).click();
//        visibility(Laboratory.btn_select).click();
//        visibility(Laboratory.btn_save).click();
    }

    @Test(dependsOnMethods = "openAddJournalTask", description = "Поиск по имени")
    public void findByName() {
        preloader.waitForPageLoaded();
        visibility(Laboratory.input_order_searchByYOB).sendKeys(dataProviderLab.getUserYOB());
        visibility(Laboratory.input_order_searchByName).sendKeys(dataProviderLab.getUsername() + Keys.ENTER);
        Assert.assertTrue(exist(Laboratory.userExist.getReplaceContext(dataProviderLab.getUsername()), 10));
        preloader.waitForPageLoaded();
        visibility(Laboratory.input_order_searchByName).getElement().clear();
        visibility(Laboratory.input_order_searchByYOB).getElement().clear();

    }

    @Test(dependsOnMethods = "findByName", description = "Поиск по PCODE")
    public void findByPcode() {
        preloader.waitForPageLoaded();
        visibility(Laboratory.input_order_searchByPcode).sendKeys(dataProviderLab.getPcode() + Keys.ENTER);
        Assert.assertTrue(exist(Laboratory.userExist.getReplaceContext(dataProviderLab.getUsername()), 10));
        preloader.waitForPageLoaded();
        visibility(Laboratory.userExist.getReplaceContext(dataProviderLab.getUsername())).click();
        visibility(Laboratory.btn_select).click();
    }

    @Test(dependsOnMethods = "findByPcode", description = "Подвердить добавление записи")
    public void confirmAddJournalTask(){
        visibility(Laboratory.btn_chooseOrg).click();
        visibility(Laboratory.btn_selectOrg).click();
        visibility(Laboratory.btn_select).click();
        visibility(Laboratory.btn_addService).click();
        visibility(Laboratory.input_searchServiceByName).sendKeys("ОАК" + Keys.ENTER);
        exist(XpathWrapper.contains("ОАК","услуга по имени"), 10);
        visibility(XpathWrapper.contains("ОАК","услуга по имени")).click();
        visibility(Laboratory.btn_moveService).click();
        visibility(Laboratory.btn_select).click();
        preloader.waitForPageLoaded();
        visibility(Laboratory.btn_save).click();
        exist(Laboratory.loadmask, 5);
        invisible(Laboratory.loadmask, 10);
        visibility(Laboratory.btn_save).click();
    }

    @Test(dependsOnMethods = "confirmAddJournalTask", description = "Удаление")
    public void delete(){
        preloader.waitForPageLoaded();
        invisible(Laboratory.loadmask, 10);
        visibility(Laboratory.btn_delete).click();
        visibility(Laboratory.btn_confirmDelete).click();
        Assert.assertFalse(exist(Laboratory.info_noPermissions, 10));
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
