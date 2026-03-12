package com.precision.ui.pages;

import com.precision.common.logger.LogManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 15;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        PageFactory.initElements(driver, this);
        LogManager.info("Initialized page: " + this.getClass().getSimpleName());
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        LogManager.info("Clicked on element: " + element);
    }

    protected void clickByLocator(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        LogManager.info("Clicked on locator: " + locator);
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
        LogManager.info("Typed '" + text + "' into element");
    }

    protected void clearAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        LogManager.info("Cleared and typed '" + text + "' into element");
    }

    protected String getText(WebElement element) {
        String text = wait.until(ExpectedConditions.visibilityOf(element)).getText();
        LogManager.info("Got text: " + text);
        return text;
    }

    protected String getAttribute(WebElement element, String attribute) {
        return wait.until(ExpectedConditions.visibilityOf(element))
                .getAttribute(attribute);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            LogManager.warn("Element not visible: " + e.getMessage());
            return false;
        }
    }

    protected boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            LogManager.warn("Element not present: " + locator);
            return false;
        }
    }

    protected void selectByVisibleText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        new Select(element).selectByVisibleText(text);
        LogManager.info("Selected dropdown option: " + text);
    }

    protected void selectByValue(WebElement element, String value) {
        wait.until(ExpectedConditions.visibilityOf(element));
        new Select(element).selectByValue(value);
        LogManager.info("Selected dropdown by value: " + value);
    }

    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitForPageLoad() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
        LogManager.info("Page fully loaded");
    }

    protected void navigateTo(String url) {
        driver.get(url);
        LogManager.info("Navigated to URL: " + url);
        waitForPageLoad();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
        LogManager.info("Scrolled to element");
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
        LogManager.info("JS clicked on element");
    }

    protected byte[] takeScreenshot() {
        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    }

    protected List<WebElement> getElements(By locator) {
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected int getElementCount(By locator) {
        return getElements(locator).size();
    }
}