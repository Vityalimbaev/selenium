package tests.api;

import io.restassured.response.Response;
import models.RestController;
import models.XML.JsonList;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MediaTest {
    private RestController restController;
    private String imgId;

    @BeforeClass(description = "Инициализация")
    public void initialize() throws Exception {
        restController = new RestController();
        restController.getMediaClientSecret();
        restController.generateMediaToken();
    }

    @Test(description = "Загрузка картинки")
    public void createImg(){
        Response response = restController.postJson("/api/media/create",new JsonList().sendImg);
        Assert.assertEquals(response.body().jsonPath().get("success").toString(), "true");
        imgId = response.body().jsonPath().get("message");
//        response.body().asString();
    }

    @Test(dependsOnMethods = "createImg", description = "Редактирование картинки")
    public void updateImg(){
        Response response = restController.postJson("/api/media/update",new JsonList().updateImg.replace("?", imgId));
        Assert.assertEquals(response.body().jsonPath().get("success").toString(), "true");
        response.body().asString();
    }
}
