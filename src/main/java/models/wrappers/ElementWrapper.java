package models.wrappers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;



public class ElementWrapper {
    private final static Logger logger = Logger.getLogger(ElementWrapper.class.getName());
    private WebElement element;
    private XpathWrapper xpathWrapper;

    public ElementWrapper(WebElement element, XpathWrapper xpathWrapper) {
        this.element = element;
        this.xpathWrapper = xpathWrapper;
        logger.debug("Найден веб-элемент: " + xpathWrapper.getDescription() + " | Xpath: " + xpathWrapper.getXpath_String());
    }

    public XpathWrapper getXpathWrapper() {
        return xpathWrapper;
    }

    private String trace(){
        Throwable throwable = new Throwable();
        StringBuilder trace = new StringBuilder();

        StackTraceElement trace_element = new StackTraceElement("undefined", "undefined", "undefined", 0);
        for (StackTraceElement loop : throwable.getStackTrace()) {
            if (loop.getClassName().contains("tests.")) {
                trace_element = loop;
                break;
            }
        }
        trace.append("#TEST: '");
        trace.append(trace_element.getFileName().replace(".java", ""));
        trace.append("' #CASE: '");
        trace.append(trace_element.getMethodName());
        trace.append("' #LINE: '");
        trace.append(trace_element.getLineNumber());
        trace.append("' - ");

        return trace.toString();
    }

    private void logger(String message){
        logger.debug(trace() + message);
    }

    public WebElement getElement() {
        logger("Веб-элемент, " + xpathWrapper.getDescription() + ", был предоставлен.");
        return element;
    }

    public void click() {
        element.click();
        logger("Веб-элемент, " + xpathWrapper.getDescription() + ", был кликнут.");
    }

    public void sendKeys(CharSequence... charSequences) {
        element.sendKeys(charSequences);
        StringBuilder s = new StringBuilder();
        for (CharSequence j : charSequences){
            s.append(j.toString());
        }
        logger("Ввод данных: '" + s + "', веб-элемент, " + xpathWrapper.getDescription());
    }

    public String getText() {
        logger("Текст веб-элемента, " + xpathWrapper.getDescription() + ", был передоставлен.");
        return element.getText();
    }

    public String getAttribute(String s) {
        logger("Атрибут '" + s + "' веб-элемента, " + xpathWrapper.getDescription() + ", был передоставлен.");
        return element.getAttribute(s);
    }

}
