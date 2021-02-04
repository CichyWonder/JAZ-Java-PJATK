package pl.edu.pjwstk.jaz.authorizationjpa;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@IntegrationTest
public class UserLoginAndRegistrationJpaTests {


    @Test
    public void adminLogin() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "cos");
        parameters.put("password", "admin156");
        var reply = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        given()
                .cookies(reply.getCookies())
                .get("/api/map")
                .then()
                .statusCode(200);
    }

    @Test
    public void messageForAdmin() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "lol");
        parameters.put("password", "admin156");
        var reply = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        given()
                .cookies(reply.getCookies())
                .get("/api/helloAdmin")
                .then()
                .statusCode(200)
                .body(equalTo("Hello Admin"));

    }

    @Test
    public void messageForUserLoggedByAdmin() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "admin");
        parameters.put("password", "admin123");
        var reply = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        given()
                .cookies(reply.getCookies())
                .get("/api/helloUser")
                .then()
                .statusCode(403);
    }


    @Test
    public void userLogin() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "user");
        parameters.put("password", "user123");
        var reply = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        given()
                .cookies(reply.getCookies())
                .get("/api/map")
                .then()
                .statusCode(200);
    }

    @Test
    public void messageForAdminLoggedByUser() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "user");
        parameters.put("password", "user123");
        var reply = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        given()
                .cookies(reply.getCookies())
                .get("/api/helloAdmin")
                .then()
                .statusCode(403);
    }

    @Test
    public void messageForUser() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "user");
        parameters.put("password", "user123");
        var reply = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        given()
                .cookies(reply.getCookies())
                .get("/api/helloUser")
                .then()
                .statusCode(200)
                .body(equalTo("Hello User"));
    }

    @Test
    public void userAlreadyExists() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "user");
        parameters.put("password", "user123");
        given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/registerjpa")
                .then()
                .statusCode(226);
    }

    @Test
    public void userTryLogInWithoutPassword() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "user");
        parameters.put("password", "");
        given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .then()
                .statusCode(401);
    }

    @Test
    public void userLoginWithBadPassword() throws JSONException {
        JSONObject parameters = new JSONObject();
        parameters.put("username", "user");
        parameters.put("password", "user124");
        var reply = given()
                .contentType("application/json")
                .body(parameters.toString())
                .when()
                .post("/api/loginjpa")
                .thenReturn();

        given()
                .cookies(reply.getCookies())
                .get("/api/map")
                .then()
                .statusCode(403);
    }
}
