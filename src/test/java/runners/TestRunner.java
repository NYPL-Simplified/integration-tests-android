package runners;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.CucumberOptions;
import courgette.api.testng.TestNGCourgette;
import org.testng.annotations.Test;

/*@CucumberOptions(
        features = {"src/test/java/features"},
        glue = {
                "hooks",
                "transformers",
                "stepdefinitions"
        },
        plugin = {
                "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm",
                //"aquality.tracking.integrations.cucumber5jvm.AqualityTrackingCucumber5Jvm",
                "json:target/Destination/cucumber.json"//,
                //"junit:target/cucumber-results.xml"
        },
        tags = "@wait1",
        strict = true
)*/
@Test
@CourgetteOptions(
        threads = 2,
        runLevel = CourgetteRunLevel.FEATURE,
        showTestOutput = true,
        cucumberOptions = @CucumberOptions(
                features = {"src/test/java/features"},
                glue = {
                        "hooks",
                        "transformers",
                        "stepdefinitions"
                },
                plugin = {
                        "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                        //"aquality.tracking.integrations.cucumber5jvm.AqualityTrackingCucumber5Jvm",
                        "json:target/Destination/cucumber.json"//,
                        //"junit:target/cucumber-results.xml"
                },
                tags = "@wait1"
        ))
public class TestRunner extends TestNGCourgette {

    /*@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }*/
}
