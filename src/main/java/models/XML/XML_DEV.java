package models.XML;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XML_DEV extends AbstractXML{
    private String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
    private String uid = String.valueOf(java.util.UUID.randomUUID());

    public String addappoint = "<WEB_SCHEDULE_REC_RESERVE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
            "  <MSH>\n" +
            "    <MSH.7>\n" +
            "      <TS.1>"+ this.date + "</TS.1>\n" +
            "    </MSH.7>\n" +
            "    <MSH.9>\n" +
            "      <MSG.1>WEB</MSG.1>\n" +
            "      <MSG.2>SCHEDULE_REC_RESERVE</MSG.2>\n" +
            "    </MSH.9>\n" +
            "    <MSH.10>2b4543e422e210dbd7635ddf</MSH.10>\n" +
            "    <MSH.18>UTF-8</MSH.18>\n" +
            "    <MSH.99>1</MSH.99>\n" +
            "  </MSH>\n" +
            "  <SCHEDULE_REC_RESERVE_IN>\n" +
            "    <DCODE>10000007</DCODE>\n" +
            "    <WORKDATE>?</WORKDATE>\n" +             //DATE YYYYMMDD
            "    <BHOUR>8</BHOUR>\n" +                          //TIME START
            "    <BMIN>30</BMIN>\n" +
            "    <FHOUR>9</FHOUR>\n" +                          //TIME STOP
            "    <FMIN>0</FMIN>\n" +
            "    <SCHEDIDENT>10005702</SCHEDIDENT>\n" +         //DOCTOR ID
            "    <PCODE>990000066</PCODE>\n" +                  //PACIENT ID
            "    <ONLINETYPE>1</ONLINETYPE>\n" +
            "  </SCHEDULE_REC_RESERVE_IN>\n" +
            "</WEB_SCHEDULE_REC_RESERVE>";

    public String removeappoint = "<WEB_SCHEDULE_REC_REMOVE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
            "  <MSH>\n" +
            "    <MSH.7>\n" +
            "      <TS.1>"+ this.date +"</TS.1>\n" +
            "    </MSH.7>\n" +
            "    <MSH.9>\n" +
            "      <MSG.1>WEB</MSG.1>\n" +
            "      <MSG.2>SCHEDULE_REC_REMOVE</MSG.2>\n" +
            "    </MSH.9>\n" +
            "    <MSH.10>d72a1a594aa8bff44a3c0753</MSH.10>\n" +
            "    <MSH.18>UTF-8</MSH.18>\n" +
            "    <MSH.99>1</MSH.99>\n" +
            "  </MSH>\n" +
            "  <SCHEDULE_REC_REMOVE_IN>\n" +
            "    <PCODE>990000066</PCODE>\n" +
            "    <SCHEDID>?</SCHEDID>\n" +
            "  </SCHEDULE_REC_REMOVE_IN>\n" +
            "</WEB_SCHEDULE_REC_REMOVE>";

    public String sendNotificationJson = "[\n" +
            "            {\n" +
            "                \"notification\": {\n" +
            "                    \"body\":\"The message 990000009\"\n" +
            "                },\n" +
            "                \"time_to_live\": 0,\n" +
            "                \"to\": [\"a2c0aae7-ef2d-4f1f-b1c8-2566bf635f5d\"],\n" +
            "                \"message_id\": \"990000009\",\n" +
            "                \"data\": {\n" +
            "                    \"user_login\":\"doctor\"\n" +
            "                }\n" +
            "            }\n" +
            "        ]";

    public String sendNotificationJson2 = "[{\n" +
            "            \"message_id\": \"*\",\n" +
            "            \"to\": [\"?\"],\n" +
            "            \"notification\": {\n" +
            "                \"body\": \"Удаление двух зубов по цене одного. Не упустите свой шанс обрести бесподобную улыбку. Только сегодня и только у нас на http://www.sdsys.ru/\"\n" +
            "            },\n" +
            "            \"data\": {\n" +
            "                \"user_login\": \"dev@infoclinica.ru\"\n" +
            "            }\n" +
            "        }]";

    public XML_DEV() {

        CLIENT_ADD = "<WEB_CLIENT_ADD xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>20200610062514</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>WEB</MSG.1>\n" +
                "      <MSG.2>CLIENT_ADD</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>80c1cc8b409eebc70c65ac76</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <CLIENT_ADD_IN>\n" +
                "    <LASTNAME>Тест</LASTNAME>\n" +
                "    <FIRSTNAME>Тестов</FIRSTNAME>\n" +
                "    <MIDNAME>Тестович</MIDNAME>\n" +
                "    <EMAIL>autotest@sds.ru</EMAIL>\n" +
                "    <PHONE>+7(000)000-00-00</PHONE>\n" +
                "    <BDATE>20000101</BDATE>\n" +
                "    <GENDER>1</GENDER>\n" +
                "    <CLLOGIN>autotest@sds.ru</CLLOGIN>\n" +
                "    <CLPASSWORD>{key}password</CLPASSWORD>\n" +
                "    <REFUSECALL>1</REFUSECALL>\n" +
                "    <REFUSESMS>1</REFUSESMS>\n" +
                "  </CLIENT_ADD_IN>\n" +
                "</WEB_CLIENT_ADD>";

        CLIENT_AUTH =
                "<WEB_CLIENT_AUTH xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>567d3b7fdcd497c7dfdce901</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610094058</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_AUTH</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>0519b6b5c042ce0a6c5f5f75</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_AUTH_IN>\n" +
                        "    <LOGINTYPE>11</LOGINTYPE>\n" +
                        "    <LOGIN>dev@infoclinica.ru</LOGIN>\n" +
                        "    <PASSWORD>{key}881308cc3ebf3fd343e38f31c83625a9cd953e16</PASSWORD>\n" +
                        "  </CLIENT_AUTH_IN>\n" +
                        "</WEB_CLIENT_AUTH>";


        CLIENT_BONUS_INFO =
                "<WEB_CLIENT_BONUS_INFO xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200625032825</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_BONUS_INFO</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>9bccf26b48c1da650999d6db</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_BONUS_INFO_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "  </CLIENT_BONUS_INFO_IN>\n" +
                        "</WEB_CLIENT_BONUS_INFO>";
        CLIENT_CHECK =
                "<WEB_CLIENT_CHECK xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610060948</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_CHECK</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>18fad24316219b32e0c27b04</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_CHECK_IN>\n" +
                        "    <LOGIN>dev@infoclinica.ru</LOGIN>\n" +
                        "    <CHECKMODE>11</CHECKMODE>\n" +
                        "  </CLIENT_CHECK_IN>\n" +
                        "</WEB_CLIENT_CHECK>";
        CLIENT_INFO =
                "<WEB_CLIENT_INFO xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610095022</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_INFO</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>2e5d4ddae692e5dd6cf884bb</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_INFO_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "  </CLIENT_INFO_IN>\n" +
                        "</WEB_CLIENT_INFO>";
        CLIENT_MODIFY =
                "<WEB_CLIENT_MODIFY xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610062652</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_MODIFY</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>14c69cc3ca466db66e074013</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_MODIFY_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <PHONE></PHONE>\n" +
                        "    <EMAIL></EMAIL>\n" +
                        "    <REFUSECALL>1</REFUSECALL>\n" +
                        "    <REFUSESMS>1</REFUSESMS>\n" +
                        "    <CLLOGIN>dev@infoclinica.ru</CLLOGIN>\n" +
                        "    <CLPASSWORD>{key}881308cc3ebf3fd343e38f31c83625a9cd953e16</CLPASSWORD>\n" +
                        "  </CLIENT_MODIFY_IN>\n" +
                        "</WEB_CLIENT_MODIFY>";
        CLIENT_PAYMENT_ADD =
                "<WEB_CLIENT_PAYMENT_ADD xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610050054</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_PAYMENT_ADD</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>2e4105798e5fdb1a56310bd9</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>1</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_PAYMENT_ADD_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <ORDERID>10016781</ORDERID>\n" +
                        "    <PAYMODE>0</PAYMODE>\n" +
                        "    <PAYAMOUNT>500.0</PAYAMOUNT>\n" +
                        "    <PAYDATE>20200610</PAYDATE>\n" +
                        "    <PAYCOMMENT>Adding a payment - Payment(Some(10017867),Some(10.06.2020),None,None,Some(Оплата приема),Some(594a92644ccd785d64599c55),Some(1),NotPaid,500,Some(1),None,None,None,Some(&quot;Онлайн консультация терапевта&quot; ),None) by payMode 0.</PAYCOMMENT>\n" +
                        "  </CLIENT_PAYMENT_ADD_IN>\n" +
                        "</WEB_CLIENT_PAYMENT_ADD>";
        CLIENT_PAYMENT_LIST =
                "<WEB_CLIENT_PAYMENT_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610102306</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_PAYMENT_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>ddc231c017ddde44ea6b1898</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_PAYMENT_LIST_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <FIRSTROW>1</FIRSTROW>\n" +
                        "    <LASTROW>20</LASTROW>\n" +
                        "  </CLIENT_PAYMENT_LIST_IN>\n" +
                        "</WEB_CLIENT_PAYMENT_LIST>";
        CLIENT_REPORT_LIST =
                "<WEB_CLIENT_REPORT_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610045417</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_REPORT_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>dd68b0238deaefb4928b37ec</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_REPORT_LIST_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "  </CLIENT_REPORT_LIST_IN>\n" +
                        "</WEB_CLIENT_REPORT_LIST>";
        CLIENT_TREATPLACE_LIST =
                "<WEB_CLIENT_TREATPLACE_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610102305</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>CLIENT_TREATPLACE_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>eaf4f4425d9f9eb8194e1ab1</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <CLIENT_TREATPLACE_LIST_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <BDATE>19900101</BDATE>\n" +
                        "    <FDATE>20200610</FDATE>\n" +
                        "    <FIRSTROW>1</FIRSTROW>\n" +
                        "    <LASTROW>20</LASTROW>\n" +
                        "  </CLIENT_TREATPLACE_LIST_IN>\n" +
                        "</WEB_CLIENT_TREATPLACE_LIST>";
        DOCT_SCHEDULE =
                "<WEB_DOCT_SCHEDULE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>5b2d0b414ccd7803fb23961d</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610030123</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>DOCT_SCHEDULE</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>148649f3151c5431ee0910c0d153e46c</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <DOCT_SCHEDULE_IN>\n" +
                        "    <FILLIST>3</FILLIST>\n" +
                        "    <BDATE>20200610</BDATE>\n" +
                        "    <FDATE>20200617</FDATE>\n" +
                        "  </DOCT_SCHEDULE_IN>\n" +
                        "</WEB_DOCT_SCHEDULE>";
        DOCT_SCHEDULE_FREE =
                "<WEB_DOCT_SCHEDULE_FREE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610102437</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>DOCT_SCHEDULE_FREE</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>c9c04ad84eeb63476484b82c</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>2</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <DOCT_SCHEDULE_FREE_IN>\n" +
                        "    <DEPLIST>40001402</DEPLIST>\n" +
                        "    <DOCTLIST>20000002</DOCTLIST>\n" +
                        "    <BDATE>20200601</BDATE>\n" +
                        "    <FDATE>20200630</FDATE>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "  </DOCT_SCHEDULE_FREE_IN>\n" +
                        "</WEB_DOCT_SCHEDULE_FREE>";
        GET_DEPARTMENT_LIST =
                "<WEB_GET_DEPARTMENT_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>59636a904ccd7847b3daf242</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610074507</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>GET_DEPARTMENT_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>5ee08f8376dc0</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <GET_DEPARTMENT_LIST_IN>\n" +
                        "    <FILLIST>2</FILLIST>\n" +
                        "    <DEPNUMIN>-1</DEPNUMIN>\n" +
                        "    <VIEWINWEB>-1</VIEWINWEB>\n" +
                        "  </GET_DEPARTMENT_LIST_IN>\n" +
                        "</WEB_GET_DEPARTMENT_LIST>";
        GET_DOCTOR_LIST =
                "<WEB_GET_DOCTOR_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>59636a904ccd7847b3daf242</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610074509</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>GET_DOCTOR_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>5ee08f85c0b52</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <GET_DOCTOR_LIST_IN>\n" +
                        "    <FILLIST>2</FILLIST>\n" +
                        "    <VIEWINWEB>-1</VIEWINWEB>\n" +
                        "    <FIRSTROW>1</FIRSTROW>\n" +
                        "    <LASTROW>501</LASTROW>\n" +
                        "  </GET_DOCTOR_LIST_IN>\n" +
                        "</WEB_GET_DOCTOR_LIST>";
        GET_FILIAL_LIST =
                "<WEB_GET_FILIAL_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>5837240b174ce70a315b29a6</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>21000101000000</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>GET_FILIAL_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>0000000000</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <GET_FILIAL_LIST_IN/>\n" +
                        "</WEB_GET_FILIAL_LIST>";
        GET_JPCONTACT_LIST =
                "<WEB_GET_JPCONTACT_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610021919</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>GET_JPCONTACT_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>27d90d5686c2600c93ffbd91</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <GET_JPCONTACT_LIST_IN>\n" +
                        "    <FILIAL>1</FILIAL>\n" +
                        "  </GET_JPCONTACT_LIST_IN>\n" +
                        "</WEB_GET_JPCONTACT_LIST>";
        PRICE_LIST =
                "<WEB_PRICE_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610103002</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>PRICE_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>0c020daabe722d8a85118ec6</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <PRICE_LIST_IN>\n" +
                        "    <DEPNUM>40001402</DEPNUM>\n" +
                        "  </PRICE_LIST_IN>\n" +
                        "</WEB_PRICE_LIST>";
        SCHEDULE =
                "<WEB_SCHEDULE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>57fe408f174ce743ffc90478</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610084406</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>117ccb4080f646109799369f07494490</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>1</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_IN>\n" +
                        "    <DCODEIN>10000002</DCODEIN>\n" +
                        "    <BDATE>20200610</BDATE>\n" +
                        "    <FDATE>20200711</FDATE>\n" +
                        "    <ONLINEMODE>0</ONLINEMODE>\n" +
                        "  </SCHEDULE_IN>\n" +
                        "</WEB_SCHEDULE>";
        SCHEDULE_CHANGE_ACCEPT =
                "<WEB_SCHEDULE_CHANGE_ACCEPT xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>57fe408f174ce743ffc90478</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610084638</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_CHANGE_ACCEPT</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>456a93bed0cf4a089bfedc34b1336438</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>2</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_CHANGE_ACCEPT_IN/>\n" +
                        "</WEB_SCHEDULE_CHANGE_ACCEPT>";
        SCHEDULE_CHANGE_LIST =
                "<WEB_SCHEDULE_CHANGE_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.3>59636a904ccd7847b3daf242</MSH.3>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610085503</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_CHANGE_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>5ee09fe78da5d</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>1</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_CHANGE_LIST_IN/>\n" +
                        "</WEB_SCHEDULE_CHANGE_LIST>";
        SCHEDULE_INFO =
                "<WEB_SCHEDULE_INFO xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610095046</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_INFO</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>450d158650ca28018c5245bb</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_INFO_IN>\n" +
                        "    <SCHEDID>20001459</SCHEDID>\n" +
                        "  </SCHEDULE_INFO_IN>\n" +
                        "</WEB_SCHEDULE_INFO>";
        SCHEDULE_LIST =
                "<WEB_SCHEDULE_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610035411</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>1543030a5eddb96ddd28b6a2</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_LIST_IN>\n" +
                        "    <BDATE>20200609</BDATE>\n" +
                        "    <FDATE>20200609</FDATE>\n" +
//                    "    <EXTPCODE>990000025</EXTPCODE>\n" +
                        "  </SCHEDULE_LIST_IN>\n" +
                        "</WEB_SCHEDULE_LIST>";
        SCHEDULE_REC_LIST =
                "<WEB_SCHEDULE_REC_LIST xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610102253</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_REC_LIST</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>8b0aecc3e55c14ba3822b216</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_REC_LIST_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <BDATE>20200610</BDATE>\n" +
                        "    <FIRSTROW>1</FIRSTROW>\n" +
                        "    <LASTROW>20</LASTROW>\n" +
                        "  </SCHEDULE_REC_LIST_IN>\n" +
                        "</WEB_SCHEDULE_REC_LIST>";
        SCHEDULE_REC_REMOVE =
                "<WEB_SCHEDULE_REC_REMOVE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610095200</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_REC_REMOVE</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>d72a1a594aa8bff44a3c0753</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>1</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_REC_REMOVE_IN>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <SCHEDID>10007758</SCHEDID>\n" +
                        "  </SCHEDULE_REC_REMOVE_IN>\n" +
                        "</WEB_SCHEDULE_REC_REMOVE>";
        SHEDULE_TEST_OK_RESERVE =
                "<WEB_SCHEDULE_REC_RESERVE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200814142008</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_REC_RESERVE</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>2b4543e422e210dbd7635ddf</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>1</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_REC_RESERVE_IN>\n" +
                        "    <DCODE>10000002</DCODE>\n" +
                        "    <WORKDATE>20200815</WORKDATE>\n" +
                        "    <BHOUR>8</BHOUR>\n" +
                        "    <BMIN>30</BMIN>\n" +
                        "    <FHOUR>9</FHOUR>\n" +
                        "    <FMIN>0</FMIN>\n" +
                        "    <SCHEDIDENT>10006295</SCHEDIDENT>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <ONLINETYPE>1</ONLINETYPE>\n" +
                        "  </SCHEDULE_REC_RESERVE_IN>\n" +
                        "</WEB_SCHEDULE_REC_RESERVE>";

        SCHEDULE_REC_RESERVE =
                "<WEB_SCHEDULE_REC_RESERVE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610094948</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_REC_RESERVE</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>ac819b500b033c826c04c082</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>2</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_REC_RESERVE_IN>\n" +
                        "    <DCODE>20000002</DCODE>\n" +
                        "    <WORKDATE>20200818</WORKDATE>\n" +
                        "    <BHOUR>11</BHOUR>\n" +
                        "    <BMIN>0</BMIN>\n" +
                        "    <FHOUR>11</FHOUR>\n" +
                        "    <FMIN>30</FMIN>\n" +
                        "    <SCHEDIDENT>10006295</SCHEDIDENT>\n" +
                        "    <PCODE>990000066</PCODE>\n" +
                        "    <ONLINETYPE>1</ONLINETYPE>\n" +
                        "  </SCHEDULE_REC_RESERVE_IN>\n" +
                        "</WEB_SCHEDULE_REC_RESERVE>";
        SCHEDULE_REC_UPDATE =
                "<WEB_SCHEDULE_REC_UPDATE xmlns=\"http://sdsys.ru/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://sdsys.ru/\">\n" +
                        "  <MSH>\n" +
                        "    <MSH.7>\n" +
                        "      <TS.1>20200610095048</TS.1>\n" +
                        "    </MSH.7>\n" +
                        "    <MSH.9>\n" +
                        "      <MSG.1>WEB</MSG.1>\n" +
                        "      <MSG.2>SCHEDULE_REC_UPDATE</MSG.2>\n" +
                        "    </MSH.9>\n" +
                        "    <MSH.10>c73d984134f15c62da2554e2</MSH.10>\n" +
                        "    <MSH.18>UTF-8</MSH.18>\n" +
                        "    <MSH.99>2</MSH.99>\n" +
                        "  </MSH>\n" +
                        "  <SCHEDULE_REC_UPDATE_IN>\n" +
                        "    <SCHEDID>20001459</SCHEDID>\n" +
                        "    <CLVISIT>1</CLVISIT>\n" +
                        "  </SCHEDULE_REC_UPDATE_IN>\n" +
                        "</WEB_SCHEDULE_REC_UPDATE>";
    }
}
