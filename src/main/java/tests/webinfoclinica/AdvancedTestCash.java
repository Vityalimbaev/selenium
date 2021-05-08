package tests.webinfoclinica;

import connection.Connector;
import crossbrowsing.API;
import factories.BrowserFactory;
import factories.DataProviderFactory;
import models.Preloader;
import models.browsers.Browser;
import models.dataproviders.DataProviderWebInfoClinica;
import models.enums.NameProject;
import models.wrappers.DriverWaitWrapper;
import models.wrappers.ElementWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.WebInfoclinic;

import java.util.ArrayList;

public class AdvancedTestCash extends DriverWaitWrapper {
    private Preloader preloader;
    private DataProviderWebInfoClinica dataProviderWebInfoClinica;
    private API crossbrowsing;
    private WebInfoclinic infoclinic = new WebInfoclinic();
    private Actions action;
    private ArrayList<String> tabs2;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize(String browser, String version, String platform) throws Exception {
        dataProviderWebInfoClinica = (DataProviderWebInfoClinica) DataProviderFactory.get(System.getProperty("mode"), NameProject.WEBINFOCLINICA);
        Browser currentBrowser = BrowserFactory.create(browser, version, platform);
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] Promo " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderWebInfoClinica.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));
        action = new Actions(getDRIVER());
    }

    @Test(description = "Авторизация")
    public void authorization(){
        visibility(infoclinic.input_login).sendKeys(dataProviderWebInfoClinica.getUser_login());
        visibility(infoclinic.input_password).sendKeys(dataProviderWebInfoClinica.getUser_password());
        visibility(XpathWrapper.contains("Войти", "Кнопка входа")).click();
    }

    @Test(alwaysRun = true, dependsOnMethods = "authorization", description = "Поиск пациента")
    public void searchPatient(){
        preloader.waitForPageLoaded();
        invisible(infoclinic.spinner, 10);
        visibility(XpathWrapper.contains("Картотека", "кнопка Картотеки")).click();
        visibility(infoclinic.input_liveSearch).sendKeys("Автотест Наличный Иванович" + Keys.ENTER);
    }

    @Test(alwaysRun = true, dependsOnMethods = "searchPatient", description = "Добавление пациента")
    public void addPatient(){
        visibility(XpathWrapper.contains("Добавить", "кнопка добавить")).click();
        visibility(infoclinic.input_requisites.addToXpath("[2]")).sendKeys("Наличный");
        visibility(infoclinic.input_requisites.addToXpath("[4]")).sendKeys("Автотест");
        visibility(infoclinic.input_requisites.addToXpath("[5]")).sendKeys("Иванович");
        visibility(infoclinic.input_requisites.addToXpath("[6]")).sendKeys("15.09.2020");
        visibility(infoclinic.input_requisites.addToXpath("[7]")).click();
        visibility(infoclinic.input_requisites.addToXpath("[9]")).sendKeys("55555555");
        visibility(infoclinic.input_requisites.addToXpath("[12]")).sendKeys("Имя Родителей");
        visibility(infoclinic.btn_search.addToXpath("[2]")).click();
        visibility(XpathWrapper.contains("Вывеска", "кнопка выбора рекламы")).click();
        visibility(XpathWrapper.contains("Выбрать", "кнопка выбора")).click();
        visibility(infoclinic.input_requisites.addToXpath("[15]")).sendKeys("00000000000");
        visibility(infoclinic.input_requisites.addToXpath("[16]")).click();
        action.sendKeys(Keys.DOWN).perform();
        action.sendKeys(Keys.DOWN).perform();
        action.sendKeys(Keys.ENTER).perform();
        visibility(infoclinic.input_requisites.addToXpath("[17]")).sendKeys("0000");
        visibility(infoclinic.input_requisites.addToXpath("[18]")).sendKeys("000000");
        visibility(infoclinic.input_requisites.addToXpath("[19]")).sendKeys("15.09.2020");
        visibility(infoclinic.input_requisites.addToXpath("[20]")).sendKeys("000");
        visibility(infoclinic.input_requisites.addToXpath("[21]")).sendKeys("MVD");

        visibility(infoclinic.btn_search.addToXpath("[4]")).click();
        visibility(XpathWrapper.contains("Гугл", "Выбор места работы")).click();
        visibility(XpathWrapper.contains("Выбрать", "кнопка выбора")).click();
        visibility(infoclinic.input_address.addToXpath("[1]")).sendKeys("410000");
        visibility(infoclinic.input_address.addToXpath("[4]")).sendKeys("г. Москва, пер. Чернышевского");
        visibility(infoclinic.input_address.addToXpath("[6]")).sendKeys("1");
        visibility(infoclinic.input_address.addToXpath("[7]")).sendKeys("1");
        visibility(infoclinic.input_address.addToXpath("[8]")).sendKeys("1");
        visibility(infoclinic.input_address.addToXpath("[9]")).sendKeys("1");
        visibility(infoclinic.input_address.addToXpath("[10]")).sendKeys("15.09.2020");

        visibility(XpathWrapper.contains("Сохранить", "Кнопка сохранить").addToXpath("[last()]")).click();
//        visibility(XpathWrapper.contains("Добавить как нового", "Кнопка добавить как нового")).click();
    }

    @Test(dependsOnMethods = "addPatient", description = "Проверка пациента")
    public void checkPatient() {
        preloader.waitForPageLoaded();
        invisible(infoclinic.spinner, 10);
        action.doubleClick(visibility(XpathWrapper.contains("Автотест","пациннт в списке")).getElement()).perform();
        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[2]")).getAttribute("value").contains("Наличный"));
        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[4]")).getAttribute("value").contains("Автотест"));
        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[5]")).getAttribute("value").contains("Иванович"));
        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[6]")).getAttribute("value").contains("15.09.2020"));
        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[18]")).getAttribute("value").contains("000000"));
        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[19]")).getAttribute("value").contains("15.09.2020"));
        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[20]")).getAttribute("value").contains("000"));
//        Assert.assertTrue(visibility(infoclinic.input_requisites.addToXpath("[21]")).getAttribute("value").contains("MVD"));
        Assert.assertTrue(visibility(infoclinic.input_address.addToXpath("[1]")).getAttribute("value").contains("410000"));
//        Assert.assertTrue(visibility(infoclinic.input_address.addToXpath("[4]")).getAttribute("value").contains("г. Москва, пер. Чернышевского"));
        Assert.assertTrue(visibility(infoclinic.input_address.addToXpath("[6]")).getAttribute("value").contains("1"));
        Assert.assertTrue(visibility(infoclinic.input_address.addToXpath("[7]")).getAttribute("value").contains("1"));
        Assert.assertTrue(visibility(infoclinic.input_address.addToXpath("[8]")).getAttribute("value").contains("1"));
        Assert.assertTrue(visibility(infoclinic.input_address.addToXpath("[9]")).getAttribute("value").contains("1"));
        Assert.assertTrue(visibility(infoclinic.input_address.addToXpath("[10]")).getAttribute("value").contains("15.09.2020"));
        visibility(XpathWrapper.contains("Закрыть", "кнопка закрыть").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Подтвердить", "кнопка подтвердить").addToXpath("[last()]")).click();
    }

    @Test(dependsOnMethods = "checkPatient", description = "Открыть расписание")
    public void openSchedule() {
        visibility(XpathWrapper.contains("Расписание", "кнопка Расписание")).click();
        visibility(XpathWrapper.contains("На неделю", "Расписание на неделю")).click();
        jsScroll(new ElementWrapper(getDRIVER().findElement(By.xpath(infoclinic.btn_searchFilial.addToXpath("[last()]").getXpath_String())), infoclinic.btn_searchFilial.addToXpath("[last()]")));
        jsClick(visibility(infoclinic.btn_searchFilial.addToXpath("[last()]")));
        preloader.waitForPageLoaded();
        visibility(infoclinic.btn_filial.getReplaceContext("Клиника №1").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Выбрать", "Кнопка выбора")).click();
    }

    @Test(dependsOnMethods = "openSchedule", description = "Записать пациента")
    public void appoint(){
        action.doubleClick(visibility(XpathWrapper.contains("Свободно","кнопка свободного дня")).getElement()).perform();
        preloader.waitForPageLoaded();
        invisible(infoclinic.spinner, 10);
        visibility(XpathWrapper.contains("15 мин", "кнопка продолжительности")).click();
        visibility(infoclinic.btn_search.addToXpath("[5]")).click();
        visibility(XpathWrapper.contains("Первичный", "статус пациента")).click();
        visibility(XpathWrapper.contains("Выбрать", "кнопка выбрать")).click();
        visibility(infoclinic.btn_search.addToXpath("[6]")).click();
        visibility(XpathWrapper.contains("Вакцинация от гриппа", "выбор повода")).click();
        visibility(XpathWrapper.contains("Выбрать", "кнопка выбрать")).click();
        visibility(infoclinic.input_comment).sendKeys("Просто комментарий");
        visibility(XpathWrapper.contains("Планируемые работы", "кнопка вкладки планируемых работ")).click();
        visibility(XpathWrapper.contains("Выбор планируемых работ", "кнопка выбора планируемых работ")).click();
        visibility(infoclinic.input_codesearch).sendKeys("ТЕСТ" + Keys.ENTER);
        visibility(XpathWrapper.contains("ТЕСТ", "кнопка услуги")).click();
        visibility(infoclinic.btn_moveService).click();
        visibility(XpathWrapper.contains("Выбрать", "кнопка выбрать")).click();
        visibility(XpathWrapper.contains("Сохранить", "кнопка сохранения")).click();

    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
