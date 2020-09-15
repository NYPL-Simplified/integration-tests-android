package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import framework.configuration.Configuration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;

public class CredentialsSteps {
    private final AccountScreen accountScreen;
    private final AccountsScreen accountsScreen;

    public CredentialsSteps() {
        accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
        accountsScreen = AqualityServices.getScreenFactory().getScreen(AccountsScreen.class);
    }

    @Then("Text on Login button is changed to Log out on Account screen")
    public void textOnLoginButtonIsChangedToLogOutOnAccountScreen() {
        Assert.assertTrue(accountScreen.isLoginSuccessful(), "Text on Login button is not changed to Log out on Account screen");
    }

    @Then("Text on Logout button is changed to Log in on Account screen")
    public void textOnLogoutButtonIsChangedToLogInOnAccountScreen() {
        Assert.assertTrue(accountScreen.isLogoutSuccessful(), "Text on Login button is not changed to Log out on Account screen");
    }


    @When("I click the log out button on the account screen")
    public void clickLogOut() {
        accountScreen.logOut();
    }

    @And("I enter credentials for {string} account")
    public void enterCredentialsForLibraryAccount(String libraryName) {
        accountsScreen.openAccount(libraryName);
        accountScreen.enterCredentials(Configuration.getEbookCardValue(), Configuration.getEbookPinValue());
    }

    @And("I enter credentials for {string} account via keyboard")
    public void enterCredentialsForLibraryAccountViaKeyboard(String libraryName) {
        accountsScreen.openAccount(libraryName);
        accountScreen.enterCredentialsViaKeyboard(Configuration.getEbookCardValue(), Configuration.getEbookPinValue());
    }
}
