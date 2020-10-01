package hooks;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.After;

public class ApplicationHooks {
    @After(order = 0)
    public void closeApplication() {
        AqualityServices.getLogger().info("Closing application");
        if (AqualityServices.isApplicationStarted()) {
            AqualityServices.getApplication().quit();
        }
    }
}
