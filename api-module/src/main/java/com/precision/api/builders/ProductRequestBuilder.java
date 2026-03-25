package com.precision.api.builders;
import com.precision.api.client.ApiClient;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductRequestBuilder {

    private ProductRequestBuilder() {}

    public static Response getAllProducts() {
        return given()
                .spec(ApiClient.getBaseSpec())
                .when()
                .get("/api/productsList");
    }

    public static Response searchProduct(String productName) {
        return given()
                .spec(ApiClient.getBaseSpec())
                .formParam("search_product", productName)
                .when()
                .post("/api/searchProduct");
    }

    public static Response searchProductWithNoParam() {
        return given()
                .spec(ApiClient.getBaseSpec())
                .when()
                .post("/api/searchProduct");
    }
}