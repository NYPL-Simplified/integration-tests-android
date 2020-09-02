package hooks;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.After;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.holds.HoldsScreen;
import screens.settings.SettingsScreen;

public class LogoutHooks {
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
        bottomMenuForm.open(BottomMenu.HOLDS);
        HoldsScreen holdsScreen = AqualityServices.getScreenFactory().getScreen(HoldsScreen.class);
        holdsScreen.cancelReservations();
    }
}
