package com.precision.ui.hooks;

import com.precision.common.driver.DriverManager;
import com.precision.common.logger.LogManager;
import com.precision.common.reports.ExtentReportManager;
import com.precision.common.reports.ExtentTestManager;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        ExtentTestManager.createTest(scenario.getName());
        log.info("Starting Scenario: {}", scenario.getName());
        ExtentTestManager.getTest()
                .info("Starting Scenario: " + scenario.getName());
        DriverManager.initDriver();
        log.info("Browser launched successfully");
        ExtentTestManager.getTest()
                .info("Browser launched successfully");
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                log.error("Scenario FAILED: {}", scenario.getName());

                String base64 = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BASE64);

                ExtentTestManager.getTest()
                        .fail("Scenario Failed: " + scenario.getName(),
                                MediaEntityBuilder
                                        .createScreenCaptureFromBase64String(base64)
                                        .build()
                        );

            } else {
                log.info("Scenario PASSED: {}", scenario.getName());
                ExtentTestManager.getTest()
                        .pass("Scenario Passed: " + scenario.getName());
            }
        } finally {
            DriverManager.quitDriver();
            log.info("Browser closed");
            ExtentTestManager.removeTest();
            ExtentReportManager.flushReports();
            log.info("Report updated");
        }
    }
}