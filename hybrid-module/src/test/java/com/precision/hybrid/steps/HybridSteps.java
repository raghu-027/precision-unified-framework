package com.precision.hybrid.steps;

import com.precision.api.builders.ProductRequestBuilder;
import com.precision.api.builders.UserRequestBuilder;
import com.precision.api.validators.ProductValidator;
import com.precision.api.validators.UserValidator;
import com.precision.common.config.ConfigReader;
import com.precision.common.logger.LogManager;
import com.precision.hybrid.context.HybridContext;
import com.precision.ui.pages.HomePage;
import com.precision.ui.pages.LoginPage;
import com.precision.ui.pages.ProductPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.UUID;

public class HybridSteps {

    private static final Logger log = LogManager.getLogger(HybridSteps.class);

    private final HybridContext context;
    private HomePage homePage;
    private LoginPage loginPage;
    private ProductPage productPage;

    public HybridSteps(HybridContext context) {
        this.context = context;
    }

    @Given("a unique API user payload is prepared")
    public void prepareUniqueApiUserPayload() {
        String uniqueEmail = "hybrid+" + UUID.randomUUID() + "@test.com";
        context.setEmail(uniqueEmail);
        context.setPassword(ConfigReader.get("valid.password"));
        log.info("Prepared hybrid user payload with email={}", uniqueEmail);
    }

    @When("the user account is created through API")
    public void createUserThroughApi() {
        context.setCreateUserResponse(
                UserRequestBuilder.createUser(context.getEmail(), context.getPassword())
        );
        log.info("Create account API response status={}", context.getCreateUserResponse().getStatusCode());
    }

    @Then("the API should confirm account creation")
    public void apiShouldConfirmAccountCreation() {
        Assert.assertEquals(
                context.getCreateUserResponse().getStatusCode(),
                200,
                "Unexpected HTTP status code for create account API"
        );
        UserValidator.validateUserCreated(context.getCreateUserResponse());
    }

    @When("the user logs into the UI with API created credentials")
    public void loginViaUiWithApiCredentials() {
        homePage = new HomePage();
        homePage.navigateToBaseUrl();
        loginPage = homePage.navigateToLoginPage();
        homePage = loginPage.loginAs(context.getEmail(), context.getPassword());
        log.info("Completed UI login attempt for email={}", context.getEmail());
    }

    @Then("the UI should show the user as logged in")
    public void uiShouldShowUserAsLoggedIn() {
        Assert.assertTrue(homePage.isUserLoggedIn(), "Expected user to be logged in on home page");
        Assert.assertNotNull(homePage.getLoggedInUsername(), "Expected logged in username to be visible");
        log.info("Hybrid validation passed. Logged in as {}", homePage.getLoggedInUsername());
    }

    @When("the user logs into the UI with API created email and invalid password")
    public void loginViaUiWithInvalidPassword() {
        homePage = new HomePage();
        homePage.navigateToBaseUrl();
        loginPage = homePage.navigateToLoginPage();
        loginPage
                .enterEmail(context.getEmail())
                .enterPassword(context.getPassword() + "_invalid")
                .clickLoginButtonExpectingFailure();
        log.info("Completed UI invalid login attempt for email={}", context.getEmail());
    }

    @Then("the UI should show login error message")
    public void uiShouldShowLoginErrorMessage() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Expected invalid login error message");
        log.info("Hybrid validation passed. Login error message displayed as expected");
    }

    @When("the user account is deleted through API")
    public void deleteUserThroughApi() {
        context.setDeleteUserResponse(
                UserRequestBuilder.deleteUser(context.getEmail(), context.getPassword())
        );
        log.info("Delete account API response status={}", context.getDeleteUserResponse().getStatusCode());
    }

    @Then("the API should confirm account deletion")
    public void apiShouldConfirmAccountDeletion() {
        Assert.assertEquals(
                context.getDeleteUserResponse().getStatusCode(),
                200,
                "Unexpected HTTP status code for delete account API"
        );
        UserValidator.validateUserDeleted(context.getDeleteUserResponse());
    }

    @When("the product list is fetched through API")
    public void fetchProductListThroughApi() {
        context.setProductsResponse(ProductRequestBuilder.getAllProducts());
        log.info("Get products API response status={}", context.getProductsResponse().getStatusCode());
    }

    @Then("the API should return a non-empty product list")
    public void apiShouldReturnNonEmptyProductList() {
        Assert.assertEquals(
                context.getProductsResponse().getStatusCode(),
                200,
                "Unexpected HTTP status code for products API"
        );
        ProductValidator.validateStatusCode(context.getProductsResponse());
        ProductValidator.validateProductsList(context.getProductsResponse());
    }

    @When("the user navigates to the products page in UI")
    public void userNavigatesToProductsPageInUi() {
        homePage = new HomePage();
        homePage.navigateToBaseUrl();
        productPage = homePage.navigateToProductsPage();
        log.info("Navigated to products page in UI");
    }

    @Then("the UI should show available products")
    public void uiShouldShowAvailableProducts() {
        Assert.assertTrue(productPage.isProductPageLoaded(), "Expected product cards on products page");
        Assert.assertFalse(productPage.getProductName(0).isBlank(), "Expected first product name to be visible");
        log.info("Hybrid product validation passed. First product: {}", productPage.getProductName(0));
    }
}
