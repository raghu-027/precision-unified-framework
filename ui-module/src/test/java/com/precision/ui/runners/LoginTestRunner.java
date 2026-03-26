package com.precision.ui.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/login.feature",
        glue = {
                "com.precision.ui.stepdefs",
                "com.precision.ui.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/login-report.html",
                "json:target/cucumber-reports/login-report.json"
        },
        tags = "@login",
        monochrome = true
)
public class LoginTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}