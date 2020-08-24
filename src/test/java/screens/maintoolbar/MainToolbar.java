package screens.maintoolbar;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class MainToolbar extends Screen {
    protected MainToolbar(By locator) {
        super(locator, "Main Toolbar");
    }

    public abstract void addAccount();
}
