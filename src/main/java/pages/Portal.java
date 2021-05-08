package pages;

import models.wrappers.XpathWrapper;

public class Portal {
    // ПРИМЕР использования хпаф с подменой контекста test.getReplaceContext("", "?", "AХТУНГ", "BАУ", "СЛОВО")
    public XpathWrapper test = new XpathWrapper("//('?' and '?' or '?')", "тестовый xpath");
    /**
     * GROUPS OF INPUT`S (ПОЛЯ)
     */
    public XpathWrapper input_login = new XpathWrapper("//input[@name='username']","поле логина");
    public XpathWrapper input_password = new XpathWrapper("//input[@name='password']", "поле пароля");
    public XpathWrapper input_cardnumber = new XpathWrapper("//*[@name = 'card-number']", "поле номера карты");
    public XpathWrapper input_cardexpire = new XpathWrapper("//*[contains(@name,'expiry-date')]", "поле срока карты");
    public XpathWrapper input_cardcvc = new XpathWrapper("//*[contains(@name,'security-code')]","поле для CVC");
    public XpathWrapper input_sms = new XpathWrapper("//input[@name='answer']","поле для кода из СМС");
    public XpathWrapper input_fio = new XpathWrapper("//input[@id='fio']","поле для ФИО");
    public XpathWrapper input_phone = new XpathWrapper("//input[@id='phone']","поле для номера телефона");
    public XpathWrapper input_email = new XpathWrapper("//input[@id='email']","поле для почты");
    public XpathWrapper input_anonSms = new XpathWrapper("//input[@id='anon-sms-confirmation']","поле для анонимной СМС");
    public XpathWrapper input_forgotPassword = new XpathWrapper("//input[@id='forgot-password-username']","поле для сброса пароля");

    /**
     * GROUPS OF CHECKBOX`ES (ФЛАЖКИ)
     */
    public XpathWrapper checkbox_agree = new XpathWrapper("//input[@name='accept']", "чекбокс согласия");

    /**
     * GROUPS OF BUTTON`S (КНОПКИ)
     */
    public XpathWrapper btn_login = new XpathWrapper("//form[@data-form='pcode']//button[@type='submit']","кнопка войти");
    public XpathWrapper btn_logout = new XpathWrapper("//a[@href='/logout']","кнопка выхода из ЛК");
    public XpathWrapper btn_lpuLogin = new XpathWrapper("(//button[@type='submit'])[3]","кнопка войти");
    //public XpathWrapper clinic_btn = new XpathWrapper("//*[contains(text(),'?')]", "кнопка выбора клиники").getReplaceContext("Запись на онлайн прием  ");
    //public XpathWrapper specialty_btn = new XpathWrapper("//*[text()='?']", "кнопка выбора специальности").getReplaceContext("Терапевт");
    public XpathWrapper btn_doctor = new XpathWrapper("//*[contains(text(),'?')]", "выбор врача");//.getReplaceContext("Медицинский Иван Иванович");
    public XpathWrapper btn_doctorListItem = new XpathWrapper("//*[contains(@class,'wr-doctor-item')]//*[contains(@class,'wr-list-item__checkbox wr-list-item__checkbox pull-left')]", "элемент из таблицы врачей");//.getReplaceContext("Медицинский Иван Иванович");
    public XpathWrapper btn_day = new XpathWrapper("//*[@class='wr-month-calendar-table__day-cell wr-month-calendar-table__day-cell--available']", "кнопка выбора дня записи");
    public XpathWrapper btn_nextMonth = new XpathWrapper("//*[@data-direction=1]", "кнопка перехода на следующий месяц");
    public XpathWrapper btn_time = new XpathWrapper("//*[@class='time label label-success available']", "кнопка выбора времени");
    public XpathWrapper btn_timeOnManySpecialists = new XpathWrapper("//*[contains(@class, 'wr-calendar-table__time--available')]", "кнопка выбора времени у нескольких специалистов");
    public XpathWrapper btn_cancel = new XpathWrapper("//*[contains(text(),'Отменить')]", "кнопка отмены");
    public XpathWrapper btn_submit = new XpathWrapper("//button[contains(@class,'success')]","кнопка подтверждения");
    public XpathWrapper btn_closeOneDay = new XpathWrapper("//*[@data-bb-handler='cancel']", "кнопка закртия модального окна");
    public XpathWrapper btn_payment = new XpathWrapper("//*[contains(text(),'Оплата')]","кнопка оплаты");
    public XpathWrapper btn_submitContract = new XpathWrapper("//*[@data-bb-handler = 'confirmButton']","кнопка соглашения с договором");
    public XpathWrapper btn_submitPayment = new XpathWrapper("//button[contains(@class,'qa-confirm-payment-button')]","кнопка подтверждения оплаты");
    public XpathWrapper btn_next = new XpathWrapper("//*[contains(@class,'fa-chevron-right')]", "кнопка далее");
    public XpathWrapper btn_lpuTime = new XpathWrapper("//*[contains(text(),'?')]","кнопка связи с пациентом у врача");
    public XpathWrapper btn_onlineAppoint = new XpathWrapper("//button[@data-bb-handler='online']", "кнопка онлайн связи");
    public XpathWrapper btn_connect = new XpathWrapper("//button[contains(@class,'mdl-button mdl-js-button mdl-js-ripple-effect main-button one-line equipment__button-join')]", "нопка подключенния по онлайн связи");
    public XpathWrapper btn_participants = new XpathWrapper("//button[@id='conference-header__button-showParticipants']", "кнопка отображения участников");
    public XpathWrapper btn_hangUp = new XpathWrapper("//button[@id='conference-footer__button-hangUp']", "кнопка сброса вызова");
    public XpathWrapper btn_nextPage = new XpathWrapper("//a[contains(@class,'next')]","кнопка перехода на следующую страницу");
    public XpathWrapper btn_3dSecureConfirm = new XpathWrapper("//button[@id='submitcvc']","кнопка перехода на следующую страницу");

