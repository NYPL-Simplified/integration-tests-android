package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import screens.addaccounts.AddAccountScreen;


public class AddAccountScreenSteps {
    private final AddAccountScreen addAccountScreen;

    public AddAccountScreenSteps() {
        addAccountScreen = AqualityServices.getScreenFactory().getScreen(AddAccountScreen.class);
    }

    @And("I select {string} on Add Account screen")
    public void selectLibrary(String libraryName) {
        addAccountScreen.selectLibrary(libraryName);
    }
}
