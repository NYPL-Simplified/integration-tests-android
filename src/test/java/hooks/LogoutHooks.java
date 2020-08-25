package hooks;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.After;
import screens.account.AccountScreen;

public class LogoutHooks {
    @After(value = "@logout", order = 1)
    public void closeApplication() {
        AccountScreen accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
        if (accountScreen.state().isDisplayed()) {
            accountScreen.logOut();
        }
    }
}
