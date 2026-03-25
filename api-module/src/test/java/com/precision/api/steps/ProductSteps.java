package com.precision.api.steps;
import com.precision.api.builders.ProductRequestBuilder;
import com.precision.api.validators.ProductValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class ProductSteps {

    private Response response;

    @Given("the API is available")
    public void theApiIsAvailable() {}

    @When("user sends GET request to products list")
    public void sendGetRequestToProductsList() {
        response = ProductRequestBuilder.getAllProducts();
    }

    @Then("response status code should be 200")
    public void validateStatusCode() {
        ProductValidator.validateAllProducts(response);
    }

    @Then("products list should not be empty")
    public void validateProductsListNotEmpty() {
        ProductValidator.validateAllProducts(response);
    }

    @When("user searches for product {string}")
    public void searchProduct(String productName) {
        response = ProductRequestBuilder.searchProduct(productName);
    }

    @Then("search results should not be empty")
    public void validateSearchResults() {
        ProductValidator.validateSearchProduct(response);
    }

    @When("user sends POST request to search without any parameter")
    public void searchWithoutAnyParameter() {
        response = ProductRequestBuilder.searchProductWithNoParam();
    }

    @Then("response should indicate missing parameter")
    public void validateMissingParameter() {
        ProductValidator.validateMissingParameter(response);
    }
}