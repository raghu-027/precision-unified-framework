package com.precision.api.hooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiHooks {

    private static final Logger log = LogManager.getLogger(ApiHooks.class);

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("Scenario FAILED: " + scenario.getName());
        } else {
            log.info("Scenario PASSED: " + scenario.getName());
        }
    }
}
