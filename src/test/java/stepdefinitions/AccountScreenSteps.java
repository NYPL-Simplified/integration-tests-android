package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import framework.configuration.Configuration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import screens.account.AccountScreen;

public class AccountScreenSteps {
    private final AccountScreen accountScreen;

    public AccountScreenSteps() {
        accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
    }

    @And("I enter credentials for ebook user on Account screen")
    public void iEnterCredentialsForUserOnAccountScreen() {
        accountScreen.enterCredentials(Configuration.getEbookCardValue(), Configuration.getEbookPinValue());
    }

    @Then("Text on Login button is changed to Log out on Account screen")
    public void textOnLoginButtonIsChangedToLogOutOnAccountScreen() {
        Assert.assertTrue(accountScreen.isLoginSuccessful(), "Text on Login button is not changed to Log out on Account screen");
    }
}
