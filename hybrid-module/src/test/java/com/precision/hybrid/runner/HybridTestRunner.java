package com.precision.hybrid.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/hybrid_login.feature",
        glue = {
                "com.precision.hybrid.steps",
                "com.precision.hybrid.hooks"
        },
        plugin = {
                "html:target/cucumber-reports/hybrid-report.html",
                "json:target/cucumber-reports/hybrid-report.json"
        },
        tags = "@hybrid",
        monochrome = true
)
public class HybridTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}