package com.precision.ui.stepdefs;

import com.precision.common.logger.LogManager;
import com.precision.common.utils.ExcelUtils;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import java.util.Map;

public class LoginSteps {

    private static final Logger log = LogManager.getLogger(LoginSteps.class);

    private final CommonSteps common;

    public LoginSteps(CommonSteps common) {
        this.common = common;
    }

//    @And("user logs in with valid credentials")
//    public void userLogsInWithValidCredentials() {
//        Map<String, String> data = ExcelUtils.getTestData("LoginData", "TC2_ValidLogin");
//        log.info("Logging in with valid credentials");
//        common.homePage = common.loginPage.loginAs(
//                data.get("email"),
//                data.get("password")
//        );
//    }
@And("user logs in with valid credentials")
public void userLogsInWithValidCredentials() {
    Map<String, String> data = ExcelUtils.getTestData("LoginData", "TC2_ValidLogin");

    System.out.println("=== DEBUG ===");
    System.out.println("Email   : " + data.get("email"));
    System.out.println("Password: " + data.get("password"));
    System.out.println("URL before login: " + common.loginPage.getCurrentUrl());

    common.homePage = common.loginPage.loginAs(
            data.get("email"),
            data.get("password")
    );

    System.out.println("URL after login : " + common.homePage.getCurrentUrl());
    System.out.println("=============");
}

    @And("user logs in with invalid credentials")
    public void userLogsInWithInvalidCredentials() {
        Map<String, String> data = ExcelUtils.getTestData("LoginData", "TC3_InvalidLogin");
        log.info("Logging in with invalid credentials");
        common.loginPage
                .enterEmail(data.get("email"))
                .enterPassword(data.get("password"))
                .clickLoginButtonExpectingFailure();
    }


@Then("error message should be displayed")
public void errorMessageShouldBeDisplayed() {
    System.out.println("Current URL: " + common.loginPage.getCurrentUrl());
    System.out.println("Error displayed: " + common.loginPage.isErrorMessageDisplayed());
    System.out.println("Error text: " + common.loginPage.getErrorMessage());

    Assert.assertTrue(
            common.loginPage.isErrorMessageDisplayed(),
            "FAILED: Error message not visible!"
    );
}
}