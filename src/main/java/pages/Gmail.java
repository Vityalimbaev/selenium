package pages;

import models.wrappers.XpathWrapper;

public class Gmail {
    public XpathWrapper input_login = new XpathWrapper("//*[@class='scpt']","поле логина");

    public XpathWrapper btn_next = new XpathWrapper("//*[@type='submit']","кнопка продолжить");
    public XpathWrapper btn_refresh = new XpathWrapper("//*[@class='mgif irefresh b']","кнопка обновления");

    public XpathWrapper iframe_mail = new XpathWrapper("//*[@name='ifmail']" ,"iframe письма");
}
