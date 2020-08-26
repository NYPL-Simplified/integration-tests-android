package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.When;
import screens.eulaagreement.EulaAgreementScreen;
import screens.welcome.WelcomeScreen;

public class ApplicationSteps {
    private final EulaAgreementScreen eulaAgreementScreen;
    private final WelcomeScreen welcomeScreen;

    public ApplicationSteps() {
        eulaAgreementScreen = AqualityServices.getScreenFactory().getScreen(EulaAgreementScreen.class);
        welcomeScreen = AqualityServices.getScreenFactory().getScreen(WelcomeScreen.class);
    }

    @When("I open application")
    public void openApplication() {
        eulaAgreementScreen.clickAgree();
        welcomeScreen.addALibraryLater();
    }
}
