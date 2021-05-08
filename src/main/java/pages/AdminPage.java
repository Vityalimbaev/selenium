package pages;

import models.wrappers.XpathWrapper;

public class AdminPage {
// tp - tariff plan
    public XpathWrapper input_login = new XpathWrapper("//input[contains(@name,'username')]","поле логина");
    public XpathWrapper input_password = new XpathWrapper("//input[@type='password']","поле пароля");
    public XpathWrapper input_search = new XpathWrapper("//*[@placeholder='Быстрый поиск по имени']","поле поиска по имени");
    public XpathWrapper input_virtualAddress = new XpathWrapper("(//*[contains(@id,'address-edit-modal')])//input","поле адреса виртуального сайта");
    public XpathWrapper input_serviceUrl = new XpathWrapper("//input[@name='serviceUrl']","поле адреса сервиса данных");
    public XpathWrapper input_internalName = new XpathWrapper("//input[@name='internalName']","поле названия виртуального сайта");
    public XpathWrapper input_title = new XpathWrapper("//input[@name='title']","поле названия портала");
    public XpathWrapper input_senderName = new XpathWrapper("//input[@name='senderName']","поле для имени отправителя");
    public XpathWrapper input_containerName = new XpathWrapper("//input[@name='containerName']","поле для имени контейнера");
    public XpathWrapper input_countUsers = new XpathWrapper("//input[@name='countUsers']","поле для количества пользователей");
    public XpathWrapper input_sessionTimeout = new XpathWrapper("//input[@name='sessionTimeout']","поле для таймаута сессии пользователя");
    public XpathWrapper input_hostNames = new XpathWrapper("//input[@name='hostNames']","поле для адресов виртуальных хостов");
    public XpathWrapper input_serviceReportUrl = new XpathWrapper("//input[@name='serviceReportUrl']","поле для сервиса отчетов");
    public XpathWrapper input_outputSystemName = new XpathWrapper("//*[@name='name']","поле для наименования внешней системы");
//    public XpathWrapper input_tp_outputSystemName = new XpathWrapper("//*[@name='extId']","поле для наименования внешней системы");
    public XpathWrapper input_tp_outputSystemName = new XpathWrapper("//*[contains(text(),'Внешняя система')]/../..//input","поле для наименования внешней системы");
    public XpathWrapper input_tp_outputHostId = new XpathWrapper("//*[@name='hostId']","поле для наименования виртуального сайта");
    public XpathWrapper input_tp_info = new XpathWrapper("//*[contains(text(),'Информация о тарифе')]/../..//input","чекбокс разрешений внешней системы");


    public XpathWrapper select_addPatientMode = new XpathWrapper("//input[@name='addPatientMode']","поле добавления пациентов к своей учетной записи");
    public XpathWrapper select_confirmPersonalData = new XpathWrapper("//input[@name='confirmPersonalData']","поле согласия на обработку персональных данных");

    public XpathWrapper checkbox_info = new XpathWrapper("(//fieldset)[2]//input[@type='button']","чекбокс в информации о вирт сайте");
    public XpathWrapper checkbox_tester = new XpathWrapper("(//*[contains(text(),'tester')]/../..)//div[@class='x-grid-row-checker']","чекбокс с именем tester");
    public XpathWrapper checkbox_tarifOptions = new XpathWrapper("(//div[contains(text(),'Включенные тарифные опции')]/../../..)//input","чекбокс тарифные опции");
    public XpathWrapper checkbox_moduleController = new XpathWrapper("(//div[contains(text(),'Управление модулями')]/../../..)//input","чекбокс тарифные опции");
    public XpathWrapper checkbox_mapNanalyst = new XpathWrapper("(//div[contains(text(),'Справка')]/../../..)//input","чекбокс аналитики и карт");
    public XpathWrapper checkbox_enableWebInfoclinic = new XpathWrapper("//*[@id='isCloudMode-inputEl']","чекбокс веб инфоклиники");
    public XpathWrapper checkbox_webInfoclinicAllows = new XpathWrapper("//*[contains(text(),'Разрешения')]/../..//input","чекбокс разрешений внешней системы");
    public XpathWrapper checkbox_webInfoclinicWebsites = new XpathWrapper("//*[contains(@class,'x-grid-row-checker')]","чекбокс сайтов для внешней системы");

    public XpathWrapper btn_login = new XpathWrapper("//a[contains(@id,'button')]","кнопка войти");
    public XpathWrapper btn_searchClinic = new XpathWrapper("//div[contains(@id,'trigger-edit')]","кнопка выбора клиники");
    public XpathWrapper btn_submitDisabled = new XpathWrapper("//*[contains(@class,'x-item-disabled')]","кнопка выбора не доступна");
    public XpathWrapper btn_submit = new XpathWrapper("//*[contains(text(),'Выбрать')]","кнопка выбора");
    public XpathWrapper btn_cancel = new XpathWrapper("//*[contains(text(),'Отмена')]","кнопка отмены");
    public XpathWrapper btn_tarificationJournal = new XpathWrapper("//td//*[contains(text(),'Журнал тарификации')]","кнопка журнала тарификации");
//    public XpathWrapper btn_time = new XpathWrapper("//div[contains(@class,'x-settings')]//*[contains(@class,'x-grid-tree-node-leaf')]","кнопка вреимени тарификации");
    public XpathWrapper btn_time = new XpathWrapper("//*[contains(@class,'x-autocontainer')]//*[contains(@class,'x-grid-tree-node-leaf')]","кнопка вреимени тарификации");
    public XpathWrapper btn_pastmonth = new XpathWrapper("(//*[@data-qtip='Месяц назад'])[last()]","кнопка предыдущего месяца");
    public XpathWrapper btn_site = new XpathWrapper("//*[text()='?']","кнопка с названием сайта");
    public XpathWrapper btn_appointsJournal = new XpathWrapper("/html/body/div[3]/div[1]/div/div[2]/div[1]/div[2]/table[5]/tbody/tr/td/div/span","журнал с записями");
    public XpathWrapper btn_update = new XpathWrapper("//*[contains(text(),'Обновить')]","обновить");
    public XpathWrapper btn_appoint = new XpathWrapper("//*[contains(text(),'?')]/parent::td/parent::tr","запись с датой");
    public XpathWrapper btn_pencil = new XpathWrapper("//div[contains(@class,'fa-pencil')]","кнопка редактирования");
    public XpathWrapper btn_input_virtualAddress = new XpathWrapper("(//*[contains(@id,'address-edit-modal')])//div[contains(@class,'x-grid-cell-inner')]","поле адреса виртуального сайта");
    public XpathWrapper btn_yes = new XpathWrapper("//*[text()='Да']","кнопка да");
    public XpathWrapper btn_newSecretKey = new XpathWrapper("//*[contains(@class,'fa-refresh')]","кнопка генерации нового секретного ключа");

    public XpathWrapper spinner = new XpathWrapper("//*[contains(@class,'spinner-wrapper')]", "спиннер");
    public XpathWrapper preloader = new XpathWrapper("//*[contains(text(),'Загрузка...')]","прелоадер");
    public XpathWrapper loadmask = new XpathWrapper("//*[contains(@style,'pointer-events: none')]","прелоадер");

}
