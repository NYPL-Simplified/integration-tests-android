package runners;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.CucumberOptions;
import courgette.api.testng.TestNGCourgette;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import util.xml.XMLUtil;

@Test
@CourgetteOptions(
        threads = 2,
        runLevel = CourgetteRunLevel.FEATURE,
        cucumberOptions = @CucumberOptions(
                features = {"src/test/java/features"},
                glue = {
                        "hooks",
                        "transformers",
                        "stepdefinitions"
                },
                plugin = {
                        "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                        //todo Aquality Tracking is temporary turned off until AqualityTrackingCucumber6Jvm is released
                        //"aquality.tracking.integrations.cucumber5jvm.AqualityTrackingCucumber5Jvm",
                },
                tags = "@train"
        ))
public class TestRunner extends TestNGCourgette {

}
