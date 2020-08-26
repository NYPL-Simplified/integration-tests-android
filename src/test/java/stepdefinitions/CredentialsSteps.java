package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import framework.configuration.Configuration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
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

    @And("I enter credentials for {string} account")
    public void enterCredentialsForLibraryAccount(String libraryName) {
        accountsScreen.openAccount(libraryName);
        accountScreen.enterCredentials(Configuration.getEbookCardValue(), Configuration.getEbookPinValue());
    }
}
