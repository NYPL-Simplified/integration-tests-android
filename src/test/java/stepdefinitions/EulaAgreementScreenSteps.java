package stepdefinitions;

import aquality.appium.mobile.screens.screenfactory.IScreenFactory;
import io.cucumber.java.en.When;
import screens.eulaagreement.EulaAgreementScreen;

public class EulaAgreementScreenSteps {
    private final EulaAgreementScreen eulaAgreementScreen;

    public EulaAgreementScreenSteps(IScreenFactory screenFactory) {
        eulaAgreementScreen = screenFactory.getScreen(EulaAgreementScreen.class);
    }

    @When("I accept EULA agreement")
    public void acceptEULAAgreement() {
        eulaAgreementScreen.clickAgree();
    }
}
