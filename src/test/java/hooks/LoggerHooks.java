package hooks;

import aquality.appium.mobile.application.AqualityServices;
import framework.utilities.Logger;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.*;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class LoggerHooks {
    private static ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);

    @Before(order = 0)
    public void startScenarioLogger(Scenario scenario) {
        Logger.getInstance().createAppender(scenario.getName());
        AqualityServices.getLogger().info(format("Scenario '%s' start", scenario.getName()));
        AqualityServices.getLogger().info("Session id - " + AqualityServices.getApplication().getDriver().getSessionId().toString());
    }

    @After(order = 1)
    public void addLogToScenario(Scenario scenario) {
        byte[] data = Logger.getInstance().getLoggerInfoBytes();
        AqualityServices.getLogger().info(format("Scenario '%s' end", scenario.getName()));
        scenario.attach(data, "text/plain", "log.txt");
        Logger.getInstance().removeAppender();
    }

    @BeforeStep
    public void getStepName(Scenario scenario) throws Exception {
        Field delegateField = scenario.getClass().getDeclaredField("delegate");
        delegateField.setAccessible(true);
        TestCaseState testCaseState = (TestCaseState) delegateField.get(scenario);

        Field testCaseField = testCaseState.getClass().getDeclaredField("testCase");
        testCaseField.setAccessible(true);
        TestCase r1 = (TestCase) testCaseField.get(testCaseState);

        List<PickleStepTestStep> stepDefinitions = r1.getTestSteps()
                .stream()
                .filter(x -> x instanceof PickleStepTestStep)
                .map(x -> (PickleStepTestStep) x)
                .collect(Collectors.toList());

        PickleStepTestStep currentStep = stepDefinitions.get(counter.get());

        AqualityServices.getLogger().info(String.format("Step %d - %s", counter.get(), currentStep.getStep().getText()));
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        counter.set(counter.get() + 1);
    }
}
