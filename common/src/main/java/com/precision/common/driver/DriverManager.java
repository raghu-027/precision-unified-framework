package com.precision.common.driver;

import com.precision.common.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager {

    // parallel execution safe
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static void initDriver() {
        String browser = ConfigReader.get("browser");
        WebDriver driver;
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser not found" + browser);
        }
        driver.manage().window().maximize();
        driverThread.set(driver);
    }

    public static WebDriver getDriver() {

        return driverThread.get();

    }
    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            driver.quit();
        }
        driverThread.remove();
    }
}