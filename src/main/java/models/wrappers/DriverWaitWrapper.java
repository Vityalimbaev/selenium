package models.wrappers;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;

import java.util.ArrayList;
import java.util.List;


public abstract class DriverWaitWrapper {
    private RemoteWebDriver DRIVER = null;
    private final static String WARN = "Веб-драйвер не указан. Используйте метод setDriver(), что бы указать веб-драйвер.";
    private final static Logger logger = Logger.getLogger(DriverWaitWrapper.class.getName());

    public void setDRIVER(RemoteWebDriver DRIVER){
        this.DRIVER = DRIVER;
    }

    public void get(String url){
        check_driver();
        DRIVER.get(url);
    }

    public void open(String url){
        check_driver();
        DRIVER.get(url);
    }

    public void destroy(){
        check_driver();
        DRIVER.quit();
    }

    public RemoteWebDriver getDRIVER() {
        return DRIVER;
    }

    private void check_driver(){
        if(DRIVER == null) {
            throw new TestException(WARN);
        }
    }

    public ElementWrapper visibility(XpathWrapper element_info, long time){
        check_driver();
        return new ElementWrapper(
                new WebDriverWait(DRIVER, time)
                        .withMessage("Не удалось найти веб-элемент: " + element_info.getDescription())
                        .until(ExpectedConditions.visibilityOfElementLocated(element_info.getXpath())),
                element_info
        );
    }

    public ElementWrapper visibility(XpathWrapper element_info){
        long time = 60;
        return visibility(element_info, time);
    }

    protected Boolean invisible(XpathWrapper xpathWrapper, long time) {
        WebDriverWait wait = new WebDriverWait(getDRIVER(), time);
        return wait
                .withMessage("Не удалось дождаться исчезновения: " + xpathWrapper.getDescription())
                .until(ExpectedConditions.invisibilityOfElementLocated(xpathWrapper.getXpath()));

    }

    public boolean exist(XpathWrapper element_info, long time){
        try{
            logger.debug("Попытка найти элемент " + element_info.getDescription());
            visibility(element_info, time);
            return true;
        }
        catch (TimeoutException e){
            logger.debug("Элемент " + element_info.getDescription() + " не найден");
            return false;
        }
    }

    public List<ElementWrapper> getElements(XpathWrapper xpath){
        List<WebElement> elements = getDRIVER().findElements(xpath.getXpath());
        logger.debug("Найдено " + elements.size() + " элементов " + xpath.getDescription());
        List<ElementWrapper> result = new ArrayList<>();
        elements.forEach(webElement -> result.add(new ElementWrapper(webElement, xpath)));
        return result;
    }

    public void jsScroll(ElementWrapper element){
        ((JavascriptExecutor) getDRIVER()).executeScript("arguments[0].scrollIntoView()", element.getElement());
        logger.debug("Скролл с помощью JS к " + element.getXpathWrapper().getDescription());
    }

    public void jsClick(ElementWrapper element){
        ((JavascriptExecutor) getDRIVER()).executeScript("arguments[0].click()", element.getElement());
        logger.debug("Клик с помощью JS по " + element.getXpathWrapper().getDescription());
    }

    public void switchToFrame(ElementWrapper element){
        getDRIVER().switchTo().frame(element.getElement());
        logger.debug("Переключено на iframe " + element.getXpathWrapper().getDescription());
    }

    public void switchToParentFrame(){
        getDRIVER().switchTo().parentFrame();
        logger.debug("Переключено на родительский iframe");
    }

    public void switchToDefaultContent(){
        getDRIVER().switchTo().defaultContent();
        logger.debug("Переключено на Default Content");
    }

    public void switchToWindow(String window){
        getDRIVER().switchTo().window(window);
        logger.debug("Переключено на вкладку " + window.toUpperCase());
    }

}
