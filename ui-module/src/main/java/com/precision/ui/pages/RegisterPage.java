package com.precision.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import java.util.Map;

public class RegisterPage extends BasePage {

    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupNameField;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmailField;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupButton;

    @FindBy(id = "id_gender1")
    private WebElement titleMr;

    @FindBy(id = "id_gender2")
    private WebElement titleMrs;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "days")
    private WebElement dobDay;

    @FindBy(id = "months")
    private WebElement dobMonth;

    @FindBy(id = "years")
    private WebElement dobYear;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement specialOffersCheckbox;

    @FindBy(id = "first_name")
    private WebElement firstNameField;

    @FindBy(id = "last_name")
    private WebElement lastNameField;

    @FindBy(id = "company")
    private WebElement companyField;

    @FindBy(id = "address1")
    private WebElement address1Field;

    @FindBy(id = "address2")
    private WebElement address2Field;

    @FindBy(id = "country")
    private WebElement countryDropdown;

    @FindBy(id = "state")
    private WebElement stateField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "zipcode")
    private WebElement zipcodeField;

    @FindBy(id = "mobile_number")
    private WebElement mobileField;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement createAccountBtn;

    @FindBy(xpath = "//h2[@class='title text-center']//b")
    private WebElement accountCreatedHeading;

    @FindBy(css = "a[data-qa='continue-button']")
    private WebElement continueBtn;

    public RegisterPage() {
        super();
    }

    public void enterSignupName(String name) {
        log.info("Entering signup name: {}", name);
        clearAndType(signupNameField, name);
    }

    public void enterSignupEmail(String email) {
        log.info("Entering signup email: {}", email);
        clearAndType(signupEmailField, email);
    }

    public void clickSignupButton() {
        log.info("Clicking Signup button");
        waitForClickability(signupButton).click();
    }

    public void selectTitle(String title) {
        log.info("Selecting title: {}", title);
        if (title.equalsIgnoreCase("Mr")) {
            waitForClickability(titleMr).click();
        } else {
            waitForClickability(titleMrs).click();
        }
    }

    public void enterPassword(String password) {
        log.info("Entering password");
        clearAndType(passwordField, password);
    }

    public void selectDobDay(String day) {
        log.info("Selecting DOB day: {}", day);
        new Select(waitForVisibility(dobDay)).selectByValue(day);
    }

    public void selectDobMonth(String month) {
        log.info("Selecting DOB month: {}", month);
        new Select(waitForVisibility(dobMonth)).selectByVisibleText(month);
    }

    public void selectDobYear(String year) {
        log.info("Selecting DOB year: {}", year);
        new Select(waitForVisibility(dobYear)).selectByValue(year);
    }

    public void enterFirstName(String firstName) {
        log.info("Entering first name: {}", firstName);
        clearAndType(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        log.info("Entering last name: {}", lastName);
        clearAndType(lastNameField, lastName);
    }

    public void enterAddress(String address) {
        log.info("Entering address: {}", address);
        clearAndType(address1Field, address);
    }

    public void selectCountry(String country) {
        log.info("Selecting country: {}", country);
        new Select(waitForVisibility(countryDropdown)).selectByVisibleText(country);
    }

    public void enterState(String state) {
        log.info("Entering state: {}", state);
        clearAndType(stateField, state);
    }

    public void enterCity(String city) {
        log.info("Entering city: {}", city);
        clearAndType(cityField, city);
    }

    public void enterZipcode(String zipcode) {
        log.info("Entering zipcode: {}", zipcode);
        clearAndType(zipcodeField, zipcode);
    }

    public void enterMobile(String mobile) {
        log.info("Entering mobile: {}", mobile);
        clearAndType(mobileField, mobile);
    }

    public void clickCreateAccount() {
        log.info("Clicking Create Account button");
        dismissAdsIfPresent();                    // ← add this
        scrollToElement(createAccountBtn);

        try { Thread.sleep(1000); } catch (Exception e) {}

        jsClick(createAccountBtn);

        try { Thread.sleep(2000); } catch (Exception e) {}
        System.out.println("URL after create account: " + driver.getCurrentUrl());
    }

    public boolean isAccountCreated() {
        try {
            boolean created = waitForVisibility(accountCreatedHeading)
                    .getText().equalsIgnoreCase("Account Created!");
            log.info("Account created: {}", created);
            return created;
        } catch (Exception e) {
            log.error("Account Created heading not found: {}", e.getMessage());
            return false;
        }
    }

    public HomePage clickContinue() {
        log.info("Clicking Continue button");
        waitForClickability(continueBtn).click();
        return new HomePage();
    }

    public void fillRegistrationForm(Map<String, String> data) {
        log.info("Filling registration form with test data");
        selectTitle(data.get("title"));
        enterPassword(data.get("password"));
        selectDobDay(data.get("day"));
        selectDobMonth(data.get("month"));
        selectDobYear(data.get("year"));
        enterFirstName(data.get("firstName"));
        enterLastName(data.get("lastName"));
        enterAddress(data.get("address"));
        selectCountry(data.get("country"));
        enterState(data.get("state"));
        enterCity(data.get("city"));
        enterZipcode(data.get("zipcode"));
        enterMobile(data.get("mobile"));
    }
}