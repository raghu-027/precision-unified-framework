package com.precision.ui.hooks;

import com.precision.common.driver.DriverManager;
import com.precision.common.logger.LogManager;
import com.precision.common.reports.ExtentReportManager;
import com.precision.common.reports.ExtentTestManager;
import com.precision.common.utils.ScreenshotUtils;
import com.aventstack.extentreports.Status;
import io.cucumber.java.*;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {

        ExtentTestManager.createTest(scenario.getName());

        log.info("Starting Scenario: " + scenario.getName());
        ExtentTestManager.getTest()
                .log(Status.INFO, "Starting Scenario: " + scenario.getName());

        DriverManager.initDriver();

        log.info("Browser launched successfully");
        ExtentTestManager.getTest()
                .log(Status.INFO, "Browser launched successfully");
    }

    @After
    public void tearDown(Scenario scenario) {

        try {
            if (scenario.isFailed()) {

                log.error("Scenario FAILED: " + scenario.getName());

                String screenshotPath = ScreenshotUtils
                        .captureScreenshot(scenario.getName());

                ExtentTestManager.getTest()
                        .fail("Scenario Failed: " + scenario.getName());

                ExtentTestManager.getTest()
                        .addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");

                log.error("Screenshot saved at: " + screenshotPath);

            } else {

                log.info("Scenario PASSED: " + scenario.getName());

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