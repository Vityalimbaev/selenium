package tests.api;

import io.restassured.response.Response;
import models.RestController;
import models.XML.*;
import models.enums.NameProject;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.containsString;

public class LabportalApi {

    private RestController restController;
    private LabPortalXml xml;
    private boolean isDev;

    @Parameters(value = {"browser", "version", "platform"})
    @BeforeClass(description = "Инициализация")
    public void initialize() throws Exception {
        isDev = System.getProperty("mode").equalsIgnoreCase("dev");
        xml = new LabPortalXml();
        restController = new RestController(NameProject.LABPORTALAPI);
        restController.getClientSecret();
        restController.generateTokenBySecretKey();
    }

    @Test(description = "REF_SERVICES")
    public void REF_SERVICES(){
        Response response = restController.postXml("/api/xml", xml.REF_SERVICES);
        response.then().body("ACK.REF_SERVICES_OUT.SERVICE_ITEM[0].SVCID", Matchers.is("10000001"));
        response.then().body("ACK.REF_SERVICES_OUT.SERVICE_ITEM[1].SVCCODE", Matchers.is("123"));
        response.then().body("ACK.REF_SERVICES_OUT.SERVICE_ITEM[2].CATALOGID", Matchers.is("52"));
        response.then().body("ACK.REF_SERVICES_OUT.SERVICE_ITEM[3].SORTORDER", Matchers.is("2"));
    }

    @Test(description = "REF_SERVICE_CATALOGS")
    public void REF_SERVICE_CATALOGS(){
        Response response = restController.postXml("/api/xml", xml.REF_SERVICE_CATALOGS);
        response.then().assertThat().body("ACK.REF_SERVICE_CATALOGS_OUT.SERVICE_CATALOG_ITEM[0].CATALOGID", containsString("52"))
                .assertThat().body("ACK.REF_SERVICE_CATALOGS_OUT.SERVICE_CATALOG_ITEM[0].CATALOGNAME", containsString("90-93 ЛАБОРАТОРНАЯ ДИАГНОСТИКА"))
                .assertThat().body("ACK.REF_SERVICE_CATALOGS_OUT.SERVICE_CATALOG_ITEM[2].CATALOGID", containsString("2"))
                .assertThat().body("ACK.REF_SERVICE_CATALOGS_OUT.SERVICE_CATALOG_ITEM[2].CATALOGNAME", containsString("11 ПРОФИЛАКТИЧЕСКАЯ И ЭСТЕТИЧЕСКАЯ СТОМАТОЛОГИЯ"))
                .assertThat().body("ACK.REF_SERVICE_CATALOGS_OUT.SERVICE_CATALOG_ITEM[3].CATALOGNAME", containsString("12 АНЕСТЕЗИЯ В СТОМАТОЛОГИИ"))
                .assertThat().body("ACK.REF_SERVICE_CATALOGS_OUT.SERVICE_CATALOG_ITEM[4].CATALOGNAME", containsString("13 ТЕРАПЕВТИЧЕСКАЯ СТОМАТОЛОГИЯ"));
    }

    @Test(description = "REF_CONTRACTORS")
    public void REF_CONTRACTORS() {
        Response response = restController.postXml("/api/xml", xml.REF_CONTRACTORS);
        response.then().body("ACK.REF_CONTRACTORS_OUT.CONTRACTOR_ITEM.CONTRACTORID", Matchers.is("990000062"));
        response.then().body("ACK.REF_CONTRACTORS_OUT.CONTRACTOR_ITEM.CONTRACTORNAME", Matchers.is("Внутренняя лаборатория"));
        response.then().body("ACK.REF_CONTRACTORS_OUT.CONTRACTOR_ITEM.LEGALNAME", Matchers.is("Внутренняя лаборатория"));
        response.then().body("ACK.REF_CONTRACTORS_OUT.CONTRACTOR_ITEM.SHORTNAME", Matchers.is("Лаборатория"));
    }

    @Test(description = "REF_BIOMATERIALS")
    public void REF_BIOMATERIALS() {
        Response response = restController.postXml("/api/xml", xml.REF_BIOMATERIALS);
        response.then().assertThat().body("ACK.REF_BIOMATERIALS_OUT.BIOMATERIAL_ITEM[0].BIOID", containsString("23"))
                .assertThat().body("ACK.REF_BIOMATERIALS_OUT.BIOMATERIAL_ITEM[1].BIONAME", containsString("Верхний клык справа I-3"))
                .assertThat().body("ACK.REF_BIOMATERIALS_OUT.BIOMATERIAL_ITEM[0].LOCTYPE", containsString("0"))
                .assertThat().body("ACK.REF_BIOMATERIALS_OUT.BIOMATERIAL_ITEM[0].PARENTID", containsString("0"))
                .assertThat().body("ACK.REF_BIOMATERIALS_OUT.BIOMATERIAL_ITEM[0].BIOTYPE", containsString("0"));
    }

