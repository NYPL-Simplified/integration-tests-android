package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.accounts.AccountsScreen;
import screens.addaccount.AddAccountScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.settings.SettingsScreen;

public class AccountSteps {
    private final AccountsScreen accountsScreen;
    private final BottomMenuForm bottomMenuForm;
    private final SettingsScreen settingsScreen;
    private final AddAccountScreen addAccountScreen;

    public AccountSteps() {
        accountsScreen = AqualityServices.getScreenFactory().getScreen(AccountsScreen.class);
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        settingsScreen = AqualityServices.getScreenFactory().getScreen(SettingsScreen.class);
        addAccountScreen = AqualityServices.getScreenFactory().getScreen(AddAccountScreen.class);
    }

    @When("I add {string} account")
    public void addAccount(String libraryName) {
        bottomMenuForm.open(BottomMenu.SETTINGS);
        settingsScreen.openAccounts();
        accountsScreen.addAccount();
        addAccountScreen.selectLibrary(libraryName);
    }

    @Then("Account {string} is present on Accounts screen")
    public void checkAccountIsPresent(String libraryName) {
        Assert.assertTrue(accountsScreen.isLibraryPresent(libraryName), libraryName + " is not present on Accounts screen");
    }
}
