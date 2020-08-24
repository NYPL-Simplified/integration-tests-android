package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.When;
import screens.eulaagreement.EulaAgreementScreen;

public class EulaAgreementScreenSteps {
    private final EulaAgreementScreen eulaAgreementScreen;

    public EulaAgreementScreenSteps() {
        eulaAgreementScreen = AqualityServices.getScreenFactory().getScreen(EulaAgreementScreen.class);
    }

    @When("I accept EULA agreement")
    public void acceptEULAAgreement() {
        eulaAgreementScreen.clickAgree();
    }
}
