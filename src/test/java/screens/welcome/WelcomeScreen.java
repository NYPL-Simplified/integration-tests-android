package screens.welcome;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class WelcomeScreen extends Screen {
    protected WelcomeScreen(By locator) {
        super(locator, "Main");
    }

    public abstract void addALibraryLater();
}
