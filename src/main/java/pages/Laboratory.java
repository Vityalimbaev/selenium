package pages;

import models.wrappers.XpathWrapper;

public class Laboratory {
    /**
     * GROUPS OF INPUT`S (ПОЛЯ)
     */
    public static XpathWrapper input_login = new XpathWrapper("//input[@name='username']","поле логина");
    public static XpathWrapper input_password = new XpathWrapper("//input[@name='password']", "поле пароля");
    public static XpathWrapper input_searchByName = new XpathWrapper("//*[@name='SEARCH_STRING']", "строка поиска по имени");
    public static XpathWrapper input_order_searchByName = new XpathWrapper("//*[@name='trigger-client-field-inputEl']", "строка поиска по имени");
    public static XpathWrapper input_order_searchByYOB = new XpathWrapper("//*[text()='Год рождения:']/../..//input", "строка поиска по году рождения");
    public static XpathWrapper input_order_searchByPcode = new XpathWrapper("//*[contains(@name,'client-search-widgets-uip')]", "строка поиска по PCode");
    public static XpathWrapper input_searchServiceByName = new XpathWrapper("//*[@name='search']", "строка поиска по названию услуги");

    /**
     * GROUPS OF CHECKBOX`ES (ФЛАЖКИ)
     */

    /**
     * GROUPS OF BUTTON`S (КНОПКИ)
     */
    public static XpathWrapper btn_login = new XpathWrapper("//a[contains(@id,'button')]","кнопка войти");
    public static XpathWrapper btn_users = new XpathWrapper("//*[contains(text(),'Пользователи')]","кнопка пользователи");
    public static XpathWrapper btn_journal = new XpathWrapper("//*[contains(text(),'Журнал заявок')]","кнопка Журнала заявок");
    public static XpathWrapper btn_add = new XpathWrapper("//*[contains(text(),'Добавить')]","кнопка добавить");
    public static XpathWrapper btn_select = new XpathWrapper("//span[text()='Выбрать']","кнопка выбора");
    public static XpathWrapper btn_choosePatient = new XpathWrapper("//div[contains(@id,'patient-combo-') and contains(@id,'-trigger-open')]","кнопка выбора пациента");
    public static XpathWrapper btn_chooseOrg = new XpathWrapper("//div[contains(@id,'dictionary')]//div[contains(@id,'WIC-combobox-') and contains(@id,'-trigger-open') and contains(@class,'x-form-trigger')]","кнопка выбора организации");
    public static XpathWrapper btn_selectOrg = new XpathWrapper("//div[contains(@id,'ext-comp')]//div[contains(@class,'x-grid-cell-inner-treecolumn')]","кнопка первой организации");
    public static XpathWrapper btn_addService = new XpathWrapper("//*[contains(text(),'Добавить услугу')]","кнопка добавления услуги");
    public static XpathWrapper btn_moveService = new XpathWrapper("//*[contains(@id,'services-controls')]//*[contains(@class,'x-btn')]","кнопка добавления услуги в список справа");
    public static XpathWrapper btn_save = new XpathWrapper("//span[text()='Сохранить']","кнопка сохранения");
    public static XpathWrapper btn_closeTab = new XpathWrapper("//*[contains(text(),' Закрыть эту вкладку')]","кнопка закрытия вкладки");
    public static XpathWrapper btn_delete = new XpathWrapper("//*[contains(text(),'Удалить')]","кнопка удаления");
    public static XpathWrapper btn_confirmDelete = new XpathWrapper("//*[contains(@class,'x-message-box')]//*[text()='OK']","кнопка подтверждения удаления");

    /**
     * GROUPS OF OTHER ELEMENTS (ДРУГИЕ ВЕБ-ЭЛЕМЕНТЫ)
     */
    public static XpathWrapper preloader = new XpathWrapper("//*[contains(@class,'spinner')]","прелоадер");
    public static XpathWrapper userExist = new XpathWrapper("//*[contains(text(),'?') and contains(@class,'x-grid-cell-inner')]", "пользватель в таблице");
    public static XpathWrapper info_noPermissions = new XpathWrapper("//*[contains(text(),'Нет прав доступа на удаление лечений/нарядов!')]","инфо 'Нет прав'");
    public static XpathWrapper spinner = new XpathWrapper("//*[contains(@class,'spinner-wrapper')]", "спиннер");
    public static XpathWrapper preloader2 = new XpathWrapper("//*[contains(text(),'Загрузка...')]","прелоадер");
    public static XpathWrapper loadmask = new XpathWrapper("//*[contains(@id,'loadmask')]","прелоадер");

}
