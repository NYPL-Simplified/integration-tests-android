package hooks;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class ApplicationHooks {
    @After(order = 0)
    public void closeApplication() {
        if (AqualityServices.isApplicationStarted()) {
            AqualityServices.getLogger().info("Closing app");
            AqualityServices.getApplication().getDriver().closeApp();
        }
    }

    @Before
    public void launchApplication() {
        AqualityServices.getLogger().info("Starting app");
        AqualityServices.getApplication().getDriver().launchApp();
    }
}
