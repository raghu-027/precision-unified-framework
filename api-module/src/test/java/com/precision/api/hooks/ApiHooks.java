package com.precision.api.hooks;

import com.precision.common.logger.LogManager;
import com.precision.common.reports.ExtentReportManager;
import com.precision.common.reports.ExtentTestManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Logger;

public class ApiHooks {

    private static final Logger log = LogManager.getLogger(ApiHooks.class);

    @Before
    public void setUp(Scenario scenario) {

        ExtentTestManager.createTest(scenario.getName());

        log.info("Starting Scenario: {}", scenario.getName());

        ExtentTestManager.getTest()
                .info("Starting Scenario: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            log.error("Scenario FAILED: {}", scenario.getName());

            ExtentTestManager.getTest()
                    .fail("Scenario Failed: " + scenario.getName());

        } else {
            log.info("Scenario PASSED: {}", scenario.getName());

            ExtentTestManager.getTest()
                    .pass("Scenario Passed: " + scenario.getName());
        }

        ExtentTestManager.removeTest();
        ExtentReportManager.flushReports();
        log.info("Report updated");
    }
}