    @Test(description = "REF_CONTAINERS")
    public void REF_CONTAINERS(){
        Response response = restController.postXml("/api/xml", xml.REF_CONTAINERS);
        response.then().assertThat().body("ACK.REF_CONTAINERS_OUT.CONTAINER_ITEM[0].CONTID", containsString("990000001"))
                .assertThat().body("ACK.REF_CONTAINERS_OUT.CONTAINER_ITEM[1].CONTNAME", containsString("красный"))
                .assertThat().body("ACK.REF_CONTAINERS_OUT.CONTAINER_ITEM[1].CONTCOLOR", containsString("0"));
    }

    @Test(description = "REF_SAMPLINGS")
    public void REF_SAMPLINGS(){
        Response response = restController.postXml("/api/xml", xml.REF_SAMPLINGS);
        response.then().body("ACK.REF_SAMPLINGS_OUT.SAMPLING_ITEM[0].SAMPLID", Matchers.is("10000001"));
        response.then().body("ACK.REF_SAMPLINGS_OUT.SAMPLING_ITEM[0].SAMPLNAME", Matchers.is("Кровь из вены"));
        response.then().body("ACK.REF_SAMPLINGS_OUT.SAMPLING_ITEM[0].CONTID", Matchers.is("990000001"));
        response.then().body("ACK.REF_SAMPLINGS_OUT.SAMPLING_ITEM[1].FILLMODE", Matchers.is("1"));
        response.then().body("ACK.REF_SAMPLINGS_OUT.SAMPLING_ITEM[1].VOLUMECONTROL", Matchers.is("0"));
        response.then().body("ACK.REF_SAMPLINGS_OUT.SAMPLING_ITEM[1].CONTRACTORID", Matchers.is("990000062"));
    }

    @Test(description = "REF_STICKERS")
    public void REF_STICKERS(){
        Response response = restController.postXml("/api/xml", xml.REF_STICKERS);
        response.then().assertThat().body("ACK.REF_STICKERS_OUT.STICKER_ITEM[0].STICKERID", containsString("500001"))
                .assertThat().body("ACK.REF_STICKERS_OUT.STICKER_ITEM[0].STICKERNAME", containsString("Шаблон по умолчанию"))
                .assertThat().body("ACK.REF_STICKERS_OUT.STICKER_ITEM[0].CONTRACTORID", containsString("0"))
                .assertThat().body("ACK.REF_STICKERS_OUT.STICKER_ITEM[0].LABELTYPE", containsString("0"))
                .assertThat().body("ACK.REF_STICKERS_OUT.STICKER_ITEM[0].LABELPRINTTYPE", containsString("0"));
    }

    @Test(description = "REF_COUNTERS")
    public void REF_COUNTERS() {
        Response response = restController.postXml("/api/xml", xml.REF_COUNTERS);
        response.then().body("ACK.REF_COUNTERS_OUT.COUNTER_ITEM.COUNTERID", Matchers.is("10000004"));
        response.then().body("ACK.REF_COUNTERS_OUT.COUNTER_ITEM.COUNTERNAME", Matchers.is("Предварительная"));
        response.then().body("ACK.REF_COUNTERS_OUT.COUNTER_ITEM.CONTRACTORID", Matchers.is("990000062"));
        response.then().body("ACK.REF_COUNTERS_OUT.COUNTER_ITEM.COUNTERTYPE", Matchers.is("2"));
        response.then().body("ACK.REF_COUNTERS_OUT.COUNTER_ITEM.COUNTERPROC", Matchers.is("LC_LIS_GEN"));
    }

    @Test(description = "REF_CONTRACTOR_SERVICES")
    public void REF_CONTRACTOR_SERVICES() {
        Response response = restController.postXml("/api/xml", xml.REF_CONTRACTOR_SERVICES);
        response.then().assertThat()
                .statusCode(200);
    }

    @Test(description = "REF_CONTRACTOR_SERVICE_LINKS")
    public void REF_CONTRACTOR_SERVICE_LINKS() {
        Response response = restController.postXml("/api/xml", xml.REF_CONTRACTOR_SERVICE_LINKS);
        response.then().body("ACK.REF_CONTRACTOR_SERVICE_LINKS_OUT.CONTRACTOR_SERVICE_LINK_ITEM.SVCLINKID", Matchers.is("10000003"));
        response.then().body("ACK.REF_CONTRACTOR_SERVICE_LINKS_OUT.CONTRACTOR_SERVICE_LINK_ITEM.CONTRACTORSVCID", Matchers.is("990000001"));
        response.then().body("ACK.REF_CONTRACTOR_SERVICE_LINKS_OUT.CONTRACTOR_SERVICE_LINK_ITEM.RECID", Matchers.is("990000002"));
        response.then().body("ACK.REF_CONTRACTOR_SERVICE_LINKS_OUT.CONTRACTOR_SERVICE_LINK_ITEM.RECTYPE", Matchers.is("203"));

    }

