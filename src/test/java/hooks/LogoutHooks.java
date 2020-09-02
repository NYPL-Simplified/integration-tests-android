package hooks;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.After;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.CatalogScreen;
import screens.holds.HoldsScreen;
import screens.settings.SettingsScreen;

import java.util.List;

public class LogoutHooks {
    private ScenarioContext context;

    @Inject
    public LogoutHooks(ScenarioContext context) {
        this.context = context;
    }

    @After(value = "@logout", order = 1)
    public void closeApplication() {
        AccountScreen accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
        if (!accountScreen.state().isDisplayed()) {
            AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class).open(BottomMenu.SETTINGS);
            AqualityServices.getScreenFactory().getScreen(SettingsScreen.class).openAccounts();
            AqualityServices.getScreenFactory().getScreen(AccountsScreen.class).openFirstLibrary();
        }
        accountScreen.logOut();
    }

    @After(value = "@cancelHold", order = 2)
    public void cancelHold() {
        BottomMenuForm bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        List<String> librariesForCancel = context.get("librariesForCancel");
        for (String library : librariesForCancel) {
            bottomMenuForm.open(BottomMenu.CATALOG);
            AqualityServices.getScreenFactory().getScreen(CatalogScreen.class).openLibrary(library);
            bottomMenuForm.open(BottomMenu.HOLDS);
            HoldsScreen holdsScreen = AqualityServices.getScreenFactory().getScreen(HoldsScreen.class);
            holdsScreen.cancelReservations();
        }
    }
}
