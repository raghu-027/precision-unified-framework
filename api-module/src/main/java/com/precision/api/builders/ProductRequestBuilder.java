package com.precision.api.builders;

import com.precision.api.client.ApiClient;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class ProductRequestBuilder {

    private static final Logger log = LogManager.getLogger(ProductRequestBuilder.class);

    private ProductRequestBuilder() {}

    public static Response getAllProducts() {

        log.info("REQUEST : GET /api/productsList");

        Response response = given()
                .spec(ApiClient.getBaseSpec())
                .when()
                .get("/api/productsList");
        return response;
    }

    public static Response searchProduct(String productName) {

        log.info("REQUEST : POST /api/searchProduct");
        log.info("PARAM   : search_product = {}", productName);

        Response response = given()
                .spec(ApiClient.getBaseSpec())
                .formParam("search_product", productName)
                .when()
                .post("/api/searchProduct");

        return response;
    }

    public static Response searchProductWithNoParam() {

        log.info("REQUEST : POST /api/searchProduct");
        log.info("PARAM   : (none - negative test)");


        Response response = given()
                .spec(ApiClient.getBaseSpec())
                .when()
                .post("/api/searchProduct");

        return response;
    }

}