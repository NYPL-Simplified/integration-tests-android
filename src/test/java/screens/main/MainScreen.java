package screens.main;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class MainScreen extends Screen {
    protected MainScreen(By locator) {
        super(locator, "Main");
    }

    public abstract void addALibraryLater();
}
