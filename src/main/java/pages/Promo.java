package pages;

import models.wrappers.XpathWrapper;

public class Promo extends Portal{
    public XpathWrapper iframe_cabinet = new XpathWrapper("//iframe[@class='wr-embedded-widget']","iframe личного кабинета");
    public XpathWrapper btn_appoints = new XpathWrapper("//a[@href='/records']","Мои записи");
    public XpathWrapper btn_cabinet = new XpathWrapper("//*[contains(text(),'ЛИЧНЫЙ КАБИНЕТ')]","кнопка входа в личный кабинет");
}
