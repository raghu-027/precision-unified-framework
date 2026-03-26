package com.precision.ui.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/register.feature",
        glue = {
                "com.precision.ui.stepdefs",
                "com.precision.ui.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/register-report.html",
                "json:target/cucumber-reports/register-report.json"
        },
        tags = "@register",
        monochrome = true
)
public class RegisterTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}