package stepdefinitions.application.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import screens.agegate.AgeGateScreen;
import stepdefinitions.application.components.AbstractApplicationSteps;

@StepsType(platform = PlatformName.IOS)
public class IosApplicationSteps extends AbstractApplicationSteps {
    private final AgeGateScreen ageGateScreen;

    public IosApplicationSteps() {
        super();
        ageGateScreen = screenFactory.getScreen(AgeGateScreen.class);
    }

    public void openApplication() {
        welcomeScreen.addALibraryLater();
        if (ageGateScreen.state().waitForDisplayed()) {
            ageGateScreen.approveAge();
        }
    }
}
