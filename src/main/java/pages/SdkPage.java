package pages;

import models.wrappers.XpathWrapper;

public class SdkPage {
    public XpathWrapper btn = new XpathWrapper("//button","Кнопка выполнить");
    public XpathWrapper input = new XpathWrapper("(//*[contains(text(),'?')]/ancestor::div[contains(@class,'row')])//input","Поле для данных");
    public XpathWrapper back = new XpathWrapper("(//div[@class='back'])[last()]","Закрыть модальное окно");
    public XpathWrapper back_fail = new XpathWrapper("//div[@class='back']","Закрыть модальное окно");
    public XpathWrapper back_modal = new XpathWrapper("//div[@class='wr-sdk-widget-modal__container']","Закрыть модальное окно");
    public XpathWrapper preloader = new XpathWrapper("//div[@id='cube-loader']","прелоадер");

}
