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

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(),'Your email or password is incorrect')]")
    private WebElement errorMessage;

    public LoginPage() {
        super();
    }

    public boolean isLoginPageLoaded() {
        boolean isDisplayed = waitForVisibility(loginHeading).isDisplayed();
        log.info("Login page loaded: {}", isDisplayed);
        return isDisplayed;
    }

    public LoginPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        clearAndType(emailField, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        clearAndType(passwordField, password);
        return this;
    }

    public HomePage clickLoginButton() {
        log.info("Clicking Login button");
        click(loginButton);
        return new HomePage();
    }

    public HomePage loginAs(String email, String password) {
        log.info("Performing login for user: {}", email);
        enterEmail(email);
        enterPassword(password);
        click(loginButton);
        return new HomePage();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return waitForVisibility(errorMessage).isDisplayed();
        } catch (Exception e) {
            log.warn("Error message not displayed");
            return false;
        }
    }

    public String getErrorMessage() {
        String message = getText(errorMessage);
        log.warn("Login failed with message: {}", message);
        return message;
    }
}