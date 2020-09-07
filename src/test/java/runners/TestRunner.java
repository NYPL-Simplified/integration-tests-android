package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/java/features"},
        glue = {
                "hooks",
                "transformers",
                "stepdefinitions"
        },
        plugin = {
                "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm"
        },
        strict = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
