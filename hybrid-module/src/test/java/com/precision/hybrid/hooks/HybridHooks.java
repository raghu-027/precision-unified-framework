package com.precision.hybrid.hooks;

import com.precision.common.driver.DriverManager;
import com.precision.common.logger.LogManager;
import com.precision.common.reports.ExtentReportManager;
import com.precision.common.reports.ExtentTestManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class HybridHooks {

    private static final Logger log = LogManager.getLogger(HybridHooks.class);

    @Before
    public void setUp(Scenario scenario) {
        ExtentTestManager.createTest(scenario.getName());
        log.info("Starting Hybrid Scenario: {}", scenario.getName());
        ExtentTestManager.getTest().info("Starting Hybrid Scenario: " + scenario.getName());
        DriverManager.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String base64 = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BASE64);
                ExtentTestManager.getTest().fail(
                        "Scenario Failed: " + scenario.getName(),
                        com.aventstack.extentreports.MediaEntityBuilder
                                .createScreenCaptureFromBase64String(base64)
                                .build()
                );
            } else {
                ExtentTestManager.getTest().pass("Scenario Passed: " + scenario.getName());
            }
        } finally {
            DriverManager.quitDriver();
            ExtentTestManager.removeTest();
            ExtentReportManager.flushReports();
            log.info("Hybrid cleanup completed");
        }
    }
}
