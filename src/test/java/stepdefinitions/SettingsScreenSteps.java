package stepdefinitions;

import aquality.appium.mobile.screens.screenfactory.IScreenFactory;
import io.cucumber.java.en.And;
import screens.settings.SettingsScreen;

public class SettingsScreenSteps {
    private final SettingsScreen settingsScreen;

    public SettingsScreenSteps(IScreenFactory screenFactory) {
        settingsScreen = screenFactory.getScreen(SettingsScreen.class);
    }

    @And("I open Accounts on Settings screen")
    public void openAccountsOnSettingsScreen() {
        settingsScreen.openAccounts();
    }
}
