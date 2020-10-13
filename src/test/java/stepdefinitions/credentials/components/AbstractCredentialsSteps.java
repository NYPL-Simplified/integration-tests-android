package stepdefinitions.credentials.components;

import aquality.appium.mobile.application.AqualityServices;
import constants.context.ContextLibrariesKeys;
import framework.configuration.Configuration;
import framework.utilities.ScenarioContext;
import org.testng.Assert;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;
import screens.subcategory.SubcategoryScreen;
import stepdefinitions.BaseSteps;

public abstract class AbstractCredentialsSteps extends BaseSteps implements ICredentialsSteps {
    protected final AccountScreen accountScreen;
    private final AccountsScreen accountsScreen;
    protected final SubcategoryScreen subcategoryScreen;
    private ScenarioContext context;

    public AbstractCredentialsSteps(ScenarioContext context) {
        this.context = context;
        accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
        accountsScreen = AqualityServices.getScreenFactory().getScreen(AccountsScreen.class);
        subcategoryScreen = AqualityServices.getScreenFactory().getScreen(SubcategoryScreen.class);
    }

    public void textOnLogoutButtonIsChangedToLogInOnAccountScreen() {
        Assert.assertTrue(accountScreen.isLogoutSuccessful(), "Text on Login button is not changed to Log out on Account screen");
    }

    public void clickLogOut() {
        accountScreen.logOut();
    }

    public void enterCredentialsForLibraryAccount(String libraryName) {
        accountsScreen.openAccount(libraryName);
        accountScreen.enterCredentials(Configuration.getCredentials(libraryName));
        context.add(ContextLibrariesKeys.LOG_OUT.getKey(), libraryName);
    }

    public void enterCredentialsForLibraryAccountViaKeyboard(String libraryName) {
        accountsScreen.openAccount(libraryName);
        accountScreen.enterCredentialsViaKeyboard(Configuration.getCredentials(libraryName));
    }
}
