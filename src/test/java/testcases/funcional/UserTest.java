package testcases.funcional;

import basetest.BaseTest;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import datafactory.Datafactory;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Map;

import static constants.Constants.ERROR_MESSAGE_INVALID_CREDENTIALS;
import static constants.Constants.ERROR_MESSAGE_TO_LOWERCASE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Listeners(ExtentITestListenerClassAdapter.class)
public class UserTest {
    private BaseTest baseTest = new BaseTest();
    private Datafactory datafactory = new Datafactory();

    String username;
    String password;

    @BeforeClass(groups = "Funcional-Users")
    public void setEnvironment() {
        //guarda os valores dos campos username e password
        Map<String, Object> userValues = baseTest.getUsers()
                .statusCode(HttpStatus.SC_OK)
                .body("users.username[0]", is(notNullValue()))
                .body("users.password[0]", is(notNullValue()))
                .extract().jsonPath().get("users[0]");

        username = (String) userValues.get("username");
        password = (String) userValues.get("password");
    }

//    @Test(groups = "Funcional-Users")
//    public void shouldReturn200_GetUsers() {
//        baseTest.getUsers()
//                .statusCode(HttpStatus.SC_OK)
//                .body("users.username[0]", is(notNullValue()))
//                .body("users.password[0]", is(notNullValue()));
//    }

    @Test(groups = "Funcional-Users")
    public void shouldReturn200_CreateToken() {
        baseTest.postToken(datafactory.createToken(username, password))
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(notNullValue()),
                        "username", is(username),
                        "email", is(notNullValue()),
                        "firstName", is(notNullValue()),
                        "lastName", is(notNullValue()),
                        "gender", is(notNullValue()),
                        "image", is(notNullValue()),
                        "token", is(notNullValue()));
    }

    @Test(groups = "Funcional-Users")
    public void shouldReturn400_CreateToken_WithInvalidUsername() {
        baseTest.postToken(datafactory.createToken("invallid", password))
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is(ERROR_MESSAGE_INVALID_CREDENTIALS));
    }

    @Test(groups = "Funcional-Users")
    public void shouldReturn500_CreateToken_WithNullUsername() {
        baseTest.postToken(datafactory.createToken(null, password))
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("message", is(ERROR_MESSAGE_TO_LOWERCASE));
    }

    @Test(groups = "Funcional-Users")
    public void shouldReturn400_CreateToken_WithInvalidPassword() {
        baseTest.postToken(datafactory.createToken(username, "invalid"))
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is(ERROR_MESSAGE_INVALID_CREDENTIALS));
    }

    @Test(groups = "Funcional-Users")
    public void shouldReturn400_CreateToken_WithNullPassword() {
        baseTest.postToken(datafactory.createToken(username, null))
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is(ERROR_MESSAGE_INVALID_CREDENTIALS));
    }

    @Test(groups = "Funcional-Users")
    public void shouldReturn400_CreateToken_WithInvalidUsernameAndInvalidPassword() {
        baseTest.postToken(datafactory.createToken("invallid", "invalid"))
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is(ERROR_MESSAGE_INVALID_CREDENTIALS));
    }

    @Test(groups = "Funcional-Users")
    public void shouldReturn500_CreateToken_WithNullUsernameAndNullPassword() {
        baseTest.postToken(datafactory.createToken(null, null))
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("message", is(ERROR_MESSAGE_TO_LOWERCASE));
    }


}