package com.precision.ui.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {}

    public static void initDriver(String browser, boolean headless) {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }

        WebDriver webDriver;

        switch (browser.toLowerCase().trim()) {

            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
            }

            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                webDriver = new EdgeDriver(edgeOptions);
            }

            default -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                webDriver = new ChromeDriver(chromeOptions);
            }
        }

        webDriver.manage().window().maximize();
        driver.set(webDriver);
    }

    public static void initDriver() {
        initDriver("chrome", false);
    }

    public static void initDriver(String browser) {
        initDriver(browser, false);
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException(
                    "Driver not initialized. Call initDriver() before getDriver()."
            );
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }

    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}