    @Test(description = "REF_SAMPLING_LINKS")
    public void REF_SAMPLING_LINKS() {
        Response response = restController.postXml("/api/xml", xml.REF_SAMPLING_LINKS);
        response.then().body("ACK.REF_SAMPLING_LINKS_OUT.SAMPLING_LINK_ITEM[0].SAMPLLINKID", Matchers.is("10000003"));
        response.then().body("ACK.REF_SAMPLING_LINKS_OUT.SAMPLING_LINK_ITEM[1].RECID", Matchers.is("990000001"));
        response.then().body("ACK.REF_SAMPLING_LINKS_OUT.SAMPLING_LINK_ITEM[2].RECTYPE", Matchers.is("35"));
        response.then().body("ACK.REF_SAMPLING_LINKS_OUT.SAMPLING_LINK_ITEM[3].SAMPLID", Matchers.is("10000002"));
    }

    @Test(description = "REF_COUNTER_LINKS")
    public void REF_COUNTER_LINKS() {
        Response response = restController.postXml("/api/xml", xml.REF_COUNTER_LINKS);
        response.then().body("ACK.REF_COUNTER_LINKS_OUT.COUNTER_LINK_ITEM.COUNTERLINKID", Matchers.is("10000002"));
        response.then().body("ACK.REF_COUNTER_LINKS_OUT.COUNTER_LINK_ITEM.RECID", Matchers.is("10000001"));
        response.then().body("ACK.REF_COUNTER_LINKS_OUT.COUNTER_LINK_ITEM.RECTYPE", Matchers.is("202"));
        response.then().body("ACK.REF_COUNTER_LINKS_OUT.COUNTER_LINK_ITEM.LINKTYPE", Matchers.is("1"));

    }

    @Test(description = "ORDER_ADD")
    public void ORDER_ADD() {
        Response response = restController.postXml("/api/xml", xml.ORDER_ADD);
//        response.then().body("ACK.ORDER_ADD_OUT.ORDERID", Matchers.is("10020252"));
        response.then().assertThat()
                .statusCode(200);
    }

    @Test(description = "RESULT_SEARCH")
    public void RESULT_SEARCH() {
        Response response = restController.postXml("/api/xml", xml.RESULT_SEARCH);
        response.then().assertThat()
                .statusCode(200);
    }

    @Test(description = "RESULT_ACCEPT")
    public void RESULT_ACCEPT() {
        Response response = restController.postXml("/api/xml", xml.RESULT_ACCEPT);
        response.then().assertThat()
                .statusCode(200);
    }

    @Test(description = "NUMRANGE_SELECT")
    public void NUMRANGE_SELECT() {
        Response response = restController.postXml("/api/xml", xml.NUMRANGE_SELECT);
        response.then().assertThat()
                .statusCode(200);
    }

    @Test(description = "NUMRANGE_ACCEPT")
    public void NUMRANGE_ACCEPT() {
        Response response = restController.postXml("/api/xml", xml.NUMRANGE_ACCEPT);
        response.then().assertThat()
                .statusCode(200);
    }

    @Test(description = "REF_PARAMETERS")
    public void REF_PARAMETERS() {
        Response response = restController.postXml("/api/xml", xml.REF_PARAMETERS);
        response.then().assertThat().body("ACK.REF_PARAMETERS_OUT.PARAMETER_ITEM[0].PARAMID", containsString("10000001"))
                .assertThat().body("ACK.REF_PARAMETERS_OUT.PARAMETER_ITEM[1].PARAMNAME", containsString("дата"))
                .assertThat().body("ACK.REF_PARAMETERS_OUT.PARAMETER_ITEM[1].PARAMFULLNAME", containsString("дата"))
                .assertThat().body("ACK.REF_PARAMETERS_OUT.PARAMETER_ITEM[2].PARAMTYPE", containsString("6"))
                .assertThat().body("ACK.REF_PARAMETERS_OUT.PARAMETER_ITEM[3].PARAMTYPEID", containsString("0"))
                .assertThat().body("ACK.REF_PARAMETERS_OUT.PARAMETER_ITEM[3].PARAMREFID", containsString("3"));
    }
}
