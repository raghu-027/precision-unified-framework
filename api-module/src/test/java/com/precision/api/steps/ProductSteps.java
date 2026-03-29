package com.precision.api.steps;

import com.precision.api.builders.ProductRequestBuilder;
import com.precision.api.validators.ProductValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductSteps {

    private static final Logger log = LogManager.getLogger(ProductSteps.class);
    private Response response;

    @Given("the API is available")
    public void theApiIsAvailable() {
        log.info("[STEP] API is available - proceeding with test");
    }

    @When("user sends GET request to products list")
    public void sendGetRequestToProductsList() {
        log.info("[STEP] Sending GET request to fetch all products");
        response = ProductRequestBuilder.getAllProducts();
    }

    @Then("response status code should be 200")
    public void validateStatusCode() {
        log.info("[STEP] Validating response status code");
        ProductValidator.validateStatusCode(response);  // status only
    }

    @Then("products list should not be empty")
    public void validateProductsListNotEmpty() {
        log.info("[STEP] Validating products list is not empty");
        ProductValidator.validateProductsList(response);  // list only
    }

    @When("user searches for product {string}")
    public void searchProduct(String productName) {
        log.info("[STEP] Searching for product -> \"{}\"", productName);
        response = ProductRequestBuilder.searchProduct(productName);
    }

    @Then("search results should not be empty")
    public void validateSearchResults() {
        log.info("[STEP] Validating search results are not empty");
        ProductValidator.validateSearchResults(response);  // search list only
    }

    @When("user sends POST request to search without any parameter")
    public void searchWithoutAnyParameter() {
        log.info("[STEP] Sending POST with no parameter (negative test)");
        response = ProductRequestBuilder.searchProductWithNoParam();
    }

    @Then("response should indicate missing parameter")
    public void validateMissingParameter() {
        log.info("[STEP] Validating missing parameter error response");
        ProductValidator.validateMissingParameter(response);
    }
}
