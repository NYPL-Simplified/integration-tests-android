package hooks;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

public class ScreenshotHooks {
    @After(order = 3)
    public void takeScreenshot(Scenario scenario) {
        scenario.attach(AqualityServices.getApplication().getDriver().getScreenshotAs(OutputType.BYTES), "image/png", "screenshot.png");
    }
}
