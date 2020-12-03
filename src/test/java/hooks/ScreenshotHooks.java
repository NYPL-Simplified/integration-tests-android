package hooks;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.context.ScenarioContextKey;
import framework.utilities.ScenarioContext;
import framework.utilities.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ScreenshotHooks {
    private ScenarioContext context;

    @Inject
    public ScreenshotHooks(ScenarioContext context) {
        this.context = context;
    }

    @Before
    public void startScenarioLogger(Scenario scenario) {
        context.add(ScenarioContextKey.SCENARIO_KEY, scenario);
    }

    @After(order = 4)
    public void takeScreenshot(Scenario scenario) {
        AqualityServices.getLogger().info("Taking screenshot");
        scenario.attach(ScreenshotUtils.getScreenshot(), "image/png", "screenshot.png");
    }
}
