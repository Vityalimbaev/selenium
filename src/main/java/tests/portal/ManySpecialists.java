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

public class ManySpecialists extends DriverWaitWrapper {

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
        currentBrowser.getCapabilities().setCapability("name", "["+System.getProperty("mode") + "] ManySpecialists " + browser);
        setDRIVER(new Connector(currentBrowser, Boolean.parseBoolean(System.getProperty("isRemote"))).getDRIVER());
        get(dataProviderPortal.getUrl());
        preloader = new Preloader(getDRIVER());
        crossbrowsing = new API(String.valueOf(getDRIVER().getSessionId()));

        patientTest = new Patient();
        patientTest.setDRIVER(getDRIVER());
        patientTest.setDataProviderPortal(dataProviderPortal);
        patientTest.setPreloader(preloader);
        patientTest.setCrossbrowsing(crossbrowsing);
        patientTest.setOnline(true);
    }

    @Test(description = "Авторизация")
    public void authorization(){
        patientTest.authorization();
    }

    @Test(dependsOnMethods = "authorization", description = "Выбор клиники")
    public void selectClinic(){
        patientTest.selectClinic();
    }

    @Test(dependsOnMethods = "selectClinic", description = "Выбор специальности")
    public void selectSpecialty(){
        patientTest.selectSpecialty();
    }

    @Test(dependsOnMethods = "selectSpecialty", description = "Выбор доктора")
    public void selectDoctor(){
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        visibility(portal.btn_doctorListItem).click();
        visibility(portal.btn_doctorListItem.addToXpath("[last()]")).click();
        visibility(portal.btn_next.addToXpath("[last()]")).click();
        Assert.assertNotNull(visibility(portal.btn_nextMonth), "Не выбран врач");
    }

    @Test(dependsOnMethods = "selectDoctor", description = "Выбор дня и времени")
    public void selectDayAndTime(){
        if(exist(XpathWrapper.contains("К сожалению, на выбранный период, расписание отсутствует", "Расписание отсутствует"), 3)){
            visibility(portal.btn_nextMonth).click();
        }
        visibility(portal.btn_timeOnManySpecialists).click();
        patientTest.selectDayAndTime();
    }

    @Test(dependsOnMethods = "selectDayAndTime", description = "Проверка записи и выбранных услуг")
    public void checkService(){
        preloader.isLoadingDesktopPortal();
        invisible(portal.preloaderDesctop, 30);
        visibility(XpathWrapper.contains("Мои записи", "кнопка мои записи")).click();
        preloader.waitForPageLoaded();
        patientTest.patientSelectDoctor();
        if(patientTest.getServiceName() != null){
            Assert.assertTrue(visibility(portal.services.addToXpath("[last()]")).getText().contains(patientTest.getServiceName()));
        }
    }

    @Test(dependsOnMethods = "checkService", description = "Отмена записи")
    public void abortAppoint(){
        visibility(portal.btn_cancel).click();
        if(exist(portal.btn_submit, 30)){
            visibility(portal.btn_submit).click();
        }
    }

    @AfterClass(alwaysRun = true, description = "Завершение")
    public void destroy(){
        super.destroy();
    }
}
