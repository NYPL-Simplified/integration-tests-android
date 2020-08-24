package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import screens.accounts.AccountsScreen;

public class AccountsScreenSteps {
    private final AccountsScreen accountsScreen;

    public AccountsScreenSteps() {
        accountsScreen = AqualityServices.getScreenFactory().getScreen(AccountsScreen.class);
    }

    @And("I click Add button on Accounts screen")
    public void clickAddButton() {
        accountsScreen.getToolbar().addAccount();
    }

    @Then("Account {string} is present on Accounts screen")
    public void checkAccountIsPresent(String libraryName) {
        Assert.assertTrue(accountsScreen.isLibraryPresent(libraryName), libraryName + " is not present on Accounts screen");
    }
}
