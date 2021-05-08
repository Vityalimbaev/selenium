package models.wrappers;

import org.openqa.selenium.By;

public class XpathWrapper {
    private By xpath;
    private String xpath_String;
    private String description;

    public XpathWrapper(String xpath, String description) {
        setXpath(xpath);
        setDescription(description);
    }

    public By getXpath() {
        return xpath;
    }

    public String getDescription() {
        return description;
    }

    public String getXpath_String(){ return xpath_String; }

    /**
     *
     * @param context - в xpath указать вместо динамических данных, знак вопроса, т.е. неизвестный контекст.
     *                Пример: xpath = //div[@text()='?'] тогда xpath.getReplaceContext("ТЕКСТ") будет //div[@text()='ТЕКСТ']
     *                Другой пример: xpath = //div[contains(@text(),'?') and text()='?'] тогда xpath.getReplaceContext("СтРоКа1", "сТрОкА2")
     *                будет //div[contains(@text(),'СтРоКа1') and text()='сТрОкА2']
     * @return объект класса XpathWrapper.
     */
    public XpathWrapper getReplaceContext(String... context){
        StringBuilder builder = new StringBuilder(this.xpath_String);
        for(String s : context){
            if(!s.equals("") && !s.equals("?")){
                int index = builder.indexOf("?");
                if(index != -1) builder.replace(index, index+1, s);
            }
        }
        return new XpathWrapper(builder.toString(), this.description);
    }

    public static XpathWrapper contains(String text_element,String description){
        return new XpathWrapper("//*[contains(text(),'"+ text_element +"')]", description);
    }

    public XpathWrapper addToXpath(String postfix){
//        setXpath("(" + this.xpath_String + ")" + postfix);
        return new XpathWrapper(("(" + this.xpath_String + ")" + postfix), this.description + postfix);
    }

    public void setXpath(String xpath) {
        this.xpath = By.xpath(xpath);
        this.xpath_String = xpath;
    }

    public void setDescription(String description) {
        this.description = description.toUpperCase();
    }
}
