package com.precision.api.steps;

import com.precision.api.builders.UserRequestBuilder;
import com.precision.api.validators.UserValidator;
import com.precision.common.config.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.UUID;

public class UserSteps {

    private Response response;
    private String currentEmail;
    private String currentPassword;

    private String generateUniqueEmail() {
        return "mohansurya+" + UUID.randomUUID() + "@test.com";
    }

    private int getHttpStatusCode() {
        return response.getStatusCode();
    }

    private int getApiResponseCode() {
        return response.jsonPath().getInt("responseCode");
    }

    // TC3 - Create user
    @When("user sends POST request to create account with valid details")
    public void createUser() {
        currentEmail = generateUniqueEmail();
        currentPassword = ConfigReader.get("valid.password");
        response = UserRequestBuilder.createUser(
                currentEmail,
                currentPassword
        );
    }

    @Then("create response status code should be 200")
    public void validateCreateUserStatusCode() {
        int statusCode = getHttpStatusCode();
        assert statusCode == 200 : "Expected HTTP 200 but got " + statusCode;
    }

    @Then("response message should confirm user created")
    public void validateUserCreated() {
        UserValidator.validateUserCreated(response);
    }

    // TC4 - Delete user
    @When("user sends DELETE request with valid credentials")
    public void deleteUser() {
        currentEmail = generateUniqueEmail();
        currentPassword = ConfigReader.get("valid.password");

        UserRequestBuilder.createUser(currentEmail, currentPassword);
        response = UserRequestBuilder.deleteUser(
                currentEmail,
                currentPassword
        );
    }

    @Then("delete response status code should be 200")
    public void validateDeleteUserStatusCode() {
        int responseCode = getApiResponseCode();
        assert responseCode == 200 : "Expected 200 but got " + responseCode;
    }

    @Then("response message should confirm account deleted")
    public void validateUserDeleted() {
        UserValidator.validateUserDeleted(response);
    }

    // TC5 - Update user
    @When("user sends PUT request to update account with new details")
    public void updateUser() {
        currentEmail = generateUniqueEmail();
        currentPassword = ConfigReader.get("valid.password");

        UserRequestBuilder.createUser(currentEmail, currentPassword);
        response = UserRequestBuilder.updateUser(
                currentEmail,
                currentPassword
        );
    }

    @Then("update response status code should be 200")
    public void validateUpdateUserStatusCode() {
        int responseCode = getApiResponseCode();
        assert responseCode == 200 : "Expected 200 but got " + responseCode;
    }

    @Then("response message should confirm user updated")
    public void validateUserUpdated() {
        UserValidator.validateUserUpdated(response);
    }
}
