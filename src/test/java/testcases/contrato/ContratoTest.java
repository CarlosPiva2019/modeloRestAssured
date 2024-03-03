package testcases.contrato;

import basetest.BaseTest;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import datafactory.Datafactory;
import dto.TokenOutDTO;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

@Listeners(ExtentITestListenerClassAdapter.class)
public class ContratoTest {
    private BaseTest baseTest = new BaseTest();
    private Datafactory datafactory = new Datafactory();
    String username;
    String password;
    TokenOutDTO tokenOut;
    Integer id;

    @BeforeClass(groups = "Contrato")
    public void setEnvironment() {

        //guarda os valores dos campos username e password
        Map<String, Object> userValues = baseTest.getUsers()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchema(new File("src/test/resources/json_schema/users.json")))
                .extract().jsonPath().get("users[0]");

        username = (String) userValues.get("username");
        password = (String) userValues.get("password");

        //guarda o valor do campo token
        tokenOut = baseTest.postToken(datafactory.createToken(username, password))
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchema(new File("src/test/resources/json_schema/token_create.json")))
                .extract().as(TokenOutDTO.class);

        //guarda o valor do campo id
        id = baseTest.getProducts_All()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchema(new File("src/test/resources/json_schema/product_get_all.json")))
                .extract().jsonPath().get("products.id[3]");
    }

    @Test(groups = "Contrato")
    public void shouldReturn200_ValidateContract_GetProducts_Id() {
        baseTest.getProducts_Id(id)
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchema(new File("src/test/resources/json_schema/product_get_id.json")));
    }

    @Test(groups = "Contrato")
    public void shouldReturn200_ValidateContract_GetProductsAuth() {
        baseTest.getProductsAuth(tokenOut.getToken())
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchema(new File("src/test/resources/json_schema/product_get_all.json")));
    }

    @Test(groups = "Contrato")
    public void shouldReturn200_ValidateContract_PostProducts() {
        baseTest.postProduct(datafactory.createProduct())
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchema(new File("src/test/resources/json_schema/product_create.json")));

    }
}
