package models.XML;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LabPortalXml{

    public String REF_SERVICES;
    public String REF_SERVICE_CATALOGS;
    public String REF_CONTRACTORS;
    public String REF_BIOMATERIALS;
    public String REF_CONTAINERS;
    public String REF_SAMPLINGS;
    public String REF_STICKERS;
    public String REF_COUNTERS;
    public String REF_CONTRACTOR_SERVICES;
    public String REF_CONTRACTOR_SERVICE_LINKS;
    public String REF_SAMPLING_LINKS;
    public String REF_COUNTER_LINKS;
    public String ORDER_ADD;
    public String RESULT_SEARCH;
    public String RESULT_ACCEPT;
    public String NUMRANGE_SELECT;
    public String NUMRANGE_ACCEPT;
    public String REF_PARAMETERS;

    static String date = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
    static String uid = String.valueOf(java.util.UUID.randomUUID());

    public LabPortalXml(){
        REF_SERVICES = "<LAB_REF_SERVICES xmlns=\"http://sdsys.ru/\">" +
                "  <MSH>" +
                "    <MSH.7>" +
                "      <TS.1>"+ date + "</TS.1>" +
                "    </MSH.7>" +
                "    <MSH.9>" +
                "      <MSG.1>LAB</MSG.1>" +
                "      <MSG.2>REF_SERVICES</MSG.2>" +
                "    </MSH.9>" +
                "    <MSH.10>" + uid + "</MSH.10>" +
                "    <MSH.18>UTF-8</MSH.18>" +
                "  </MSH>" +
                "  <REF_SERVICES_IN>" +
                "    <SEARCH_DISABLED>-1</SEARCH_DISABLED>" +
                "    <PART_SIZE>-1</PART_SIZE>" +
                "  </REF_SERVICES_IN>" +
                "</LAB_REF_SERVICES>";

        REF_SERVICE_CATALOGS = "<LAB_REF_SERVICE_CATALOGS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>"+ date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_SERVICE_CATALOGS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>"+ uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_SERVICE_CATALOGS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_SERVICE_CATALOGS_IN>\n" +
                "</LAB_REF_SERVICE_CATALOGS>";

        REF_CONTRACTORS = "<LAB_REF_CONTRACTORS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_CONTRACTORS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_CONTRACTORS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_CONTRACTORS_IN>\n" +
                "</LAB_REF_CONTRACTORS>";

        NUMRANGE_ACCEPT = "<LAB_NUMRANGE_ACCEPT xmlns=\"http://sdsys.ru/\">\n" +
                "    <MSH>\n" +
                "        <MSH.7>\n" +
                "            <TS.1>"+ date +"</TS.1>\n" +
                "        </MSH.7>\n" +
                "        <MSH.9>\n" +
                "            <MSG.1>LAB</MSG.1>\n" +
                "            <MSG.2>NUMRANGE_ACCEPT</MSG.2>\n" +
                "        </MSH.9>\n" +
                "        <MSH.10>" + uid +"</MSH.10>\n" +
                "        <MSH.18>UTF-8</MSH.18>\n" +
                "    </MSH>\n" +
                "    <NUMRANGE_ACCEPT_IN>\n" +
                "       <RANGEID>990010000000006</RANGEID>\n" +
                "    </NUMRANGE_ACCEPT_IN>\n" +
                "</LAB_NUMRANGE_ACCEPT>";

        NUMRANGE_SELECT = "<LAB_NUMRANGE_SELECT xmlns=\"http://sdsys.ru/\">\n" +
                "    <MSH>\n" +
                "        <MSH.7>\n" +
                "            <TS.1>" + date + "</TS.1>\n" +
                "        </MSH.7>\n" +
                "        <MSH.9>\n" +
                "            <MSG.1>LAB</MSG.1>\n" +
                "            <MSG.2>NUMRANGE_SELECT</MSG.2>\n" +
                "        </MSH.9>\n" +
                "        <MSH.10>" + uid +"</MSH.10>\n" +
                "        <MSH.18>UTF-8</MSH.18>\n" +
                "    </MSH>\n" +
                "    <NUMRANGE_SELECT_IN>\n" +
                "       <CONTRACTORID>990010000000006</CONTRACTORID>\n" +
                "    </NUMRANGE_SELECT_IN>\n" +
                "</LAB_NUMRANGE_SELECT>";

        ORDER_ADD = "<LAB_ORDER_ADD xmlns=\"http://sdsys.ru/\">\n" +
                "    <MSH>\n" +
                "        <MSH.7>\n" +
                "            <TS.1>"+ date +"</TS.1>\n" +
                "        </MSH.7>\n" +
                "        <MSH.9>\n" +
                "            <MSG.1>LAB</MSG.1>\n" +
                "            <MSG.2>ORDER_ADD</MSG.2>\n" +
                "        </MSH.9>\n" +
                "        <MSH.10>" + uid +"</MSH.10>\n" +
                "        <MSH.18>UTF-8</MSH.18>\n" +
                "    </MSH>\n" +
                "    <ORDER_ADD_IN>\n" +
                "    <ORDER_INFO>\n" +
                "      <EXTORDERCODE>" + uid.substring(0,23) +"</EXTORDERCODE>\n" +
                "    </ORDER_INFO>\n" +
                "    <CONTRACTOR_INFO>\n" +
                "      <CONTRACTORID>990010000000001</CONTRACTORID>\n" +
                "    </CONTRACTOR_INFO>\n" +
                "    <PATIENT_INFO>\n" +
                "      <LASTNAME>Last</LASTNAME>\n" +
                "      <FIRSTNAME>First</FIRSTNAME>\n" +
                "      <MIDNAME>Mid</MIDNAME>\n" +
                "      <GENDER>1</GENDER>\n" +
                "      <BIRTHDATE>19990101</BIRTHDATE>\n" +
                "    </PATIENT_INFO>\n" +
                "    <SERVICES>\n" +
                "      <SERVICE_ITEM>\n" +
                "        <SVCID>990010000000005</SVCID>\n" +
                "        <BIOID>990010000000122</BIOID>\n" +
                "      </SERVICE_ITEM>\n" +
                "      <SERVICE_ITEM>\n" +
                "        <SVCID>990010000000005</SVCID>\n" +
                "        <BIOID>990010000000122</BIOID>\n" +
                "      </SERVICE_ITEM>\n" +
                "      <SERVICE_ITEM>\n" +
                "        <SVCID>990010000000004</SVCID>\n" +
                "        <BIOID>990010000000123</BIOID>\n" +
                "      </SERVICE_ITEM>\n" +
                "    </SERVICES>\n" +
                "    <CONTAINERS>\n" +
                "      <CONTAINER_ITEM>\n" +
                "        <LABNUMBER>1</LABNUMBER>\n" +
                "        <BIOID>990010000000122</BIOID>\n" +
                "        <CONTID>990010000000192</CONTID>\n" +
                "        <SAMPLID>990010000000194</SAMPLID>\n" +
                "        <SERVICELINKS>\n" +
                "          <SERVICELINK_ITEM>\n" +
                "            <SVCID>990010000000005</SVCID>\n" +
                "            <CONTRACTORSVCID>990010000001368</CONTRACTORSVCID>\n" +
                "          </SERVICELINK_ITEM>\n" +
                "          <SERVICELINK_ITEM>\n" +
                "            <SVCID>990010000000005</SVCID>\n" +
                "            <CONTRACTORSVCID>990010000001368</CONTRACTORSVCID>\n" +
                "          </SERVICELINK_ITEM>\n" +
                "          <SERVICELINK_ITEM>\n" +
                "            <SVCID>990010000000004</SVCID>\n" +
                "            <CONTRACTORSVCID>990010000001365</CONTRACTORSVCID>\n" +
                "          </SERVICELINK_ITEM>\n" +
                "        </SERVICELINKS>\n" +
                "      </CONTAINER_ITEM>\n" +
                "      <CONTAINER_ITEM>\n" +
                "        <LABNUMBER>10</LABNUMBER>\n" +
                "        <BIOID>990010000000122</BIOID>\n" +
                "        <CONTID>990010000000238</CONTID>\n" +
                "        <SAMPLID>990010000000312</SAMPLID>\n" +
                "        <SERVICELINKS>\n" +
                "          <SERVICELINK_ITEM>\n" +
                "            <SVCID>990010000000005</SVCID>\n" +
                "            <CONTRACTORSVCID>990010000001368</CONTRACTORSVCID>\n" +
                "          </SERVICELINK_ITEM>\n" +
                "        </SERVICELINKS>\n" +
                "      </CONTAINER_ITEM>\n" +
                "      <CONTAINER_ITEM>\n" +
                "        <LABNUMBER>11</LABNUMBER>\n" +
                "        <BIOID>990010000000123</BIOID>\n" +
                "        <CONTID>990010000000192</CONTID>\n" +
                "        <SAMPLID>990010000000194</SAMPLID>\n" +
                "        <SERVICELINKS>\n" +
                "          <SERVICELINK_ITEM>\n" +
                "            <SVCID>990010000000005</SVCID>\n" +
                "            <CONTRACTORSVCID>990010000001368</CONTRACTORSVCID>\n" +
                "          </SERVICELINK_ITEM>\n" +
                "        </SERVICELINKS>\n" +
                "      </CONTAINER_ITEM>\n" +
                "    </CONTAINERS>\n" +
                "    </ORDER_ADD_IN>\n" +
                "</LAB_ORDER_ADD>";

        REF_BIOMATERIALS = "<LAB_REF_BIOMATERIALS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>"+ date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_BIOMATERIALS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>"+uid+"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_BIOMATERIALS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_BIOMATERIALS_IN>\n" +
                "</LAB_REF_BIOMATERIALS>";

        REF_CONTAINERS = "<LAB_REF_CONTAINERS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date + "</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_CONTAINERS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_CONTAINERS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_CONTAINERS_IN>\n" +
                "</LAB_REF_CONTAINERS>";

        REF_CONTRACTOR_SERVICE_LINKS = "<LAB_REF_CONTRACTOR_SERVICE_LINKS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_CONTRACTOR_SERVICE_LINKS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid + "</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_CONTRACTOR_SERVICE_LINKS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_CONTRACTOR_SERVICE_LINKS_IN>\n" +
                "</LAB_REF_CONTRACTOR_SERVICE_LINKS>";

        REF_CONTRACTOR_SERVICES = "<LAB_REF_CONTRACTOR_SERVICES xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_CONTRACTOR_SERVICES</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_CONTRACTOR_SERVICES_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_CONTRACTOR_SERVICES_IN>\n" +
                "</LAB_REF_CONTRACTOR_SERVICES>";

        REF_COUNTER_LINKS = "<LAB_REF_COUNTER_LINKS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_COUNTER_LINKS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid + "</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_COUNTER_LINKS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_COUNTER_LINKS_IN>\n" +
                "</LAB_REF_COUNTER_LINKS>";

        REF_COUNTERS = "<LAB_REF_COUNTERS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_COUNTERS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_COUNTERS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_COUNTERS_IN>\n" +
                "</LAB_REF_COUNTERS>";

        REF_PARAMETERS = "<LAB_REF_PARAMETERS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_PARAMETERS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_PARAMETERS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_PARAMETERS_IN>\n" +
                "</LAB_REF_PARAMETERS>";

        REF_SAMPLING_LINKS = "<LAB_REF_SAMPLING_LINKS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_SAMPLING_LINKS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_SAMPLING_LINKS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_SAMPLING_LINKS_IN>\n" +
                "</LAB_REF_SAMPLING_LINKS>";

        REF_SAMPLINGS = "<LAB_REF_SAMPLINGS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_SAMPLINGS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>" + uid + "</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_SAMPLINGS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_SAMPLINGS_IN>\n" +
                "</LAB_REF_SAMPLINGS>";

        REF_STICKERS = "<LAB_REF_STICKERS xmlns=\"http://sdsys.ru/\">\n" +
                "  <MSH>\n" +
                "    <MSH.7>\n" +
                "      <TS.1>" + date +"</TS.1>\n" +
                "    </MSH.7>\n" +
                "    <MSH.9>\n" +
                "      <MSG.1>LAB</MSG.1>\n" +
                "      <MSG.2>REF_STICKERS</MSG.2>\n" +
                "    </MSH.9>\n" +
                "    <MSH.10>"+ uid +"</MSH.10>\n" +
                "    <MSH.18>UTF-8</MSH.18>\n" +
                "  </MSH>\n" +
                "  <REF_STICKERS_IN>\n" +
                "    <PART_SIZE>-1</PART_SIZE>\n" +
                "  </REF_STICKERS_IN>\n" +
                "</LAB_REF_STICKERS>";

        RESULT_ACCEPT = "<LAB_RESULT_ACCEPT xmlns=\"http://sdsys.ru/\">\n" +
                "    <MSH>\n" +
                "        <MSH.7>\n" +
                "            <TS.1>" + date +"</TS.1>\n" +
                "        </MSH.7>\n" +
                "        <MSH.9>\n" +
                "            <MSG.1>LAB</MSG.1>\n" +
                "            <MSG.2>RESULT_ACCEPT</MSG.2>\n" +
                "        </MSH.9>\n" +
                "        <MSH.10>" + uid +"</MSH.10>\n" +
                "        <MSH.18>UTF-8</MSH.18>\n" +
                "    </MSH>\n" +
                "    <RESULT_ACCEPT_IN>\n" +
                "       <RESULT_ACCEPT_ITEM>\n" +
                "          <RESULTID>990010000001941</RESULTID>\n" +
                "       </RESULT_ACCEPT_ITEM>\n" +
                "    </RESULT_ACCEPT_IN>\n" +
                "</LAB_RESULT_ACCEPT>";

        RESULT_SEARCH = "<LAB_RESULT_SEARCH xmlns=\"http://sdsys.ru/\">\n" +
                "    <MSH>\n" +
                "        <MSH.7>\n" +
                "            <TS.1>" + date +"</TS.1>\n" +
                "        </MSH.7>\n" +
                "        <MSH.9>\n" +
                "            <MSG.1>LAB</MSG.1>\n" +
                "            <MSG.2>RESULT_SEARCH</MSG.2>\n" +
                "        </MSH.9>\n" +
                "        <MSH.10>" + uid + "</MSH.10>\n" +
                "        <MSH.18>UTF-8</MSH.18>\n" +
                "    </MSH>\n" +
                "    <RESULT_SEARCH_IN>\n" +
                "       <SEARCH_CONTRACTORID>990010000000001</SEARCH_CONTRACTORID>\n" +
                "       <PART_SIZE>50</PART_SIZE>\n" +
                "    </RESULT_SEARCH_IN>\n" +
                "</LAB_RESULT_SEARCH>";
    }
}
