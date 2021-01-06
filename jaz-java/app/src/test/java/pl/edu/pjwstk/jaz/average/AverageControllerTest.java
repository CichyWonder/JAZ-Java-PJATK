package pl.edu.pjwstk.jaz.average;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.jaz.IntegrationTest;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SpringRunner.class)
@IntegrationTest
public class AverageControllerTest {

    @Test
    public void should_calculate_simple_average1() {
        var response = given()
                .param("numbers", 25,25)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 25"));
    }
    @Test
    public void should_calculate_simple_average2() {
        var response = given()
                .param("numbers", 4,3,1,7,5)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 4"));
    }
    @Test
    public void should_calculate_simple_average3() {
        var response = given()
                .param("numbers", 2,1)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 1.5"));
    }
    @Test
    public void should_calculate_simple_average4() {
        var response = given()
                .param("numbers", 2,1,1)
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Average equals to: 1.33"));
    }

    @Test
    public void test_without_paramteres() {
        var response = given()
                .param("numbers")
                .when()
                .get("/api/average")
                .then()
                .statusCode(200)
                .body(equalTo("Please put the parameters"));
    }


}