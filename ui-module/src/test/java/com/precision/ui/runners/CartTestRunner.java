package com.precision.ui.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/cart.feature",
        glue = {
                "com.precision.ui.stepdefs",
                "com.precision.ui.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cart-report.html",
                "json:target/cucumber-reports/cart-report.json"
        },
        tags = "@cart",
        monochrome = true
)
public class CartTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}