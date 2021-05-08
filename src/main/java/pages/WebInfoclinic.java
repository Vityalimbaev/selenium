package pages;

import models.wrappers.XpathWrapper;

public class WebInfoclinic {
    public XpathWrapper input_login = new XpathWrapper("//input[contains(@name,'username')]","поле логина");
    public XpathWrapper input_password = new XpathWrapper("//input[@type='password']","поле пароля");
    public XpathWrapper input_liveSearch = new XpathWrapper("//*[@name='SEARCH_LIVE']","поле поиска по фио");
    public XpathWrapper input_firstName = new XpathWrapper("//*[@name='SEARCH_FIRSTNAME']","поле поиска по имени");
    public XpathWrapper input_lastName = new XpathWrapper("//*[@name='SEARCH_LASTNAME']","поле поиска фамилии");
    public XpathWrapper input_midName = new XpathWrapper("//*[@name='SEARCH_MIDNAME']","поле поиска по отчеству");
    public XpathWrapper input_requisites = new XpathWrapper("//*[contains(text(),'Общие реквизиты')]//ancestor::fieldset//input","поле общих реквизитов");
    public XpathWrapper input_address = new XpathWrapper("//*[contains(text(),'Адрес регистрации')]//ancestor::fieldset//input","поле общих реквизитов");
    public XpathWrapper input_comment = new XpathWrapper("//*[contains(@name,'COMMENT')]","поле для комментария к назначению");
    public XpathWrapper input_codesearch = new XpathWrapper("//*[contains(@name,'codesearch')]","поле для поиска по коду");

    public XpathWrapper elementToPrint = new XpathWrapper("//div[contains(@class,'x-menu-item')]", "элемент для печати");
    public XpathWrapper btn_searchFilial = new XpathWrapper("//div[@id='filialId-trigger-open']", "кнопка поиска филиалов");
    public XpathWrapper btn_search = new XpathWrapper("//*[contains(@class,'x-form-search-trigger')]", "кнопка с лупой");
    public XpathWrapper btn_filial = new XpathWrapper("//span[contains(text(),'?')]", "кнопка с назвнием филиала");
    public XpathWrapper btn_moveService = new XpathWrapper("//*[contains(@id,'services-controls')]//*[contains(@class,'x-btn')]","кнопка добавления услуги в список справа");

    public XpathWrapper spinner = new XpathWrapper("//*[contains(@class,'spinner-wrapper')]", "спиннер");
    public XpathWrapper preloader = new XpathWrapper("//*[contains(text(),'Загрузка...')]","прелоадер");
    public XpathWrapper loadmask = new XpathWrapper("//*[contains(@style,'pointer-events: none')]","прелоадер");

}
