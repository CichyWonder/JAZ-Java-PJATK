package pl.edu.pjwstk.jaz.allezon;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.allezon.request.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AllezonUserTest {

    private static Response adminResponse;
    private static Response userResponse;
    private static Response userResponseForEdit;


    @BeforeClass
    public static void beforeClass() throws Exception {

        JSONObject parameters = new JSONObject();
        parameters.put("username", "admin");
        parameters.put("password", "admin123");
        given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/registerjpa")
                .thenReturn();

        parameters.put("username", "admin");
        parameters.put("password", "admin123");
        adminResponse = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        parameters.put("username", "user");
        parameters.put("password", "user123");
        given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/registerjpa")
                .thenReturn();

        parameters.put("username", "user");
        parameters.put("password", "user123");
        userResponse = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        parameters.put("username", "userForEdit");
        parameters.put("password", "user124");
        given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/registerjpa")
                .thenReturn();

        parameters.put("username", "userForEdit");
        parameters.put("password", "user124");
        userResponseForEdit = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();


        List<PhotoRequest> photoList = new ArrayList<>();
        photoList.add(new PhotoRequest("link1",1));
        photoList.add(new PhotoRequest("link2",2));
        photoList.add(new PhotoRequest("link3",3));

        List<ParameterRequest> parameterList = new ArrayList<>();
        parameterList.add(new ParameterRequest("Engine","2.2 Diesel"));
        parameterList.add(new ParameterRequest("Color","White"));
        parameterList.add(new ParameterRequest("Transmisson","Automatic"));


        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Motorization"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("Cars","Motorization"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(2453,
                        "Used Mercedes w212",
                        "I'm the first owner of the car",
                        "Cars",
                        photoList,
                        parameterList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction");


    }


    @Test
    public void editAuctionTitleShouldResponseStatus200(){


        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(45000,
                        "Used Mercedes w212 2009",
                        null,
                        null,
                        null,
                        2L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/6")
                .then()
                .statusCode(200);


    }

    @Test
    public void editAuctionPriceShouldResponseStatus200() {


        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(48000,
                        "Used Mercedes w212 2009",
                        null,
                        null,
                        null,
                        1L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/6")
                .then()
                .statusCode(200);


    }

    @Test
    public void editAuctionByAnotherUserShouldResponseStatus401(){

        given()
                .cookies(userResponseForEdit.getCookies())
                .body(new AuctionRequest(0,
                        "Used Mercedes w212 2009",
                        null,
                        null,
                        null,
                        3L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/6")
                .then()
                .statusCode(401);


    }

    @Test
    public void checkingVersionShouldResponse400() {

        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Used Mercedes w212 2009",
                        null,
                        null,
                        null,
                        1L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/6")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(0,
                        "Used Mercedes w212 2009",
                        null,
                        null,
                        null,
                        1L,
                        null,
                        null
                ))
                .contentType(ContentType.JSON)
                .put("/api/editAuction/6")
                .then()
                .statusCode(400);

    }

    @Test
    public void shouldResponse500AfterTryingToEditNotExistingAuction(){
        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest())
                .contentType(ContentType.JSON)
                .put("/api/editAuction/99")
                .then()
                .statusCode(500);
    }

    @Test
    public void viewAuctionShouldResponse200() {


        given()
                .cookies(userResponse.getCookies())
                .contentType(ContentType.JSON)
                .when()
                .get("/api/auctions/6")
                .then()
                .statusCode(200);

    }

    @Test
    public void viewAuctionListShouldResponse200() {


        given()
                .cookies(userResponse.getCookies())
                .contentType(ContentType.JSON)
                .when()
                .get("/api/auctions")
                .then()
                .statusCode(200);

    }

    @Test
    public void ShowListOfAuctionOnConsole() throws InterruptedException {
        List<PhotoRequest> photoList = new ArrayList<>();
        photoList.add(new PhotoRequest("link1",1));
        photoList.add(new PhotoRequest("link2",2));
        photoList.add(new PhotoRequest("link3",3));

        List<ParameterRequest> parameterList = new ArrayList<>();
        parameterList.add(new ParameterRequest("Engine","2.2 Diesel"));
        parameterList.add(new ParameterRequest("Color","White"));
        parameterList.add(new ParameterRequest("Transmisson","Automatic"));

        given()
                .cookies(userResponse.getCookies())
                .body(new AuctionRequest(2453,
                        "Used Mercedes w204",
                        "I'm the first owner of the car",
                        "Cars",
                        photoList,
                        parameterList
                ))
                .contentType(ContentType.JSON)
                .post("/api/addAuction");

        given()
                .cookies(userResponse.getCookies())
                .contentType(ContentType.JSON)
                .when()
                .get("/api/auctions")
                .print();
                Thread.sleep(10000);


    }

    @Test
    public void ShowAuctionOnConsole() throws InterruptedException {


        given()
                .cookies(userResponse.getCookies())
                .contentType(ContentType.JSON)
                .when()
                .get("/api/auctions/6")
                .print();
        Thread.sleep(10000);


    }


}
