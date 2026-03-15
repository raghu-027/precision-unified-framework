package com.precision.ui.pages;

import com.precision.common.logger.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[contains(text(),'Signup / Login')]")
    private WebElement signupLoginLink;

    @FindBy(xpath = "//a[contains(text(),'Cart')]")
    private WebElement cartLink;

    @FindBy(xpath = "//a[contains(text(),' Logged in as ')]")
    private WebElement loggedInUsername;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    private WebElement logoutLink;

    public HomePage(WebDriver driver) {
        super(driver);
        LogManager.info("HomePage initialized");
    }

    public LoginPage navigateToLogin() {
        LogManager.info("Clicking Signup/Login link");
        click(signupLoginLink);
        return new LoginPage(driver);
    }

    public CartPage navigateToCart() {
        LogManager.info("Clicking Cart link");
        click(cartLink);
        return new CartPage(driver);
    }

    public boolean isUserLoggedIn() {
        LogManager.info("Checking if user is logged in");
        return isDisplayed(loggedInUsername);
    }

    public String getLoggedInUsername() {
        return getText(loggedInUsername);
    }

    public void clickLogout() {
        click(logoutLink);
    }
}