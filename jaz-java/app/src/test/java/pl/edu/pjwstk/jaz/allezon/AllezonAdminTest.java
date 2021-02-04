package pl.edu.pjwstk.jaz.allezon;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;
import pl.edu.pjwstk.jaz.allezon.request.CategoryRequest;
import pl.edu.pjwstk.jaz.allezon.request.SectionRequest;


import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@IntegrationTest
public class AllezonAdminTest {
    private static   Response adminResponse;
    private static  Response userResponse;

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


        parameters.put("username", "admin");
        parameters.put("password", "admin123");
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

    }


    @Test
    public void createSectionShouldResponseStatus200() {


        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Motorization"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .then()
                .statusCode(200);


    }
    @Test
    public void createCategoryShouldResponseStatus200(){
        
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Clothes"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("T-shirts","Clothes"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .then()
                .statusCode(200);

    }
    @Test
    public void tryToCreateSectionWithAllReadyExistNameShouldResponseStatus500(){

        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("RTV"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("RTV"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .then()
                .statusCode(500);

    }
    @Test
    public void tryToCreateCategoryWithAllReadyExistNameShouldResponseStatus500(){

        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Garden"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("Lawn mowers","Garden"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())
                .body(new CategoryRequest("Lawn mowers","Garden"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .then()
                .statusCode(500);

    }

    @Test
    public void updateSectionNameShouldResponseStatus200(){

        given()
                .cookies(adminResponse.getCookies())

                .body(new SectionRequest("AGD"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()

                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("Health"))
                .contentType(ContentType.JSON)
                .put("/api/updateSection/AGD")
                .then()
                .statusCode(200);


    }
    @Test
    public void updateCategoryNameShouldResponseStatus200(){

        given()
                .cookies(adminResponse.getCookies())
                .body(new SectionRequest("AGD"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())

                .body(new CategoryRequest("Washing machines","AGD"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .thenReturn();
        given()
                .cookies(adminResponse.getCookies())

                .body(new CategoryRequest("ovens","AGD"))
                .contentType(ContentType.JSON)
                .put("/api/updateCategory/Washing machines")
                .then()
                .statusCode(200);


    }

    @Test
    public void createSectionByBasicUserShouldResponseStatus403() {

        given()
                .cookies(userResponse.getCookies())
                .body(new SectionRequest("Garden"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .then()
                .statusCode(403);


    }

    @Test
    public void createCategoryByBasicUserShouldResponseStatus403() {

        given()
                .cookies(userResponse.getCookies())
                .body(new SectionRequest("Home"))
                .contentType(ContentType.JSON)
                .post("/api/addSection")
                .thenReturn();
        given()
                .cookies(userResponse.getCookies())
                .body(new CategoryRequest("Furniture","Home"))
                .contentType(ContentType.JSON)
                .post("/api/addCategory")
                .then()
                .statusCode(403);

    }



}
