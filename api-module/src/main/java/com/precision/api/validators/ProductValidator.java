package com.precision.api.validators;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.Matchers.*;

public class ProductValidator {

    private static final Logger log = LogManager.getLogger(ProductValidator.class);

    private ProductValidator() {}

    public static void validateAllProducts(Response response) {
        log.info("Validating get all products response");
        response.then()
                .contentType(ContentType.JSON)
                .body("responseCode", equalTo(200))
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0));
    }

    public static void validateSearchProduct(Response response) {
        log.info("Validating search product response");
        response.then()
                .contentType(ContentType.JSON)
                .body("responseCode", equalTo(200))
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0));
    }

    public static void validateMissingParameter(Response response) {
        log.info("Validating missing parameter response");
        response.then()
                .contentType(ContentType.JSON)
                .body("responseCode", equalTo(400))
                .body("message", equalTo(
                        "Bad request, search_product parameter is missing in POST request."
                ));
    }
}