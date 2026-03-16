package com.precision.ui.pages;

import com.precision.common.logger.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(xpath = "//li[normalize-space()='Shopping Cart']")
    private WebElement cartPageTitle;

    @FindBy(xpath = "//tr[contains(@id,'product')]")
    private WebElement cartProductRow;

    @FindBy(xpath = "//a[normalize-space()='Proceed To Checkout']")
    private WebElement proceedToCheckoutButton;

    @FindBy(xpath = "//p[contains(text(),'Cart is empty')]")
    private WebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        super(driver);
        LogManager.info("CartPage initialized");
    }

    public boolean isCartPageDisplayed() {
        LogManager.info("Checking if cart page is displayed");
        return isDisplayed(cartPageTitle);
    }

    public boolean isProductInCart() {
        LogManager.info("Checking if product exists in cart");
        return isDisplayed(cartProductRow);
    }

    public boolean isCartEmpty() {
        LogManager.info("Checking if cart is empty");
        return isDisplayed(emptyCartMessage);
    }

    public void clickProceedToCheckout() {
        LogManager.info("Clicking Proceed To Checkout");
        click(proceedToCheckoutButton);
    }
}