package tests.api;

import io.restassured.response.Response;
import models.RestController;
import models.XML.AbstractXML;
import models.XML.JsonList;
import models.XML.XML_DEV;
import models.XML.XML_PROD;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class ApiTestCase {
    private RestController restController;
    private AbstractXML xml;
    private boolean isDev;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize() throws Exception {
        isDev = System.getProperty("mode").equalsIgnoreCase("dev");
        xml = isDev ? new XML_DEV() : new XML_PROD();
        restController = new RestController();
        restController.getClientSecret();
        restController.generateToken();
    }

    @Test(description = "dbProxy")
    public void dbProxy(){
        Response response = restController.postJson("/api/dbproxy",new JsonList().selectBiClients);
        Assert.assertEquals(response.body().jsonPath().get("success").toString(), "true");
        Assert.assertNotNull(response.body().jsonPath().get("result.data[0].PCODE").toString());
        Assert.assertNotNull(response.body().jsonPath().get("result.data[1].FIRSTNAME").toString());
        Assert.assertNotNull(response.body().jsonPath().get("result.data[2].MIDNAME").toString());
        Assert.assertNotNull(response.body().jsonPath().get("result.data[3].BDATE").toString());
    }

    @Test(description = "Добавление клиента")
    public void client_add(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_ADD);
        response.then().body("ACK.CLIENT_ADD_OUT.CHECKDATA.CHECKCODE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_ADD_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.CLIENT_ADD_OUT.SPCOMMENT", Matchers.is("Пациент добавлен"));
    }

    @Test(description = "Авторизация")
    public void client_auth(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_AUTH);
        response.then().body("ACK.CLIENT_AUTH_OUT.PCODE", Matchers.is(isDev ? "990000066" : "10000001"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.PCODE", Matchers.is(isDev ? "990000066" : "10000001"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.PNAME", Matchers.is("Иван Иванович"));
//        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.PPHONE", Matchers.is("+7(927)104-46-37"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.LOGIN", Matchers.is(isDev ? "dev@infoclinica.ru" : "demo@infoclinica.ru"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.BDATE", Matchers.is(isDev ? "20110101" : "19850101"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.REFUSECALL", Matchers.is("1"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.REFUSESMS", Matchers.is("1"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.WEBSCHEDTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.WEBHISTTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.WEBPAYTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.PASSTYPE", Matchers.is("0"));
//        response.then().body("ACK.CLIENT_AUTH_OUT.CLIENT_MAININFO.NOTIFICATIONID", Matchers.is("cb1acda5-72a3-4255-b91d-e90c9ba49c4e"));
        response.then().body("ACK.CLIENT_AUTH_OUT.SPCOMMENT", Matchers.is("Пользователь авторизован"));
    }

    @Test(description = "Данные по сертификатам")
    public void client_bonus_info(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_BONUS_INFO);
        response.then().body("ACK.CLIENT_BONUS_INFO_OUT.BONUSINFO.BONUSNAME", Matchers.is(isDev ?"Бонусный сертификат" : "Карта клиники 5%"));
        response.then().body("ACK.CLIENT_BONUS_INFO_OUT.BONUSINFO.CERTIFIC_NUM", Matchers.is(isDev ? "123456789" : "150001"));
        response.then().body("ACK.CLIENT_BONUS_INFO_OUT.BONUSINFO.AMOUNTRUB", Matchers.is(isDev ?"10000.00" : "2030.00"));
        response.then().body("ACK.CLIENT_BONUS_INFO_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.CLIENT_BONUS_INFO_OUT.SPCOMMENT", Matchers.is("Данные по сертификатам пациента получены"));
    }

    @Test(description = "Поиск пациента")
    public void client_check(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_CHECK);
        response.then().body("ACK.CLIENT_CHECK_OUT.PCODE", Matchers.is(isDev ? "990000066" : "10000001"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.PCODE", Matchers.is(isDev ? "990000066" : "10000001"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.PNAME", Matchers.is("Иван Иванович"));
//        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.PPHONE", Matchers.is("+7(927)104-46-37"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.LOGIN", Matchers.is(isDev ? "dev@infoclinica.ru" : "demo@infoclinica.ru"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.BDATE", Matchers.is(isDev ? "20110101" : "19850101"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.REFUSECALL", Matchers.is("1"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.REFUSESMS", Matchers.is("1"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.WEBSCHEDTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.WEBHISTTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.WEBPAYTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.PASSTYPE", Matchers.is("0"));
//        response.then().body("ACK.CLIENT_CHECK_OUT.CLIENT_MAININFO.NOTIFICATIONID", Matchers.is("cb1acda5-72a3-4255-b91d-e90c9ba49c4e"));
        response.then().body("ACK.CLIENT_CHECK_OUT.SPCOMMENT", Matchers.is("Пациент найден"));
    }

    @Test(description = "Даннные по пациенту")
    public void client_info(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_INFO);
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.PCODE", Matchers.is(isDev ?"990000066" : "10000001"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.PNAME", Matchers.is("Иван Иванович"));
//        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.PPHONE", Matchers.is("+7(927)104-46-37"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.LOGIN", Matchers.is(isDev ? "dev@infoclinica.ru" : "demo@infoclinica.ru"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.BDATE", Matchers.is(isDev ?"20110101" : "19850101"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.REFUSECALL", Matchers.is("1"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.REFUSESMS", Matchers.is("1"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.WEBSCHEDTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.WEBHISTTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.WEBPAYTYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.PASSTYPE", Matchers.is("0"));
//        response.then().body("ACK.CLIENT_INFO_OUT.CLIENT_MAININFO.NOTIFICATIONID", Matchers.is("cb1acda5-72a3-4255-b91d-e90c9ba49c4e"));
        response.then().body("ACK.CLIENT_INFO_OUT.SPCOMMENT", Matchers.is("Данные по пациенту получены"));
    }

    @Test(description = "Изменение данных пациента")
    public void client_modify(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_MODIFY);
        response.then().body("ACK.CLIENT_MODIFY_OUT.PCODE", Matchers.is(isDev ? "990000066" : "10000001"));
        response.then().body("ACK.CLIENT_MODIFY_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.CLIENT_MODIFY_OUT.SPCOMMENT", Matchers.is("Данные пациента сохранены"));
    }

    @Test(description = "Добавление платежа")//TODO : Долг по счету не найден. ORDERID = 10016781 DEV
    public void client_payment_add(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_PAYMENT_ADD);
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.CLIENT_MAININFO.PCODE", Matchers.is(isDev ? "990000066" : "10000001"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.CLIENT_MAININFO.PNAME", Matchers.is("Иван Иванович"));
//        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.CLIENT_MAININFO.PPHONE", Matchers.is("+7(927)104-46-37"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.CLIENT_MAININFO.BDATE", Matchers.is(isDev ? "20110101" : "19850101"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.CLIENT_MAININFO.REFUSECALL", Matchers.is("1"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.CLIENT_MAININFO.REFUSECALL", Matchers.is("1"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.SCHEDID", Matchers.is(isDev ? "10006517" : "10008508"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.WORKDATE", Matchers.is(isDev ? "20200625" : "20200619"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.BHOUR", Matchers.is(isDev ? "8" : "13"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.BMIN", Matchers.is("0"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.FHOUR", Matchers.is(isDev ? "8" : "13"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.FMIN", Matchers.is("30"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.FILIAL", Matchers.is("1"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.DCODE", Matchers.is(isDev ? "10000007" : "10000002"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.CASHID", Matchers.is("0"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.SCHEDIDENT", Matchers.is(isDev ? "10005659" : "10006977"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SCHED_INFO.ONLINETYPE", Matchers.is("1"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.CLIENT_PAYMENT_ADD_OUT.SPCOMMENT", Matchers.is("Платеж успешно добавлен"));
    }

    @Test(description = "Данные по платежам")
    public void client_payment_list(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_PAYMENT_LIST);
        response.then().body("ACK.CLIENT_PAYMENT_LIST_OUT.RECORDSDATA.ITEMSNUMBER", Matchers.is("20"));
        response.then().body("ACK.CLIENT_PAYMENT_LIST_OUT.RECORDSDATA.FIRSTROW", Matchers.is("1"));
        response.then().body("ACK.CLIENT_PAYMENT_LIST_OUT.RECORDSDATA.LASTROW", Matchers.is("20"));
        response.then().body("ACK.CLIENT_PAYMENT_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.CLIENT_PAYMENT_LIST_OUT.SPCOMMENT", Matchers.is("Данные по платежам получены"));
    }

    @Test(description = "Данные по отчетам")
    public void client_report_list(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_REPORT_LIST);
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[0].DOCID", Matchers.is(isDev ? "10001" : "990000010"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[0].DOCNAME", Matchers.is(isDev ? "Информация для пациента 1" : "Акт по выполненным работам"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[0].DOCTYPE", Matchers.is(isDev ? "1" : "2"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[0].DOCCOMMENT", Matchers.is(isDev ? "Тестовое описание" : "Детальный отчет по пациенту по выполненным работам за период"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[1].DOCID", Matchers.is(isDev ? "10002" : "990000009"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[1].DOCNAME", Matchers.is(isDev ? "Информация для пациента 2" : "Выписка из бонусного счета"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[1].DOCTYPE", Matchers.is(isDev ? "2" : "1"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.CLIENTREPORTDOC[1].DOCCOMMENT", Matchers.is(isDev ? "Отчет в разработке !!!" : "Детальный отчет по начислению и списанию средств с бонусных сертификатов пациента"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.CLIENT_REPORT_LIST_OUT.SPCOMMENT", Matchers.is("Данные по отчетам получены"));
    }

    @Test(description = "Данные по протоколам")
    public void client_treatplace_list(){
        Response response = restController.postXml("/api/xml", xml.CLIENT_TREATPLACE_LIST);
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].PROTOCOLID", Matchers.is(isDev ? "20000001" : "10000006"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].TREATCODE", Matchers.is(isDev ? "20000131" : "10012966"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].PROTDATE", Matchers.is(isDev ? "20191106" : "20171116"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].DCODE", Matchers.is(isDev ? "20000006" : "10000007"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].DOCTNAME", Matchers.is("Веселый Роман Сергеевич"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].PROTOCOLNAME", Matchers.is(isDev ? "РМ Тест" : "Прием офтальмолога"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].DEPNUM", Matchers.is("40001402"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].DEPNAME", Matchers.is("Терапевт"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].FILIAL", Matchers.is(isDev ? "2" : "1"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].FILNAME", Matchers.is(isDev ? "Клиника №2" : "Клиника №1"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.TREATPLACE[0].SIGNDATE", Matchers.is(isDev ? "20191106173641" : "20171117010356"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.RECORDSDATA.ITEMSNUMBER", Matchers.is(isDev ? "20" : "2"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.RECORDSDATA.FIRSTROW", Matchers.is("1"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.RECORDSDATA.LASTROW", Matchers.is("20"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.CLIENT_TREATPLACE_LIST_OUT.SPCOMMENT", Matchers.is("Данные по протоколам получены"));
    }


    @Test(description = "Расписание работы врачей")
    public void doctor_schedule(){
        Response response = restController.postXml("/api/xml", xml.DOCT_SCHEDULE);
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].SCHEDIDENT", Matchers.is(isDev ? "30001488" : "30001925"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].DCODE", Matchers.is(isDev ? "30000002" : "30000003"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].DNAME", Matchers.is(isDev ? "Пушкин Александр Сергеевич" : "Кашпировский Анатолий Михайлович"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].DEPNUM", Matchers.is(isDev ? "40001402" : "16212"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].DEPNAME", Matchers.is(isDev ? "Терапевт" : "Психотерапия"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].FILIAL", Matchers.is("3"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].FNAME", Matchers.is("Клиника №3"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].WDATE", Matchers.is("20200610"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].BEGHOUR", Matchers.is(isDev ? "8" : "9"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].BEGMIN", Matchers.is("0"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].ENDHOUR", Matchers.is(isDev ? "21" : "20"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].ENDMIN", Matchers.is("0"));
        response.then().body("ACK.DOCT_SCHEDULE_OUT.DOCTSCHED[0].ONLINEMODE", Matchers.is(isDev ? "1" : "0"));
    }

    @Test(description = "Расписание свободных дней врачей")
    public void doctor_schedule_free(){
        Response response = restController.postXml("/api/xml", xml.DOCT_SCHEDULE_FREE);
        response.then().body("ACK.DOCT_SCHEDULE_FREE_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.DOCT_SCHEDULE_FREE_OUT.SPCOMMENT", Matchers.is("Расписание работы врачей получено"));
    }

    @Test(description = "Данные по отделениям")
    public void get_department_list(){
        Response response = restController.postXml("/api/xml", xml.GET_DEPARTMENT_LIST);
        response.then().body("ACK.GET_DEPARTMENT_LIST_OUT.GETDEPARTMENTLIST[0].DEPNUM", Matchers.is("993291358"));
        response.then().body("ACK.GET_DEPARTMENT_LIST_OUT.GETDEPARTMENTLIST[0].DEPNAME", Matchers.is("Администрация"));
        response.then().body("ACK.GET_DEPARTMENT_LIST_OUT.GETDEPARTMENTLIST[0].DEPGRPNAME", Matchers.is("Отделения"));
        response.then().body("ACK.GET_DEPARTMENT_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.GET_DEPARTMENT_LIST_OUT.SPCOMMENT", Matchers.is("Данные по отделениям получены"));

    }

    @Test(description = "Данные по врачам")
    public void get_doctor_list(){
        Response response = restController.postXml("/api/xml", xml.GET_DOCTOR_LIST);

        response.then().body("ACK.GET_DOCTOR_LIST_OUT.GETDOCTORLIST[0].DCODE", Matchers.is("20000002"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.GETDOCTORLIST[0].DNAME", Matchers.is("Грозный Иван Васильевич"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.GETDOCTORLIST[0].DEPNUM", Matchers.is("40001402"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.GETDOCTORLIST[0].DEPNAME", Matchers.is("Терапевт"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.GETDOCTORLIST[0].FILIAL", Matchers.is("2"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.GETDOCTORLIST[0].FNAME", Matchers.is("Клиника №2"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.GETDOCTORLIST[0].VIEWINWEB", Matchers.is("1"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.GET_DOCTOR_LIST_OUT.SPCOMMENT", Matchers.is("Данные по врачам получены"));
    }

    @Test(description = "Данные по филиалам")
    public void get_filial_list(){//DEV [1] PROD [0]
        Response response = restController.postXml("/api/xml", xml.GET_FILIAL_LIST);
        String filial = "ACK.GET_FILIAL_LIST_OUT.GETFILIALLIST[?]";
        filial = isDev ? filial.replace("?", "1") : filial.replace("?", "0");
        response.then().body(filial + ".FILIAL", Matchers.is("1"));
        response.then().body(filial + ".FNAME", Matchers.is("Клиника №1"));
        response.then().body(filial + ".FADDR", Matchers.is("Москва, ул. Тверская, д. 1"));
        response.then().body(filial + ".FPHONE", Matchers.is("+7(000)000-0001"));
        response.then().body(filial + ".FMAIL", Matchers.is("demo@infoclinica.ru"));
        response.then().body(filial + ".VIEWINWEB_OUT", Matchers.is("1"));
//        response.then().body("ACK.GET_FILIAL_LIST_OUT.GETFILIALLIST[1].MEDIAID", Matchers.is("562faa30e4b0a306aa1e7b38"));
        response.then().body("ACK.GET_FILIAL_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.GET_FILIAL_LIST_OUT.SPCOMMENT", Matchers.is("Данные по филиалам получены"));
    }

    @Test(description = "Данные по контактным лицам")
    public void get_jpcontact_list(){

        Response response = restController.postXml("/api/xml", xml.GET_JPCONTACT_LIST);
        response.then().body("ACK.GET_JPCONTACT_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.GET_JPCONTACT_LIST_OUT.SPCOMMENT", Matchers.is("Данные по контактным лицам контрагентов получены"));
    }

    @Test(description = "Данные по ценам")
    public void price_list(){
        Response response = restController.postXml("/api/xml", xml.PRICE_LIST);
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].SCHID", Matchers.is(isDev ? "10000002" : "990000001"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].KODOPER", Matchers.is(isDev ? "123" : " У 1055"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].SCHNAME", Matchers.is(isDev ? "Консультация специалиста специалиста специалиста специалиста специалиста специалиста специалиста специалиста специалиста специалиста специалиста специалиста специалиста" : "Первичный прием уролога"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].SPRICE", Matchers.is(isDev ? "1000.00" : "600.00"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].DISCPERCENT", Matchers.is("100.00"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].DISCPRICE", Matchers.is(isDev ? "1000.00" : "600.00"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].STRUCTNAME", Matchers.is("Текущий прейскурант"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].FILIAL", Matchers.is("1"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].FNAME", Matchers.is(isDev ? "Прейскурант №1" : "Базовый прейскурант"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].SPECCODE", Matchers.is(isDev ? "9" : "17"));
        response.then().body("ACK.PRICE_LIST_OUT.SCH[0].SPECNAME", Matchers.is(isDev ? "18 ТЕРАПИЯ" : "26 УРОЛОГИЯ"));
    }

    @Test(description = "Данные по расписанию")
    public void schedule(){
        Response response = restController.postXml("/api/xml", xml.SCHEDULE);

        response.then().body("ACK.SCHEDULE_OUT.DEP.DEPNUM", Matchers.is(isDev ? "40001402" : "40000017"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.DCODE", Matchers.is(isDev ? "10000002" : "10000003"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].WDATE", Matchers.is("20200610"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].SCHEDINT.SCHEDIDENT", Matchers.is(isDev ? "10006238" : "10007859"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].SCHEDINT.RNUM", Matchers.is(isDev ? "К1" : "К2"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].SCHEDINT.INTERVAL[0].BHOUR", Matchers.is(isDev ? "8" : "14"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].SCHEDINT.INTERVAL[0].BMIN", Matchers.is("0"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].SCHEDINT.INTERVAL[0].FHOUR", Matchers.is(isDev ? "8" : "14"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].SCHEDINT.INTERVAL[0].FMIN", Matchers.is("30"));
        response.then().body("ACK.SCHEDULE_OUT.DEP.DOCT.WRDATE[0].SCHEDINT.INTERVAL[0].FREETYPE", Matchers.is("-6"));

        response.then().body("ACK.SCHEDULE_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.SCHEDULE_OUT.SPCOMMENT", Matchers.is("Данные по расписанию получены"));

    }

    @Test(description = "???")
    public void schedule_change_accept(){
        Response response = restController.postXml("/api/xml", xml.SCHEDULE_CHANGE_ACCEPT);
        response.then().body("ACK.SCHEDULE_CHANGE_ACCEPT_OUT.SPRESULT", Matchers.is("-99"));
        response.then().body("ACK.SCHEDULE_CHANGE_ACCEPT_OUT.SPCOMMENT", Matchers.is("Начало блока CHANGE_INFO не найдено"));
    }

    @Test(description = "Данные по записям")
    public void schedule_list(){
        Response response = restController.postXml("/api/xml", xml.SCHEDULE_LIST);
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].SCHEDID", Matchers.is(isDev ? "10005963" : "20001454"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].WORKDATE", Matchers.is(isDev ?"20200609" : "20200609"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].BHOUR", Matchers.is("8"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].BMIN", Matchers.is(isDev ? "0" : "30"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].FHOUR", Matchers.is(isDev ? "8" : "9"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].FMIN", Matchers.is(isDev ? "30" : "0"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].FILIAL", Matchers.is(isDev ? "1" : "2"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].FNAME", Matchers.is(isDev ? "Клиника №1" : "Клиника №2"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].DCODE", Matchers.is(isDev ? "10000007" : "20000002"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].DNAME", Matchers.is(isDev ? "Веселый Роман Сергеевич" : "Грозный Иван Васильевич"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].DEPNUM", Matchers.is(isDev ? "40000005" : "40001402"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].DEPNAME", Matchers.is(isDev ? "Уролог" : "Терапевт"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].RNUM", Matchers.is(isDev ? "К3" : "К1"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].CHNAME", Matchers.is(isDev ? "Кабинет 3" : "Кабинет 1"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].PCODE", Matchers.is(isDev ? "990000066" : "10000001"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].PNAME", Matchers.is("Иван Иванович"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].PBIRTHDATE", Matchers.is(isDev ? "20110101" : "19850101"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].PGENDER", Matchers.is("1"));
//        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].PPHONE", Matchers.is("+7(927)104-46-37"));
        response.then().body("ACK.SCHEDULE_LIST_OUT.SCHEDULEREC[0].ONLINETYPE", Matchers.is("1"));
    }

    @Test(description = "Список приемов")
    public void schedule_rec_list(){
        Response response = restController.postXml("/api/xml", xml.SCHEDULE_REC_LIST);
        response.then().body("ACK.SCHEDULE_REC_LIST_OUT.RECORDSDATA.ITEMSNUMBER", Matchers.is("20"));
        response.then().body("ACK.SCHEDULE_REC_LIST_OUT.RECORDSDATA.FIRSTROW", Matchers.is("1"));
        response.then().body("ACK.SCHEDULE_REC_LIST_OUT.RECORDSDATA.LASTROW", Matchers.is("20"));

        response.then().body("ACK.SCHEDULE_REC_LIST_OUT.SPRESULT", Matchers.is("1"));
        response.then().body("ACK.SCHEDULE_REC_LIST_OUT.SPCOMMENT", Matchers.is("Список приемов"));
    }

    @Test(description = "Отмена записи")
    public void schedule_rec_remove(){
        Response response = restController.postXml("/api/xml", xml.SCHEDULE_REC_REMOVE);
        response.then().body("ACK.SCHEDULE_REC_REMOVE_OUT.CHECKDATA.CHECKCODE", Matchers.is("2"));
        response.then().body("ACK.SCHEDULE_REC_REMOVE_OUT.CHECKDATA.CHECKTEXT", Matchers.is("Ваша запись на прием уже отменена, список записей будет обновлен в ближайшее время"));
        response.then().body("ACK.SCHEDULE_REC_REMOVE_OUT.SPRESULT", Matchers.is("0"));
        response.then().body("ACK.SCHEDULE_REC_REMOVE_OUT.SPCOMMENT", Matchers.is("Запись пациента не найдена"));
    }

    @Test(description = "Запись")
    public void schedule_reserve(){
        Response response = restController.postXml("/api/xml", xml.SHEDULE_TEST_OK_RESERVE);
        response.then().body("ACK.SCHEDULE_REC_RESERVE_OUT.CHECKDATA.CHECKCODE", Matchers.is("2"));
        response.then().body("ACK.SCHEDULE_REC_RESERVE_OUT.CHECKDATA.CHECKLABEL", Matchers.is("Время занято"));
        response.then().body("ACK.SCHEDULE_REC_RESERVE_OUT.CHECKDATA.CHECKTEXT", Matchers.is("К сожалению, выбранное время было недавно занято. Пожалуйста, выберите другое время."));
        response.then().body("ACK.SCHEDULE_REC_RESERVE_OUT.SPRESULT", Matchers.is("0"));
        response.then().body("ACK.SCHEDULE_REC_RESERVE_OUT.SPCOMMENT", Matchers.is("К сожалению, выбранное время было недавно занято. Пожалуйста, выберите другое время."));
    }

    @Test(description = "Обновить запись")
    public void schedule_update(){
        Response response = restController.postXml("/api/xml", xml.SCHEDULE_REC_UPDATE);
        response.then().body("ACK.SCHEDULE_REC_UPDATE_OUT.SPRESULT", Matchers.is(isDev ? "-2" : "0"));
        response.then().body("ACK.SCHEDULE_REC_UPDATE_OUT.SPCOMMENT", Matchers.is(isDev ? "Запись пациента не найдена" : "Изменение отметки о посещении в прошедших назначениях запрещено"));
    }

    @Test(description = "Отчет")
    public void report_api(){
        Response response = isDev ? restController.get("/api/report?fdate=2020-05-31&format=html&docid=10002&bdate=2018-05-31&loginType=11&pcode=990000066") :
                                    restController.get("/api/report?fdate=2020-05-31&format=html&docid=990000010&bdate=2018-05-31&loginType=11&pcode=10000001");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(description = "Оплата POST")
    public void post_payment(){
            Response response =
                    restController.post("/api/payment?" +
                                    "pcode=?&".replace("?", isDev ? "990000066" : "10000001") +
                                    "orderid=?&".replace("?", isDev ? "10019458" : "10022568") +
                                    "payprofileid=?&".replace("?", isDev ? "57a9e44541738e6dd21d5606" : "594a92644ccd785d64599c55") +
                                    "payamount=1000&".replace("?", isDev ? "1000" : "500") +
                                    "paymethod=AC&" +
                                    "filial=1&" +
                                    "successurl=https://yandex.ru&" +
                                    "errorurl=https://google.com");

            Assert.assertEquals(response.getBody().asString()
                    .contains("<button class=\"wr-payment-offer__ok\" type=\"button\">Согласен(на)</button> </div>"), true);
            Assert.assertEquals(response.getBody().asString()
                    .contains("return checkout.render('payment-form');"), true);
    }

    @Test(description = "Оплата GET")
    public void get_payment() throws UnsupportedEncodingException {
        String builderUrl = "/api/payment?" +
                "access_token=" + restController.getAccessToken()+ "&" +
                "pcode=?&".replace("?", isDev ? "990000066" : "10000001") +
                "orderid=?&".replace("?", isDev ? "10019458" : "10022568") +
                "payprofileid=?&".replace("?", isDev ? "57a9e44541738e6dd21d5606" : "594a92644ccd785d64599c55") +
                "payamount=?&".replace("?", isDev ? "1000" : "500") +
                "paymethod=AC&" +
                "filial=1&" +
                "successurl=https://yandex.ru&" +
                "errorurl=https://google.com";
        Response response = restController.get(builderUrl);
        Assert.assertEquals(response.getBody().asString()
                .contains("<button class=\"wr-payment-offer__ok\" type=\"button\">Согласен(на)</button> </div>"), true);
        Assert.assertEquals(response.getBody().asString()
                .contains("return checkout.render('payment-form');"), true);
    }

    @Test(description = "Pay")
    public void pay(){
        String builderUrl = "/pay?" +
                "pcode=?&".replace("?", isDev ? "990000066" : "10000001") +
                "orderid=?&".replace("?", isDev ? "10019458" : "10022568") +
                "filial=1";
        Response response = restController.get(builderUrl);
        Assert.assertEquals(response.getBody().asString()
                .contains("<button class=\"wr-payment-offer__ok\" type=\"button\">Согласен(на)</button> </div>"), true);
        Assert.assertEquals(response.getBody().asString()
                .contains("return checkout.render('payment-form');"), true);
    }

    @Test(description = "cert")
    public void cert(){
        try {
            Response response = restController.postWithCert("/api/widget/login", new JsonList().trueConfLogin);
            response.getBody().asString();
            JSONObject jsonObject = new JSONObject(response.getBody().asString());
            String loginToken = jsonObject.getJSONObject("data").get("authToken").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
