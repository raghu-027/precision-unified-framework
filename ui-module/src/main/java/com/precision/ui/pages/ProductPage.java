package com.precision.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class ProductPage extends BasePage {

    @FindBy(css = ".productinfo.text-center p")
    private List<WebElement> productNames;

    @FindBy(css = ".product-image-wrapper")
    private List<WebElement> productCards;

    @FindBy(css = "a[data-product-id]")
    private List<WebElement> addToCartButtons;

    @FindBy(css = "#cartModal .modal-title")
    private WebElement modalTitle;

    @FindBy(css = "button[data-dismiss='modal']")
    private WebElement continueShoppingBtn;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement viewCartBtn;

    public ProductPage() {
        super();
    }

    public boolean isProductPageLoaded() {
        return !productCards.isEmpty();
    }

    public String getProductName(int index) {
        return getText(productNames.get(index));
    }

    public void addProductToCart(int index) {
        log.info("Adding product at index: {}", index);
        dismissAdsIfPresent();                   // ← dismiss ads first
        scrollToElement(productCards.get(index));
        jsClick(addToCartButtons.get(index));
        waitForVisibility(modalTitle);
    }

    public void clickContinueShopping() {
        log.info("Clicking Continue Shopping");
        waitForVisibility(continueShoppingBtn);
        jsClick(continueShoppingBtn);
    }

    public CartPage clickViewCart() {
        log.info("Clicking View Cart");
        click(viewCartBtn);
        return new CartPage();
    }
}