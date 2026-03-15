package com.precision.ui.pages;

import com.precision.common.logger.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//h2[normalize-space()='Login to your account']")
    private WebElement loginFormTitle;

    @FindBy(xpath = "//input[@data-qa='login-email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[normalize-space()='Your email or password is incorrect!']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        LogManager.info("LoginPage initialized");
    }

    public HomePage login(String email, String password) {
        LogManager.info("Logging in with email: " + email);
        type(emailField, email);
        type(passwordField, password);
        click(loginButton);
        return new HomePage(driver);
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(loginFormTitle);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public String getErrorText() {
        return getText(errorMessage);
    }
}