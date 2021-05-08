package tests.portal;

import connection.Connector;
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

public class Anonym extends DriverWaitWrapper {

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
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] Anonym " + browser);
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
        patientTest.setConfirmModal(false);
    }

    @Test(description = "Открыть расписание")
    public void openSchedule(){
        visibility(XpathWrapper.contains("Расписание","кнопка расписания")).click();
    }

    @Test(dependsOnMethods = "openSchedule", description = "Выбор клиники")
    public void selectClinic(){
        patientTest.selectClinic();
    }

    @Test(dependsOnMethods = "selectClinic", description = "Выбор специализации")
    public void selectSpecialty(){
        patientTest.selectSpecialty();
    }
    //
    @Test(dependsOnMethods = "selectSpecialty", description = "Выбор врача")
    public void selectDoctor() {
        patientTest.selectDoctor();
    }
    //
    @Test(dependsOnMethods = "selectDoctor", description = "Выбор даты и времени")
    public void selectDayAndTime(){
        patientTest.selectDayAndTime();
    }

    @Test(dependsOnMethods = "selectDayAndTime", description = "Подтверждение")
    public void confirm(){
        visibility(portal.input_fio).sendKeys("Автотестов Тест Тестович");
        visibility(portal.input_phone).sendKeys("+7 951 880 61 76");
        visibility(portal.input_email).sendKeys("tester@test.test");
        visibility(XpathWrapper.contains("Согласен(на)", "чекбокс с согласием")).click();
        visibility(portal.btn_submit).click();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        visibility(portal.input_anonSms).sendKeys("1234567");
        visibility(portal.btn_submit.addToXpath("[last()]")).click();
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        Assert.assertFalse(exist(XpathWrapper.contains("Ошибка" , "ошибка"), 10));
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
