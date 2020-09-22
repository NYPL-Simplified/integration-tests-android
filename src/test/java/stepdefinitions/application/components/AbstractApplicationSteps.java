package stepdefinitions.application.components;

import aquality.appium.mobile.application.AqualityServices;
import screens.eulaagreement.EulaAgreementScreen;
import screens.welcome.WelcomeScreen;
import stepdefinitions.BaseSteps;

public abstract class AbstractApplicationSteps extends BaseSteps implements IApplicationSteps {
    protected final EulaAgreementScreen eulaAgreementScreen;
    protected final WelcomeScreen welcomeScreen;

    public AbstractApplicationSteps() {
        eulaAgreementScreen = AqualityServices.getScreenFactory().getScreen(EulaAgreementScreen.class);
        welcomeScreen = AqualityServices.getScreenFactory().getScreen(WelcomeScreen.class);
    }

    public abstract void openApplication();

    @Override
    public void returnToPreviousPage() {
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @Override
    public void restartApp() {
        AqualityServices.getApplication().getDriver().closeApp();
        AqualityServices.getApplication().getDriver().launchApp();
    }
}
