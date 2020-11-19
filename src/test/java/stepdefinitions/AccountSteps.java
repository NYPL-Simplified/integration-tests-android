package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.context.ContextLibrariesKeys;
import framework.configuration.Configuration;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;
import screens.addaccount.AddAccountScreen;
import screens.addcustomopdsfeed.AddCustomOpdsFeedScreen;
import screens.agegate.AgeGateScreen;
import screens.alert.AlertScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.form.MainCatalogToolbarForm;
import screens.catalog.screen.catalog.CatalogScreen;
import screens.debugoptionsscreen.DebugOptionsScreen;
import screens.settings.SettingsScreen;

public class AccountSteps {
    private final AccountsScreen accountsScreen;
    private final BottomMenuForm bottomMenuForm;
    private final SettingsScreen settingsScreen;
    private final AddAccountScreen addAccountScreen;
    private final AlertScreen alertScreen;
    private final AccountScreen accountScreen;
    private final DebugOptionsScreen debugOptionsScreen;
    private final AddCustomOpdsFeedScreen addCustomOpdsScreen;
    private final AgeGateScreen ageGateScreen;
    private final MainCatalogToolbarForm mainCatalogToolbarForm;
    private final CatalogScreen catalogScreen;
    private ScenarioContext context;

    @Inject
    public AccountSteps(ScenarioContext context) {
        this.context = context;
        accountsScreen = AqualityServices.getScreenFactory().getScreen(AccountsScreen.class);
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        settingsScreen = AqualityServices.getScreenFactory().getScreen(SettingsScreen.class);
        addAccountScreen = AqualityServices.getScreenFactory().getScreen(AddAccountScreen.class);
        alertScreen = AqualityServices.getScreenFactory().getScreen(AlertScreen.class);
        accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
        debugOptionsScreen = AqualityServices.getScreenFactory().getScreen(DebugOptionsScreen.class);
        addCustomOpdsScreen = AqualityServices.getScreenFactory().getScreen(AddCustomOpdsFeedScreen.class);
        ageGateScreen = AqualityServices.getScreenFactory().getScreen(AgeGateScreen.class);
        mainCatalogToolbarForm = AqualityServices.getScreenFactory().getScreen(MainCatalogToolbarForm.class);
        catalogScreen = AqualityServices.getScreenFactory().getScreen(CatalogScreen.class);
    }

    @When("I add {string} account")
    public void addAccount(String libraryName) {
        openAccounts();
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
        openAccounts();
    }

    @When("I open account {string}")
    public void openAccount(String libraryName) {
        bottomMenuForm.open(BottomMenu.SETTINGS);
        openAccounts();
        accountsScreen.openAccount(libraryName);
    }

    private void openAccounts() {
        bottomMenuForm.open(BottomMenu.SETTINGS);
        settingsScreen.openAccounts();
    }

    @When("I add custom {string} odps feed")
    public void iAddCustomTheNewYorkPublicLibraryOdpsFeed(String feedName) {
        bottomMenuForm.open(BottomMenu.SETTINGS);
        Assert.assertTrue(settingsScreen.openDebugButton(), "Feed name wasn't opened");
        settingsScreen.openDebugMode();
        debugOptionsScreen.addCustomOpds();
        addCustomOpdsScreen.enterOpds(Configuration.getOpds(feedName));
        bottomMenuForm.open(BottomMenu.SETTINGS);
        bottomMenuForm.open(BottomMenu.CATALOG);
        if (ageGateScreen.state().waitForDisplayed()) {
            ageGateScreen.approveAge();
        }
        mainCatalogToolbarForm.chooseAnotherLibrary();
        catalogScreen.openLibrary(feedName);
        accountScreen.enterCredentials(Configuration.getCredentials(feedName));
        context.add(ContextLibrariesKeys.LOG_OUT.getKey(), feedName);
        settingsScreen.state().waitForDisplayed();
        bottomMenuForm.open(BottomMenu.CATALOG);
    }
}
