package com.precision.ui.stepdefs;

import com.precision.common.logger.LogManager;
import com.precision.ui.pages.HomePage;
import com.precision.ui.pages.LoginPage;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class CommonSteps {

    private static final Logger log = LogManager.getLogger(CommonSteps.class);

    public HomePage homePage;
    public LoginPage loginPage;

    @Given("user launches the application")
    public void userLaunchesTheApplication() {
        log.info("Launching application");
        homePage = new HomePage();
        homePage.navigateToBaseUrl();
    }

    @When("user clicks on Signup Login button")
    public void userClicksOnSignupLoginButton() {
        log.info("Clicking Signup/Login button");
        loginPage = homePage.navigateToLoginPage();
    }

    @Then("user should be logged in successfully")
    public void userShouldBeLoggedInSuccessfully() {
        log.info("Verifying user is logged in");
        Assert.assertTrue(
                homePage.isUserLoggedIn(),
                "FAILED: User is not logged in!"
        );
        log.info("PASSED: Logged in as: {}", homePage.getLoggedInUsername());
    }
}