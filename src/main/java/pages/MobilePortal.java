package pages;

import models.wrappers.XpathWrapper;

public class MobilePortal {
    public XpathWrapper checkbox = new XpathWrapper("//*[contains(@type,'checkbox')]", "чекбокс");

    public XpathWrapper btn_hamburger = new XpathWrapper("//*[contains(@class,'hamburger')]", "кнопка меню-гамбургер");
    public XpathWrapper btn_pricelist_record = new XpathWrapper("//*[contains(@class,'wr-pricelist__records-item')]", "кнопка выбора отделения");
    public XpathWrapper btn_buy = new XpathWrapper("//*[contains(@class,'wr-button--default')]", "кнопка покупки");

    public XpathWrapper preloader = new XpathWrapper("//*[contains(@class,'wr-spinner__spin')]", "прелоадер");
    public XpathWrapper date = new XpathWrapper("//li[@class='wr-list__child']", "дата записи");
    public XpathWrapper time = new XpathWrapper("(//li[@class='wr-list__child'])/div[contains(@class,'wr-list__item')]", "дата записи");
    public XpathWrapper appointInfo = new XpathWrapper("//dd", "информация о записи");
}
