package models;

import models.wrappers.DriverWaitWrapper;
import models.wrappers.XpathWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

public class Preloader{
    private RemoteWebDriver driver;

    public Preloader(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void isLoadingDesktopPortal() {
        String script = "try{return jQuery.active == 0}catch(e){return false;}";
        waitJS("Истек тайм-аут 90 секунд, ожидания завершения всех запросов.",script, 90);
    }


    protected void waitJS(String text_fail_msg, String js_script, Integer time) {
        final String js_code = js_script;
        new WebDriverWait(this.driver, time)
                .withMessage(text_fail_msg)
                .until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        return (Boolean) js.executeScript(js_code);
                    }
                });
    }

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            //System.out.println("Timeout waiting for Page Load Request to complete.");
        }
    }
}