    /**
     * GROUPS OF IFRAMES
     */
    public XpathWrapper frame_payment = new XpathWrapper("//iframe[contains(@class,'qa-iframe-widget')]","iframe оплаты");
    public XpathWrapper frame_trueConf = new XpathWrapper("//iframe[contains(@class,'wr-trueconf__iframe')]", "iframe видеосвязи");


    /**
     * GROUPS OF OTHER ELEMENTS (ДРУГИЕ ВЕБ-ЭЛЕМЕНТЫ)
     */
    public XpathWrapper onlyOneDay_info = new XpathWrapper("//*[contains(text(),'Запись на один день')]", "инфобокс 'Запись на один день'");
    public XpathWrapper appointDenied = new XpathWrapper("//*[text()='Запись запрещена']", "запись запрещена");
    public XpathWrapper freeday = new XpathWrapper("//*[contains(@class,'wr-month-calendar-table__day-cell--available')]", "кнопка свободного дня");
    public XpathWrapper submit_modal = new XpathWrapper("//*[text()='Подтверждение']", "модальное окно подтверждения");
    public XpathWrapper preloader = new XpathWrapper("//div[@style='display: none;']//div[@class='wr-spinner__spin']", "прелоадер");
    public XpathWrapper preloaderDesctop = new XpathWrapper("//*[contains(@class,'wr-app__spinner')]", "прелоадер для пк");
    public XpathWrapper preloaderKassa = new XpathWrapper("//*[contains(@class,'Spin2')]", "прелоадер для пк");
    public XpathWrapper patientDate = new XpathWrapper("//*[contains(text(),'?')]", "дата пациента");
    public XpathWrapper bootbox = new XpathWrapper("//*[contains(@class,'bootbox')]", "bootbox");
    public XpathWrapper services = new XpathWrapper("//*[@class='info-lable']", "услуги");
    public XpathWrapper chosenService = new XpathWrapper("(//label[@class='checkbox'])", "выбранная услуга");

    public XpathWrapper date = new XpathWrapper("//*[@id='reserve-information']/table/tbody/tr/td[?]", "дата записи");
    public XpathWrapper firstColumnAppointInfo = new XpathWrapper("//*[@id='reserve-information']/table/tbody/tr/td[1]", "первая колонка записи");
    public XpathWrapper lpuDate = new XpathWrapper("//*[contains(@class,'wr-lpu-schedule-filter__date')]", "дата записи (lpu)");
    public XpathWrapper anyday = new XpathWrapper("//td[contains(@class,'day')]", "проверка загрузки сетки дней");
    public XpathWrapper notPayed = new XpathWrapper("//*[contains(text(),'Онлайн прием не оплачен пациентом')]", "онлайн прием не оплачен");
}
