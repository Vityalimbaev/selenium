package models;

import factories.DataProviderFactory;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.dataproviders.DataProviderAPI;
import models.dataproviders.DataProviderAdmin;
import models.enums.NameProject;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RestController {
    private String clientSecret;

    private String accessToken;
    private String clientid;//pcode?
    private DataProviderAPI dataProviderAPI;

    public RestController() {
        dataProviderAPI = (DataProviderAPI) DataProviderFactory.get(System.getProperty("mode"), NameProject.ADMINAPI);
    }

    public RestController(NameProject project) {
        dataProviderAPI = (DataProviderAPI) DataProviderFactory.get(System.getProperty("mode"), project);
    }

    public void getClientSecret() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String hashType = "0";
        SimpleDateFormat AuthDateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String requestTime = AuthDateTimeFormat.format(new Date());

        // source data for hash
        String hashData = requestTime + "|" + dataProviderAPI.getClient_id() + "|" + dataProviderAPI.getSecretKey();
        MessageDigest sha1Digest = MessageDigest.getInstance("SHA1");

        // calculate sha1 hash
        byte[] sha1Bytes = sha1Digest.digest(hashData.getBytes("UTF-8"));
        StringBuffer sha1Hex = new StringBuffer();

        // convert hash to hex
        for (int i = 0; i < sha1Bytes.length; i++) {
            sha1Hex.append(Integer.toString((sha1Bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // final client secret
        this.clientSecret = hashType + "-" + requestTime + "-" + sha1Hex;
    }

    public void getMediaClientSecret() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String hashType = "1";
        SimpleDateFormat AuthDateTimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String requestTime = AuthDateTimeFormat.format(new Date());
        String scope = "media";

        // source data for hash
        String hashData = requestTime + "|" + dataProviderAPI.getClient_id() + "|" + dataProviderAPI.getHostSecretKey() + "|" + scope;
        MessageDigest sha1Digest = MessageDigest.getInstance("SHA1");

        // calculate sha1 hash
        byte[] sha1Bytes = sha1Digest.digest(hashData.getBytes("UTF-8"));
        StringBuffer sha1Hex = new StringBuffer();

        // convert hash to hex
        for (int i = 0; i < sha1Bytes.length; i++) {
            sha1Hex.append(Integer.toString((sha1Bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        // final client secret
        this.clientSecret = hashType + "-" + requestTime + "-" + sha1Hex;
    }

    public void generateToken(){
        Response response = RestAssured
                .given()
                .auth().preemptive().basic(dataProviderAPI.getClient_id(), this.clientSecret)
                .contentType(ContentType.URLENC.withCharset(Charset.defaultCharset()))
                .formParam("grant_type", dataProviderAPI.getGrant_type())
                .formParam("username", dataProviderAPI.getUser_login())
                .formParam("password", dataProviderAPI.getUser_password())
                .formParam("scope", dataProviderAPI.getScope())
                .formParam("client_id", dataProviderAPI.getClient_id())
                .formParam("client_secret", clientSecret)
                .when()
                .post(dataProviderAPI.getUrl()+"/oauth2/token");

        response.getBody().asString();
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        this.accessToken = jsonObject.get("access_token").toString();
        this.clientid = jsonObject.get("client_id").toString();
    }

    public void generateMediaToken(){
        Response response = RestAssured
                .given()
                .auth().preemptive().basic(dataProviderAPI.getClient_id(), this.clientSecret)
                .contentType(ContentType.URLENC.withCharset(Charset.defaultCharset()))
                .formParam("grant_type", "client_credentials")
//                .formParam("username", dataProviderAPI.getUser_login())
//                .formParam("password", dataProviderAPI.getUser_password())
                .formParam("scope", "media")
                .formParam("client_id", dataProviderAPI.getClient_id())
                .formParam("client_secret", clientSecret)
                .when()
                .post(dataProviderAPI.getUrl()+"/oauth2/token");

        response.getBody().asString();
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        this.accessToken = jsonObject.get("access_token").toString();
        this.clientid = jsonObject.get("client_id").toString();
    }

    public void generateTokenBySecretKey(){
        Response response = RestAssured
                .given()
                .auth().preemptive().basic(dataProviderAPI.getClient_id(), this.clientSecret)
                .contentType(ContentType.URLENC.withCharset(Charset.defaultCharset()))
                .formParam("grant_type", "client_credentials")
//                .formParam("username", dataProviderAPI.getUser_login())
//                .formParam("password", dataProviderAPI.getUser_password())
                .formParam("scope", dataProviderAPI.getScope())
                .formParam("client_id", dataProviderAPI.getClient_id())
                .formParam("client_secret", dataProviderAPI.getSecretKey())
                .when()
                .post(dataProviderAPI.getUrl()+"/oauth2/token");

        response.getBody().asString();
        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        this.accessToken = jsonObject.get("access_token").toString();
    }


    public Response get(String url){
        return RestAssured
                .given()
                .auth()
                .oauth2(accessToken)
                .when()
                .get(dataProviderAPI.getUrl() + url);
    }

    public Response get(String url, String header){
        return RestAssured
                .given()
                .header(header.split(":")[0], header.split(":")[1])
                .when()
                .get(dataProviderAPI.getUrl() + url);
    }

    public Response postXml(String url, String xml){
        return RestAssured
                .given()
                .auth()
                .oauth2(accessToken)
                .contentType(ContentType.XML.withCharset(Charset.defaultCharset()))
                .body(xml)
                .when()
                .post(dataProviderAPI.getUrl() + url);
    }

    public Response postJson(String url, String json){
        return RestAssured
                .given()
                .auth()
                .oauth2(accessToken)
                .contentType(ContentType.JSON.withCharset(Charset.defaultCharset()))
                .body(json)
                .when()
                .post(dataProviderAPI.getUrl() + url);
    }

    public Response postWithCert(String url, String json) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        KeyStore keyStore = null;
        SSLConfig config = null;

        String password = "changeme";
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(
                    new FileInputStream("/home/tester/WorkProjects/SeleniumUI/src/main/resources/keystore.p12"),
                    password.toCharArray());
        } catch (Exception ex) {
            System.out.println("Error while loading keystore >>>>>>>>>");
            ex.printStackTrace();
        }

        if (keyStore != null) {
            org.apache.http.conn.ssl.SSLSocketFactory clientAuthFactory = null;
            clientAuthFactory = new org.apache.http.conn.ssl.SSLSocketFactory(keyStore, password);
            // set the config in rest assured
            config = new SSLConfig().with().sslSocketFactory(clientAuthFactory).and().allowAllHostnames();
            RestAssured.config = RestAssured.config().sslConfig(config);
//            RestAssured.given().when().get("/path").then();
        }

        return RestAssured
                .given().config(RestAssured.config().sslConfig(config))
                .contentType(ContentType.JSON.withCharset(Charset.defaultCharset()))
                .headers("Authorization", "Bearer " + accessToken/*, "X-Forwarded-Host", "dev-demo.infoclinica.ru"*/)
                .body(json)
                .when()
                .post(dataProviderAPI.getUrl() + url);
    }

    public Response post2(String url, String xml){
        return RestAssured
                .given()
                .contentType(ContentType.JSON.withCharset(Charset.defaultCharset()))
                .header("X-Authorization","Certificate e5cafb98-7f86-4571-b3ce-b87144f8ccef-12")
                .body(xml)
                .when()
                .post(url);
    }

    public Response post(String url){
        return RestAssured
                .given()
                .auth()
                .oauth2(accessToken)
                .contentType(ContentType.URLENC.withCharset(Charset.defaultCharset()))
                .when()
                .post(dataProviderAPI.getUrl() + url);
    }

    public String getClientid() {
        return clientid;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
