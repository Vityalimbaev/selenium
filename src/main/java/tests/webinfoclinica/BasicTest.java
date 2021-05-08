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
import java.util.List;

public class BasicTest extends DriverWaitWrapper {
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

    @Test(dependsOnMethods = "authorization", description = "Поиск по имени")
    public void searchByName(){
        preloader.waitForPageLoaded();
        invisible(infoclinic.spinner, 10);
        visibility(XpathWrapper.contains("Картотека", "кнопка Картотеки")).click();
        visibility(infoclinic.input_liveSearch).sendKeys("Проверочный Иван Иванович" + Keys.ENTER);
        Assert.assertTrue(exist(XpathWrapper.contains("990000002", "номер карты Ивана Ивановича"), 10));
    }

    @Test(alwaysRun = true, dependsOnMethods = "searchByName", description = "Упрощенный поиск")
    public void easySearch(){
        visibility(XpathWrapper.contains("Упрощенный поиск", "кнопка Упрощенный поиск")).click();
        visibility(infoclinic.input_firstName).sendKeys("Иван");
        visibility(infoclinic.input_lastName).sendKeys("Проверочный");
        visibility(infoclinic.input_midName).sendKeys("Иванович");
        visibility(XpathWrapper.contains("Поиск","кнопка поиска").addToXpath("[last()]")).click();
        Assert.assertTrue(exist(XpathWrapper.contains("990000002", "номер карты Ивана Ивановича"), 10));
    }

    @Test(dependsOnMethods = "easySearch", description = "Печать")
    public void printCard(){
        preloader.waitForPageLoaded();
        visibility(XpathWrapper.contains("990000002", "номер карты Ивана Ивановича")).click();
        preloader.waitForPageLoaded();
        visibility(XpathWrapper.contains("Печать", "кнопка печати")).click();
        preloader.waitForPageLoaded();
        List<ElementWrapper> list = getElements(infoclinic.elementToPrint);
        visibility(XpathWrapper.contains("Печать", "кнопка печати")).click();
        for (int i = 0; i < list.size(); i++) {
            String current_tab = getDRIVER().getWindowHandle();
            preloader.waitForPageLoaded();
            visibility(XpathWrapper.contains("Печать", "кнопка печати")).click();
            visibility(list.get(i).getXpathWrapper());
            (list.get(i).getElement()).click();

            List<String> tab_list = new ArrayList<>(getDRIVER().getWindowHandles());
            tab_list.remove(current_tab);
            tab_list.forEach(tab -> getDRIVER().switchTo().window(tab).close());
            getDRIVER().switchTo().window(current_tab);
        }
    }

    @Test(alwaysRun = true, dependsOnMethods = "printCard", description = "Печать из карточки")
    public void printPatientCard(){
        XpathWrapper print_btn = XpathWrapper.contains("Печать", "кнопка печати").addToXpath("[last()]");
        preloader.waitForPageLoaded();
        action.doubleClick(visibility(XpathWrapper.contains("990000002", "номер карты Ивана Ивановича")).getElement()).perform();
        preloader.waitForPageLoaded();
        visibility(print_btn).click();
        preloader.waitForPageLoaded();
        List<ElementWrapper> list = getElements(infoclinic.elementToPrint);
        visibility(print_btn).click();
        for (int i = 5; i < list.size(); i++) {//В списке выше было 5 докуметов на печать
            String current_tab = getDRIVER().getWindowHandle();
            preloader.waitForPageLoaded();
            visibility(print_btn).click();
            visibility(XpathWrapper.contains("Отчет из карточки", "отчет из карточки"));
            (list.get(i).getElement()).click();

            List<String> tab_list = new ArrayList<>(getDRIVER().getWindowHandles());
            tab_list.remove(current_tab);
            tab_list.forEach(tab -> getDRIVER().switchTo().window(tab).close());
            getDRIVER().switchTo().window(current_tab);
        }
        visibility(XpathWrapper.contains("Закрыть", "кнопка закрыть").addToXpath("[last()]")).click();
        visibility(XpathWrapper.contains("Подтвердить", "кнопка подтверждения")).click();
    }

    @Test(alwaysRun = true, dependsOnMethods = "printPatientCard", description = "Ежедневное расписание")
    public void openDailySchedule() {
        visibility(XpathWrapper.contains("Расписание", "кнопка Расписание")).click();
    }

    @Test(dependsOnMethods = "openDailySchedule", description = "Еженедельное расписание")
    public void openWeeklySchedule() {
        visibility(XpathWrapper.contains("На неделю", "Расписание на неделю")).click();
    }

    @Test(dependsOnMethods = "openWeeklySchedule", description = "Проверка филиалов")
    public void filialsCheck() {
        for (int i = 3; i > 0; i--) {
            jsScroll(new ElementWrapper(getDRIVER().findElement(By.xpath(infoclinic.btn_searchFilial.addToXpath("[last()]").getXpath_String())), infoclinic.btn_searchFilial.addToXpath("[last()]")));
            jsClick(visibility(infoclinic.btn_searchFilial.addToXpath("[last()]")));
            preloader.waitForPageLoaded();
            visibility(infoclinic.btn_filial.getReplaceContext("Клиника №" + i).addToXpath("[last()]")).click();
            visibility(XpathWrapper.contains("Выбрать", "Кнопка выбора")).click();
        }
    }

    @Test(dependsOnMethods = "filialsCheck", description = "История болезней")
    public void medicalHistory() {
        visibility(XpathWrapper.contains("Проверочный И. И.", "Проверочный И. И.'").addToXpath("[15]")).click();
        visibility(XpathWrapper.contains("Перейти в картотеку", "кнопка перехода в картотеку")).click();
        visibility(XpathWrapper.contains("990000002", "номер карты Ивана Ивановича")).click();
        visibility(XpathWrapper.contains("История болезни", "История болезни")).click();
        visibility(XpathWrapper.contains("Клиника №1", "выбор протокола").addToXpath("[last()]")).click();

        String current_tab = getDRIVER().getWindowHandle();
        visibility(XpathWrapper.contains("Просмотр протокола", "Просмотр протокола")).click();
        List<String> tab_list = new ArrayList<>(getDRIVER().getWindowHandles());
        tab_list.remove(current_tab);
        tab_list.forEach(tab -> getDRIVER().switchTo().window(tab).close());
        getDRIVER().switchTo().window(current_tab);
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
