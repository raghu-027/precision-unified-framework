package com.precision.ui.pages;

import com.precision.common.config.ConfigReader;
import com.precision.common.driver.DriverManager;
import com.precision.common.logger.LogManager;
import com.precision.common.utils.WaitUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final Logger log;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.log = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }

    // ───────────────── Navigation ─────────────────

    public void navigateToBaseUrl() {
        String url = ConfigReader.get("base.url");
        log.info("Navigating to base URL: {}", url);
        driver.get(url);
    }

    public void navigateTo(String path) {
        String url = ConfigReader.get("base.url") + path;
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    // ───────────────── Browser Utilities ─────────────────

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void refreshPage() {
        log.info("Refreshing page");
        driver.navigate().refresh();
    }

    public void goBack() {
        log.info("Navigating back");
        driver.navigate().back();
    }



    protected void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    // ───────────────── Wait Utilities ─────────────────

    protected WebElement waitForVisibility(WebElement element) {
        return WaitUtils.waitForVisibility(element);
    }

    protected WebElement waitForClickability(WebElement element) {
        return WaitUtils.waitForClickability(element);
    }

    protected boolean waitForInvisibility(WebElement element) {
        return WaitUtils.waitForInvisibility(element);
    }


    protected void waitForUrlToChange(String currentUrlPart) {
        log.info("Waiting for URL to change from: {}", currentUrlPart);
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.not(
                        ExpectedConditions.urlContains(currentUrlPart)
                ));
    }
    // ───────────────── Element Actions ─────────────────

    protected void click(WebElement element) {
        log.info("Clicking element");
        waitForClickability(element).click();
    }

    protected void clearAndType(WebElement element, String text) {
        log.info("Typing text: {}", text);
        WebElement el = waitForVisibility(element);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(WebElement element) {
        return waitForVisibility(element).getText();
    }

    // ───────────────── JavaScript Utilities ─────────────────

    protected void scrollToElement(WebElement element) {
        log.info("Scrolling to element");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void jsClick(WebElement element) {
        log.info("Performing JS click");
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    // ───────────────── Screenshot Utility ─────────────────

    protected void takeScreenshot(String name) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/" + name + ".png");

        try {
            FileHandler.copy(src, dest);
            log.info("Screenshot saved: {}", dest.getAbsolutePath());
        } catch (IOException e) {
            log.error("Failed to save screenshot", e);
        }
    }
    protected void dismissAdsIfPresent() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var selectors = [" +
                            "'iframe[id*=google]', 'iframe[id*=ad]', " +
                            "'.adsbygoogle', '#ad_position_box', " +
                            "'.advertisement', 'ins.adsbygoogle', " +
                            "'div[id*=google_ads]', 'div[class*=adsbygoogle]'" +
                            "];" +
                            "selectors.forEach(function(sel) {" +
                            "  document.querySelectorAll(sel).forEach(function(el) {" +
                            "    el.style.display='none';" +
                            "  });" +
                            "});"
            );
            log.info("Ads hidden via JS");
            Thread.sleep(300);
        } catch (Exception e) {
            log.warn("Ad dismissal skipped: {}", e.getMessage());
        }
    }
}