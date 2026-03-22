package com.precision.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(id = "slider-carousel")
    private WebElement homeCarousel;

    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginBtn;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement cartBtn;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutBtn;

    @FindBy(xpath = "//a[contains(.,'Logged in as')]/b")
    private WebElement loggedInUsername;

    public HomePage() {
        super();
    }

    public boolean isHomePageLoaded() {
        return waitForVisibility(homeCarousel).isDisplayed();
    }

    public boolean isUserLoggedIn() {
        try {
            return waitForVisibility(loggedInUsername).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoggedInUsername() {
        return waitForVisibility(loggedInUsername).getText();
    }

    public LoginPage navigateToLoginPage() {
        log.info("Clicking Signup/Login button");
        waitForClickability(signupLoginBtn).click();
        return new LoginPage();
    }

    public CartPage navigateToCartPage() {
        log.info("Clicking Cart button");
        waitForClickability(cartBtn).click();
        return new CartPage();
    }

    public ProductPage navigateToProductsPage() {
        log.info("Navigating to Products page");
        navigateTo("/products");
        waitForPageLoad();
        dismissAdsIfPresent();
        return new ProductPage();
    }

    public void logout() {
        log.info("Clicking Logout");
        waitForClickability(logoutBtn).click();
    }
}