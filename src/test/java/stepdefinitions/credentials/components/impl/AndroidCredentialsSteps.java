package stepdefinitions.credentials.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import org.testng.Assert;
import stepdefinitions.credentials.components.AbstractCredentialsSteps;

@StepsType(platform = PlatformName.ANDROID)
public class AndroidCredentialsSteps extends AbstractCredentialsSteps {
    public AndroidCredentialsSteps(ScenarioContext context) {
        super(context);
    }

    @Override
    public void checkLoginIsPerformedSuccessfully() {
        Assert.assertTrue(accountScreen.isLoginSuccessful(), "Text on Login button is not changed to Log out on Account screen");
    }
}
