package com.precision.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(xpath = "//li[contains(text(),'Shopping Cart')]")
    private WebElement cartHeading;

    @FindBy(css = "#cart_info_table tbody tr")
    private List<WebElement> cartItems;

    @FindBy(css = "a[href='/checkout']")
    private WebElement proceedToCheckoutBtn;

    @FindBy(css = ".cart_delete a")
    private List<WebElement> deleteButtons;

    @FindBy(xpath = "//b[contains(text(),'Cart is empty!')]")
    private WebElement emptyCartMessage;

    @FindBy(css = ".cart_price p")
    private List<WebElement> itemPrices;

    @FindBy(css = ".cart_quantity button")
    private List<WebElement> itemQuantities;

    public CartPage() {
        super();
    }

    public boolean isCartPageLoaded() {
        boolean loaded = waitForVisibility(cartHeading).isDisplayed();
        log.info("CartPage loaded: {}", loaded);
        return loaded;
    }

    public int getCartItemCount() {
        int count = cartItems.size();
        log.info("Cart item count: {}", count);
        return count;
    }

    public boolean isCartEmpty() {
        try {
            boolean empty = waitForVisibility(emptyCartMessage).isDisplayed();
            log.info("Cart is empty: {}", empty);
            return empty;
        } catch (Exception e) {
            return false;
        }
    }

    public void removeItemFromCart(int index) {
        log.info("Removing cart item at index: {}", index);
        click(deleteButtons.get(index));
        waitForInvisibility(cartItems.get(index));
    }

    public void removeAllItems() {
        log.info("Removing all items from cart");
        while (!deleteButtons.isEmpty()) {
            click(deleteButtons.get(0));
        }
    }

    public String getItemPrice(int index) {
        return getText(itemPrices.get(index));
    }

    public String getItemQuantity(int index) {
        return getText(itemQuantities.get(index));
    }

    public void proceedToCheckout() {
        log.info("Clicking Proceed To Checkout");
        click(proceedToCheckoutBtn);
    }
}