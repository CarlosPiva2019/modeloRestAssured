package testcases.healthcheck;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import org.apache.http.HttpStatus;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static constants.Constants.BASE_URI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static requestspecification.RequestSpecificationFactory.requestSpecification;
import static requestspecification.RequestSpecificationFactory.responseSpecification;

@Listeners(ExtentITestListenerClassAdapter.class)
public class HealthCheckTest {

    @Test(groups = "HealthCheck")
    public void shouldReturn200_HealthCheck() {
        given()
                .when()
                .spec(requestSpecification(BASE_URI))
                .get("/test")
                .then()
                .spec(responseSpecification())
                .statusCode(HttpStatus.SC_OK)
                .body("status", is("ok"),
                        "method", is("GET"));
    }
}