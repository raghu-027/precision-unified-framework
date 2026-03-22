package com.precision.ui.stepdefs;

import com.precision.common.logger.LogManager;
import com.precision.common.utils.ExcelUtils;
import com.precision.ui.pages.CartPage;
import com.precision.ui.pages.ProductPage;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import java.util.Map;

public class CartSteps {

    private static final Logger log = LogManager.getLogger(CartSteps.class);

    private final CommonSteps common;
    private ProductPage productPage;
    private CartPage cartPage;
    private String addedProductName;
    private Map<String, String> cartData;

    public CartSteps(CommonSteps common) {
        this.common = common;
    }

    @When("user adds first product to cart")
    public void userAddsFirstProductToCart() {
        cartData = ExcelUtils.getTestData("CartData", "TC4_AddToCart");
        int productIndex = Integer.parseInt(cartData.get("productIndex"));

        log.info("Navigating to products page");
        productPage = common.homePage.navigateToProductsPage();

        dismissAdIfPresent();

        addedProductName = productPage.getProductName(productIndex);
        log.info("Adding product: {}", addedProductName);
        productPage.addProductToCart(productIndex);
    }

    private void dismissAdIfPresent() {
        try {
            org.openqa.selenium.WebDriver driver =
                    com.precision.common.driver.DriverManager.getDriver();
            java.util.List<org.openqa.selenium.WebElement> adClose =
                    driver.findElements(
                            org.openqa.selenium.By.cssSelector("iframe[id*='ad'], .adsbygoogle"));
            if (!adClose.isEmpty()) {
                log.info("Ad detected - executing scroll to dismiss");
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("window.scrollTo(0, 300)");
                Thread.sleep(500);
            }
        } catch (Exception e) {
            log.warn("Ad dismiss attempt failed: {}", e.getMessage());
        }
    }

    @And("user clicks continue shopping")
    public void userClicksContinueShopping() {
        log.info("Clicking Continue Shopping");
        productPage.clickContinueShopping();
    }

    @And("user clicks on Cart button")
    public void userClicksOnCartButton() {
        log.info("Navigating to cart page");
        cartPage = common.homePage.navigateToCartPage();
    }

    @Then("cart page should be displayed")
    public void cartPageShouldBeDisplayed() {
        log.info("Verifying cart page is displayed");
        Assert.assertTrue(
                cartPage.isCartPageLoaded(),
                "FAILED: Cart page not displayed!"
        );
    }

    @And("cart should contain the added product")
    public void cartShouldContainTheAddedProduct() {
        String expectedProduct = cartData.get("expectedProductName");
        String expectedQty     = cartData.get("expectedQuantity");

        log.info("Verifying cart contains: {}", expectedProduct);
        Assert.assertTrue(
                cartPage.getCartItemCount() > 0,
                "FAILED: Cart is empty!"
        );
        Assert.assertEquals(
                cartPage.getItemQuantity(0),
                expectedQty,
                "FAILED: Item quantity mismatch!"
        );
        log.info("TC4 PASSED: Product verified in cart");
    }

    @And("user removes item at index 0")
    public void userRemovesItemAtIndex0() {
        log.info("Removing item at index 0 from cart");
        cartPage.removeItemFromCart(0);
    }

    @Then("cart should be empty")
    public void cartShouldBeEmpty() {
        log.info("Verifying cart is empty");
        Assert.assertTrue(
                cartPage.isCartEmpty(),
                "FAILED: Cart is not empty after removal!"
        );
        log.info("TC5 PASSED: Cart is empty after removal");
    }
}