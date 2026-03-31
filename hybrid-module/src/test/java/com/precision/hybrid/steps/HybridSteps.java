package com.precision.hybrid.steps;

import com.precision.api.builders.UserRequestBuilder;
import com.precision.api.validators.UserValidator;
import com.precision.common.config.ConfigReader;
import com.precision.common.logger.LogManager;
import com.precision.hybrid.context.HybridContext;
import com.precision.ui.pages.HomePage;
import com.precision.ui.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.UUID;

public class HybridSteps {

    private static final Logger log = LogManager.getLogger(HybridSteps.class);

    private final HybridContext context;
    private HomePage homePage;
    private LoginPage loginPage;

    public HybridSteps(HybridContext context) {
        this.context = context;
    }

    @Given("a unique API user payload is prepared")
    public void prepareUniqueApiUserPayload() {
        String uniqueEmail = "hybrid+" + UUID.randomUUID() + "@test.com";
        context.setEmail(uniqueEmail);
        context.setPassword(ConfigReader.get("valid.password"));
        log.info("Prepared hybrid user payload with email={}", uniqueEmail);
    }

    @When("the user account is created through API")
    public void createUserThroughApi() {
        context.setCreateUserResponse(
                UserRequestBuilder.createUser(context.getEmail(), context.getPassword())
        );
        log.info("Create account API response status={}", context.getCreateUserResponse().getStatusCode());
    }

    @Then("the API should confirm account creation")
    public void apiShouldConfirmAccountCreation() {
        Assert.assertEquals(
                context.getCreateUserResponse().getStatusCode(),
                200,
                "Unexpected HTTP status code for create account API"
        );
        UserValidator.validateUserCreated(context.getCreateUserResponse());
    }

    @When("the user logs into the UI with API created credentials")
    public void loginViaUiWithApiCredentials() {
        homePage = new HomePage();
        homePage.navigateToBaseUrl();
        loginPage = homePage.navigateToLoginPage();
        homePage = loginPage.loginAs(context.getEmail(), context.getPassword());
        log.info("Completed UI login attempt for email={}", context.getEmail());
    }

    @Then("the UI should show the user as logged in")
    public void uiShouldShowUserAsLoggedIn() {
        Assert.assertTrue(homePage.isUserLoggedIn(), "Expected user to be logged in on home page");
        Assert.assertNotNull(homePage.getLoggedInUsername(), "Expected logged in username to be visible");
        log.info("Hybrid validation passed. Logged in as {}", homePage.getLoggedInUsername());
    }
}
