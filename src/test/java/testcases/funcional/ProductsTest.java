package testcases.funcional;

import basetest.BaseTest;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import datafactory.Datafactory;
import dto.ProductDTO;
import dto.TokenOutDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Map;

import static constants.Constants.NONEXISTENT_TOKEN;
import static org.hamcrest.CoreMatchers.*;

@Listeners(ExtentITestListenerClassAdapter.class)
public class ProductsTest {
    private BaseTest baseTest = new BaseTest();
    private Datafactory datafactory = new Datafactory();
    String username;
    String password;
    TokenOutDTO tokenOut;
    Integer id;

    @BeforeClass(groups = "Funcional-Products")
    public void setEnvironment() {
        //guarda os valores dos campos username e password
        Map<String, Object> userValues = baseTest.getUsers()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().get("users[0]");

        username = (String) userValues.get("username");
        password = (String) userValues.get("password");

        //guarda o valor do campo token
        tokenOut = baseTest.postToken(datafactory.createToken(username, password))
                .statusCode(HttpStatus.SC_OK)
                .extract().as(TokenOutDTO.class);

        //guarda o valor do campo id
        id = baseTest.getProducts_All()
                .statusCode(HttpStatus.SC_OK)
                .body("products.size()", is(not(0)))
                .extract().jsonPath().get("products.id[3]");
    }

//    @Test(groups = "Funcional-Products")
//    public void shouldReturn200_GetProducts() {
//        baseTest.getProducts_All()
//                .statusCode(HttpStatus.SC_OK)
//                .body("products.size()", is(not(0)));
//    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn200_GetProducts_Id() {
        baseTest.getProducts_Id(id)
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(id));
    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn404_GetProducts_WithNonexistentId() {
        baseTest.getProducts_Id(1256)
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Product with id '1256' not found"));
    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn200_GetProductsAuth() {
        baseTest.getProductsAuth(tokenOut.getToken())
                .statusCode(HttpStatus.SC_OK)
                .body("products.size()", is(not(0)));
    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn401_GetProducts_WithInvalidToken() {
        baseTest.getProductsAuth(RandomStringUtils.randomAlphanumeric(340))
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("name", is("JsonWebTokenError"),
                        "message", is("Invalid/Expired Token!"));
    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn500_GetProducts_WithNonexistentToken() {
        baseTest.getProductsAuth(NONEXISTENT_TOKEN)
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("message", is("invalid signature"));
    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn403_GetProducts_WithoutToken() {
        baseTest.getProductsAuth_HeaderWithoutToken()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("message", is("Authentication Problem"));
    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn200_PostProducts() {
        ProductDTO productIn = datafactory.createProduct();

        baseTest.postProduct(productIn)
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(notNullValue()),
                        "title", is(productIn.getTitle()),
                        "price", is(productIn.getPrice()),
                        "stock", is(productIn.getStock()),
                        "rating", is(8),
                        "thumbnail", is(productIn.getThumbnail()),
                        "description", is(productIn.getDescription()),
                        "brand", is(productIn.getBrand()),
                        "category", is(productIn.getCategory()));
    }

    @Test(groups = "Funcional-Products")
    public void shouldReturn200_PostProducts_Empty() {
        baseTest.postProduct(datafactory.createProduct_Empty())
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(notNullValue()),
                        "title", is(nullValue()),
                        "price", is(nullValue()),
                        "stock", is(nullValue()),
                        "rating", is(0),
                        "thumbnail", is(nullValue()),
                        "description", is(nullValue()),
                        "brand", is(nullValue()),
                        "category", is(nullValue()));
    }
}
