package pages;

import models.wrappers.XpathWrapper;

public class VcDemo {
    public XpathWrapper input_login = new XpathWrapper("//input[@id='login__input-container__input-username']","поле логина");
    public XpathWrapper input_password = new XpathWrapper("//input[@id='login__input-container__input-password']", "поле пароля");
    public XpathWrapper input_conferenceID = new XpathWrapper("//*[@class='param-input']", "поле ID конференции");

    public XpathWrapper btn_login = new XpathWrapper("//*[@id='login__button']", "кнопка входа");
    public XpathWrapper btn_download = new XpathWrapper("//*[@class='material-icons']", "кнопка загрузки");
    public XpathWrapper btn_loginHeader = new XpathWrapper("//*[@id='main-header__buttons-login']", "кнопка входа в хедере");
}
