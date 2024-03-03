package basetest;

import dto.ProductDTO;
import dto.TokenDTO;
import io.restassured.response.ValidatableResponse;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static requestspecification.RequestSpecificationFactory.requestSpecification;
import static requestspecification.RequestSpecificationFactory.responseSpecification;

public class BaseTest {

    public ValidatableResponse getUsers() {
        return given()
                .spec(requestSpecification(BASE_URI))
                .when()
                .get(ENDPOINT_USERS)
                .then()
                .spec(responseSpecification());
    }

    public ValidatableResponse postToken(TokenDTO tokenDTO) {
        return given()
                .spec(requestSpecification(BASE_URI))
                .contentType("application/json")
                .body(tokenDTO)
                .when()
                .post(ENDPOINT_TOKEN_CREATION)
                .then()
                .spec(responseSpecification());
    }

    public ValidatableResponse getProductsAuth(String token) {
        return given()
                .spec(requestSpecification(BASE_URI))
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(ENDPOINT_PRODUCTS_AUTH)
                .then()
                .spec(responseSpecification());
    }

    public ValidatableResponse getProducts_All() {
        return given()
                .spec(requestSpecification(BASE_URI))
                .when()
                .get(ENDPOINT_PRODUCTS_ALL)
                .then()
                .spec(responseSpecification());
    }

    public ValidatableResponse getProducts_Id(Integer id) {
        return given()
                .spec(requestSpecification(BASE_URI))
                .pathParam("id", id)
                .when()
                .get(ENDPOINT_PRODUCTS_BY_ID)
                .then()
                .spec(responseSpecification());
    }

    public ValidatableResponse getProductsAuth_HeaderWithoutToken() {
        return given()
                .spec(requestSpecification(BASE_URI))
                .contentType("application/json")
                .when()
                .get(ENDPOINT_PRODUCTS_AUTH)
                .then()
                .spec(responseSpecification());

    }

    public ValidatableResponse postProduct(ProductDTO productDTO) {
        return given()
                .spec(requestSpecification(BASE_URI))
                .contentType("application/json")
                .body(productDTO)
                .when()
                .post(ENDPOINT_PRODUCTS_CREATION)
                .then()
                .spec(responseSpecification());
    }
}