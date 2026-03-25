package com.precision.api.steps;

import com.precision.api.builders.UserRequestBuilder;
import com.precision.api.validators.UserValidator;
import com.precision.common.config.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class UserSteps {

    private Response response;

    // TC3 - Create user
    @When("user sends POST request to create account with valid details")
    public void createUser() {
        response = UserRequestBuilder.createUser(
                ConfigReader.get("valid.email"),
                ConfigReader.get("valid.password")
        );
    }

    @Then("response message should confirm user created")
    public void validateUserCreated() {
        UserValidator.validateUserCreated(response);
    }

    // TC4 - Delete user
    @When("user sends DELETE request with valid credentials")
    public void deleteUser() {
        response = UserRequestBuilder.deleteUser(
                ConfigReader.get("valid.email"),
                ConfigReader.get("valid.password")
        );
    }

    @Then("response message should confirm account deleted")
    public void validateUserDeleted() {
        UserValidator.validateUserDeleted(response);
    }

    // TC5 - Update user
    @When("user sends PUT request to update account with new details")
    public void updateUser() {
        response = UserRequestBuilder.updateUser(
                ConfigReader.get("valid.email"),
                ConfigReader.get("valid.password")
        );
    }

    @Then("response message should confirm user updated")
    public void validateUserUpdated() {
        UserValidator.validateUserUpdated(response);
    }
}
