package com.precision.api.validators;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductValidator {

    private static final Logger log = LogManager.getLogger(ProductValidator.class);

    private ProductValidator() {}


    public static void validateStatusCode(Response response) {
        int responseCode = response.jsonPath().getInt("responseCode");
        log.info("  [CHECK] STATUS CODE -> Expected: 200 | Actual: {}", responseCode);
        assert responseCode == 200 : "Expected 200 but got " + responseCode;
    }

    public static void validateProductsList(Response response) {
        List<?> products = response.jsonPath().getList("products");
        int productCount = (products != null) ? products.size() : 0;
        log.info("  [CHECK] PRODUCTS    -> Expected: non-empty | Actual: {} items", productCount);
        assert products != null && products.size() > 0 : "Products list is empty";
        log.info("  [PASS]  Products list validation PASSED");
    }

    public static void validateSearchResults(Response response) {
        List<?> products = response.jsonPath().getList("products");
        int productCount = (products != null) ? products.size() : 0;
        log.info("  [CHECK] RESULTS     -> Expected: non-empty | Actual: {} items", productCount);
        assert products != null && products.size() > 0 : "Search results are empty";
        log.info("  [PASS]  Search results validation PASSED");
    }

    public static void validateMissingParameter(Response response) {
        log.info("[VALIDATE] Missing Parameter Response (Negative Test)");
        int responseCode   = response.jsonPath().getInt("responseCode");
        String message     = response.jsonPath().getString("message");
        String expectedMsg = "Bad request, search_product parameter is missing in POST request.";

        log.info("  [CHECK] STATUS CODE -> Expected: 400 | Actual: {}", responseCode);
        assert responseCode == 400 : "Expected 400 but got " + responseCode;

        log.info("  [CHECK] MESSAGE     -> Expected: {} | Actual: {}", expectedMsg, message);
        assert message.equals(expectedMsg) : "Unexpected message: " + message;

        log.info("  [PASS]  All validations PASSED for Negative Validation");
    }
}