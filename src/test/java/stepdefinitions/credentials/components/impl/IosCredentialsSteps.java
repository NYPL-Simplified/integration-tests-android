package stepdefinitions.credentials.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import org.testng.Assert;
import stepdefinitions.credentials.components.AbstractCredentialsSteps;

@StepsType(platform = PlatformName.IOS)
public class IosCredentialsSteps extends AbstractCredentialsSteps {
    public IosCredentialsSteps(ScenarioContext context) {
        super(context);
    }

    @Override
    public void checkLoginIsPerformedSuccessfully() {
        Assert.assertTrue(subcategoryScreen.state().waitForDisplayed() || accountScreen.isLoginSuccessful(), "Log in is not completed");
    }
}
