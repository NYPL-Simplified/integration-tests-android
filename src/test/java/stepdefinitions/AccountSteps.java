package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;
import screens.addaccount.AddAccountScreen;
import screens.alert.AlertScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.settings.SettingsScreen;

public class AccountSteps {
    private final AccountsScreen accountsScreen;
    private final BottomMenuForm bottomMenuForm;
    private final SettingsScreen settingsScreen;
    private final AddAccountScreen addAccountScreen;
    private final AlertScreen alertScreen;
    private final AccountScreen accountScreen;

    public AccountSteps() {
        accountsScreen = AqualityServices.getScreenFactory().getScreen(AccountsScreen.class);
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        settingsScreen = AqualityServices.getScreenFactory().getScreen(SettingsScreen.class);
        addAccountScreen = AqualityServices.getScreenFactory().getScreen(AddAccountScreen.class);
        alertScreen = AqualityServices.getScreenFactory().getScreen(AlertScreen.class);
        accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
    }

    @When("I add {string} account")
    public void addAccount(String libraryName) {
        bottomMenuForm.open(BottomMenu.SETTINGS);
        settingsScreen.openAccounts();
        accountsScreen.addAccount();
        Assert.assertTrue(addAccountScreen.state().waitForDisplayed(),
                "Checking that add accounts screen visible");
        addAccountScreen.selectLibrary(libraryName);
    }

    @Then("Account {string} is present on Accounts screen")
    public void checkAccountIsPresent(String libraryName) {
        if (accountScreen.state().isDisplayed()) {
            AqualityServices.getApplication().getDriver().navigate().back();
        }
        Assert.assertTrue(accountsScreen.isLibraryPresent(libraryName), libraryName + " is not present on Accounts screen");
    }

    @Then("Account {string} is not present on Accounts screen")
    public void checkAccountIsNotPresent(String libraryName) {
        Assert.assertFalse(accountsScreen.isLibraryPresent(libraryName), libraryName + " is present on Accounts screen");
    }

    @When("I remove {string} account")
    public void removeAccount(String libraryName) {
        accountsScreen.deleteLibrary(libraryName);
        alertScreen.state().waitForExist();
        alertScreen.accept();
        bottomMenuForm.open(BottomMenu.SETTINGS);
        settingsScreen.openAccounts();
    }

    @When("I open account {string}")
    public void openAccount(String libraryName) {
        bottomMenuForm.open(BottomMenu.SETTINGS);
        bottomMenuForm.open(BottomMenu.SETTINGS);
        settingsScreen.openAccounts();
        accountsScreen.openAccount(libraryName);
    }
}
