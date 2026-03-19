package com.precision.ui.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends BasePage {

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

    public void selectTitle(String title) {
        if (title.equalsIgnoreCase("Mr")) {
            waitForClickability(titleMr).click();
        } else {
            waitForClickability(titleMrs).click();
        }
    }

    public void enterName(String name) {
        clearAndType(nameField, name);
    }

    public void enterPassword(String password) {
        clearAndType(passwordField, password);
    }

    public void selectDobDay(String day) {
        new Select(waitForVisibility(dobDay)).selectByValue(day);
    }

    public void selectDobMonth(String month) {
        new Select(waitForVisibility(dobMonth)).selectByVisibleText(month);
    }

    public void selectDobYear(String year) {
        new Select(waitForVisibility(dobYear)).selectByValue(year);
    }

    public void enterFirstName(String firstName) {
        clearAndType(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        clearAndType(lastNameField, lastName);
    }

    public void enterAddress(String address) {
        clearAndType(address1Field, address);
    }

    public void selectCountry(String country) {
        new Select(waitForVisibility(countryDropdown)).selectByVisibleText(country);
    }

    public void enterState(String state) {
        clearAndType(stateField, state);
    }

    public void enterCity(String city) {
        clearAndType(cityField, city);
    }

    public void enterZipcode(String zipcode) {
        clearAndType(zipcodeField, zipcode);
    }

    public void enterMobile(String mobile) {
        clearAndType(mobileField, mobile);
    }

    public void clickCreateAccount() {
        scrollToElement(createAccountBtn);
        waitForClickability(createAccountBtn).click();
    }

    public boolean isAccountCreated() {
        return waitForVisibility(accountCreatedHeading)
                .getText().equalsIgnoreCase("Account Created!");
    }

    public HomePage clickContinue() {
        waitForClickability(continueBtn).click();
        return new HomePage();
    }

    public void fillRegistrationForm(java.util.Map<String, String> data) {
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