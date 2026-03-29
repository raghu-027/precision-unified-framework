package com.precision.api.runner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {
                "src/test/resources/features/get_products.feature",
                "src/test/resources/features/search_product.feature",
                "src/test/resources/features/create_user.feature",
                "src/test/resources/features/update_user.feature",
                "src/test/resources/features/delete_user.feature",
                "src/test/resources/features/negative_validation.feature"
        },
        glue = {
                "com.precision.api.steps",
                "com.precision.api.hooks"
        },
        plugin = {
                "html:target/cucumber-reports/api-report.html"
        },
        monochrome = true
)
public class ApiTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
