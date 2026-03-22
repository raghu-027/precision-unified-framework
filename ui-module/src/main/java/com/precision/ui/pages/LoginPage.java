package com.precision.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//h2[normalize-space()='Login to your account']")
    private WebElement loginHeading;

    @FindBy(css = "input[data-qa='login-email']")
    private WebElement emailField;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[normalize-space()='Your email or password is incorrect!']")
    private WebElement errorMessage;


    public LoginPage() {
        super();
    }

    public boolean isLoginPageLoaded() {
        return waitForVisibility(loginHeading).isDisplayed();
    }

    public LoginPage enterEmail(String email) {
        clearAndType(emailField, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        clearAndType(passwordField, password);
        return this;
    }

    public HomePage clickLoginButton() {
        click(loginButton);
        return new HomePage();
    }

    public LoginPage clickLoginButtonExpectingFailure() {
        click(loginButton);
        return this;
    }

    public HomePage loginAs(String email, String password) {
        log.info("Performing login for: {}", email);

        dismissAdsIfPresent();

        clearAndType(emailField, email);
        clearAndType(passwordField, password);

        System.out.println("Email typed   : " + emailField.getAttribute("value"));
        System.out.println("Password typed: " + passwordField.getAttribute("value"));
        System.out.println("Button visible: " + loginButton.isDisplayed());
        System.out.println("Button enabled: " + loginButton.isEnabled());

        try { Thread.sleep(1000); } catch (Exception e) {}

        dismissAdsIfPresent();

        try {
            waitForClickability(loginButton).click();
            System.out.println("Normal click worked");
        } catch (Exception e) {
            System.out.println("Normal click failed: " + e.getMessage());
            try {
                jsClick(loginButton);
                System.out.println("JS click worked");
            } catch (Exception e2) {
                System.out.println("JS click failed: " + e2.getMessage());
                new org.openqa.selenium.interactions.Actions(driver)
                        .moveToElement(loginButton)
                        .click()
                        .perform();
                System.out.println("Actions click used");
            }
        }

        try { Thread.sleep(3000); } catch (Exception e) {}
        System.out.println("URL after login: " + driver.getCurrentUrl());

        return new HomePage();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return waitForVisibility(errorMessage).isDisplayed();
        } catch (Exception e) {
            log.warn("Error message not found with current locator");
            return false;
        }
    }


    public String getErrorMessage() {
        return getText(errorMessage);
    }
}