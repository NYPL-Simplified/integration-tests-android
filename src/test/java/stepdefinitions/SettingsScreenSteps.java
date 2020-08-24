package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import screens.settings.SettingsScreen;

public class SettingsScreenSteps {
    private final SettingsScreen settingsScreen;

    public SettingsScreenSteps() {
        settingsScreen = AqualityServices.getScreenFactory().getScreen(SettingsScreen.class);
    }

    @And("I open Accounts on Settings screen")
    public void openAccountsOnSettingsScreen() {
        settingsScreen.openAccounts();
    }
}
