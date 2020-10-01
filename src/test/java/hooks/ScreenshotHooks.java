package hooks;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

public class ScreenshotHooks {
    @After(order = 4)
    public void takeScreenshot(Scenario scenario) {
        AqualityServices.getLogger().info("Taking screenshot");
        scenario.attach(AqualityServices.getApplication().getDriver().getScreenshotAs(OutputType.BYTES), "image/png", "screenshot.png");
    }
}
