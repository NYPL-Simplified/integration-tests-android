package screens.settings;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class SettingsScreen extends Screen {
    protected SettingsScreen(By locator) {
        super(locator, "Settings");
    }

    public abstract void openAccounts();
}
