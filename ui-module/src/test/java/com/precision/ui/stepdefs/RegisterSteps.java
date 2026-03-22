package com.precision.ui.stepdefs;

import com.precision.common.logger.LogManager;
import com.precision.common.utils.ExcelUtils;
import com.precision.ui.pages.RegisterPage;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import java.util.Map;

public class RegisterSteps {

    private static final Logger log = LogManager.getLogger(RegisterSteps.class);

    private final CommonSteps common;
    private RegisterPage registerPage;
    private Map<String, String> registerData;

    public RegisterSteps(CommonSteps common) {
        this.common = common;
    }

    @And("user enters signup name and email")
    public void userEntersSignupNameAndEmail() {
        registerData = ExcelUtils.getTestData("RegisterData", "TC1_RegisterUser");

        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@gmail.com";
        log.info("Using unique email: {}", uniqueEmail);

        registerPage = new RegisterPage();
        registerPage.enterSignupName(registerData.get("name"));
        registerPage.enterSignupEmail(uniqueEmail);

        registerData.put("email", uniqueEmail);
    }

    @And("user clicks signup button")
    public void userClicksSignupButton() {
        log.info("Clicking Signup button");
        registerPage.clickSignupButton();
    }

    @And("user fills the registration form")
    public void userFillsTheRegistrationForm() {
        log.info("Filling registration form");
        registerPage.fillRegistrationForm(registerData);
    }

    @And("user clicks create account button")
    public void userClicksCreateAccountButton() {
        log.info("Clicking Create Account button");
        registerPage.clickCreateAccount();
        System.out.println("URL after create account: " +
                registerPage.getCurrentUrl());
    }

    @When("user clicks continue button")
    public void userClicksContinueButton() {
        log.info("Clicking Continue button");
        common.homePage = registerPage.clickContinue();
    }

    @Then("account created message should be displayed")
    public void accountCreatedMessageShouldBeDisplayed() {
        log.info("Verifying Account Created message");
        Assert.assertTrue(
                registerPage.isAccountCreated(),
                "FAILED: Account Created message not displayed!"
        );
        log.info("TC1: Account Created verified");
    }
}
