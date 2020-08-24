package stepdefinitions;

import aquality.appium.mobile.screens.screenfactory.IScreenFactory;
import io.cucumber.java.en.And;
import screens.addaccounts.AddAccountScreen;

public class AddAccountScreenSteps {
    private final AddAccountScreen addAccountScreen;

    public AddAccountScreenSteps(IScreenFactory screenFactory) {
        addAccountScreen = screenFactory.getScreen(AddAccountScreen.class);
    }

    @And("I select {string} on Add Account screen")
    public void selectLibrary(String libraryName) {
        addAccountScreen.selectLibrary(libraryName);
    }
}
