package com.precision.common.utils;

import com.precision.common.config.ConfigReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.precision.common.driver.DriverManager;

import java.time.Duration;

public class WaitUtils {

    private static final int TIMEOUT = Integer.parseInt(ConfigReader.get("explicit.wait"));

    // Wait until element is visible on the page
    public static WebElement waitForVisibility(WebElement element) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }
    // Wait until element is clickable
    public static WebElement waitForClickability(WebElement element) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }
    // Wait until element disappears
    public static boolean waitForInvisibility(WebElement element) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.invisibilityOf(element));
    }

}