package com.precision.api.steps;

import com.precision.api.builders.UserRequestBuilder;
import com.precision.api.validators.UserValidator;
import com.precision.common.config.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class UserSteps {

    private static final Logger log = LogManager.getLogger(UserSteps.class);


    private static String sharedEmail;
    private static String sharedPassword;
    private static boolean userCreated = false;

    private Response response;

    private String generateUniqueEmail() {
        return "mohansurya+" + UUID.randomUUID() + "@test.com";
    }

    @When("user sends POST request to create account with valid details")
    public void createUser() {
        log.info("[STEP] Creating new user account");
        sharedEmail    = generateUniqueEmail();
        sharedPassword = ConfigReader.get("valid.password");
        log.info("  [INFO] Generated Email : {}", sharedEmail);
        log.info("  [INFO] Password        : {}", sharedPassword);
        response    = UserRequestBuilder.createUser(sharedEmail, sharedPassword);
        userCreated = true;
    }

    @Then("create response status code should be 200")
    public void validateCreateUserStatusCode() {
        int statusCode = response.getStatusCode();
        log.info("  [CHECK] HTTP STATUS -> Expected: 200 | Actual: {}", statusCode);
        assert statusCode == 200 : "Expected HTTP 200 but got " + statusCode;
    }

    @Then("response message should confirm user created")
    public void validateUserCreated() {
        UserValidator.validateUserCreated(response);
    }

    @When("user sends PUT request to update account with new details")
    public void updateUser() {
        log.info("[STEP] Updating existing user account");
        if (!userCreated || sharedEmail == null) {
            log.error("  [ERROR] Cannot update - user has not been created yet!");
            log.error("  [ERROR] Ensure TC3 (Create User) runs before TC5 (Update User)");
            throw new IllegalStateException(
                    "Update cannot proceed: user was never created. Run TC3 first."
            );
        }
        response = UserRequestBuilder.updateUser(sharedEmail, sharedPassword);
        log.info("[INFO] Update request sent using credentials from TC3");
    }

    @Then("update response status code should be 200")
    public void validateUpdateUserStatusCode() {
        int responseCode = response.jsonPath().getInt("responseCode");
        log.info("  [CHECK] RESPONSE CODE -> Expected: 200 | Actual: {}", responseCode);
        assert responseCode == 200 : "Expected 200 but got " + responseCode;
    }

    @Then("response message should confirm user updated")
    public void validateUserUpdated() {
        log.info("[STEP] Validating response body for Update User");
        UserValidator.validateUserUpdated(response);
    }

    @When("user sends DELETE request with valid credentials")
    public void deleteUser() {
        log.info("[STEP] Deleting existing user account");
        if (!userCreated || sharedEmail == null) {
            log.error("  [ERROR] Cannot delete - user has not been created yet!");
            log.error("  [ERROR] Ensure TC3 (Create User) runs before TC4 (Delete User)");
            throw new IllegalStateException(
                    "Delete cannot proceed: user was never created. Run TC3 first."
            );
        }
        response    = UserRequestBuilder.deleteUser(sharedEmail, sharedPassword);
        userCreated = false;
        log.info("[INFO] Delete request sent using credentials from TC3");
    }

    @Then("delete response status code should be 200")
    public void validateDeleteUserStatusCode() {
        int responseCode = response.jsonPath().getInt("responseCode");
        log.info("  [CHECK] RESPONSE CODE -> Expected: 200 | Actual: {}", responseCode);
        assert responseCode == 200 : "Expected 200 but got " + responseCode;
    }

    @Then("response message should confirm account deleted")
    public void validateUserDeleted() {
        log.info("[STEP] Validating response body for Delete User");
        UserValidator.validateUserDeleted(response);
    }
}
