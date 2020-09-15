package hooks;

import aquality.appium.mobile.application.AqualityServices;
import framework.utilities.Logger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static java.lang.String.format;

public class LoggerHooks {
    @Before(order = 0)
    public void startScenarioLogger(Scenario scenario) {
        Logger.getInstance().createAppender(scenario.getName());
        AqualityServices.getLogger().info(format("Scenario '%s' start", scenario.getName()));
    }

    @After(order = 0)
    public void addLogToScenario(Scenario scenario) {
        byte[] data = Logger.getInstance().getLoggerInfoBytes();
        AqualityServices.getLogger().info(format("Scenario '%s' end", scenario.getName()));
        scenario.attach(data, "text/plain", "log.txt");
        Logger.getInstance().removeAppender();
    }
}
