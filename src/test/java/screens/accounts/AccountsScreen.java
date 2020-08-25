package screens.accounts;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;
import screens.maintoolbar.MainToolbar;

public abstract class AccountsScreen extends Screen {
    protected AccountsScreen(By locator) {
        super(locator, "Screen");
    }

    public abstract MainToolbar getToolbar();

    public abstract boolean isLibraryPresent(String libraryName);

    public abstract void openAccount(String libraryName);
}
