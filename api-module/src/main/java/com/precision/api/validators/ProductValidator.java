package com.precision.api.validators;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

import static org.hamcrest.Matchers.*;

public class ProductValidator {

    private static final Logger log = LogManager.getLogger(ProductValidator.class);

    private ProductValidator() {}

    public static void validateAllProducts(Response response) {
        log.info("Validating get all products response");
        int responseCode = response.jsonPath().getInt("responseCode");
        List products = response.jsonPath().getList("products");

        assert responseCode == 200 : "Expected 200 but got " + responseCode;
        assert products != null && products.size() > 0 : "Products list is empty";
    }

    public static void validateSearchProduct(Response response) {
        log.info("Validating search product response");
        int responseCode = response.jsonPath().getInt("responseCode");
        List products = response.jsonPath().getList("products");

        assert responseCode == 200 : "Expected 200 but got " + responseCode;
        assert products != null && products.size() > 0 : "Products list is empty";
    }

    public static void validateMissingParameter(Response response) {
        log.info("Validating missing parameter response");
        int responseCode = response.jsonPath().getInt("responseCode");
        String message = response.jsonPath().getString("message");

        assert responseCode == 400 : "Expected 400 but got " + responseCode;
        assert message.equals("Bad request, search_product parameter is missing in POST request.")
                : "Unexpected message: " + message;
    }
}