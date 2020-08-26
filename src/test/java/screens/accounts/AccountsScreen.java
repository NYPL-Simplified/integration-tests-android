package screens.accounts;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class AccountsScreen extends Screen {
    protected AccountsScreen(By locator) {
        super(locator, "Screen");
    }

    public abstract boolean isLibraryPresent(String libraryName);

    public abstract void openAccount(String libraryName);

    public abstract void addAccount();
}
