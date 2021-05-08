package pages;

import models.wrappers.XpathWrapper;

public class Yandex {
    public static XpathWrapper search_input = new XpathWrapper("//input[@aria-label='Запрос']", "поле поискового запроса");
    public static XpathWrapper search_submit_button = new XpathWrapper("//button[@data-bem='{\"button\":{}}']", "кнопка поиск");
}
