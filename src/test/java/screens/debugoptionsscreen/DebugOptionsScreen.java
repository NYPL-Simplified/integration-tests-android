package screens.debugoptionsscreen;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class DebugOptionsScreen extends Screen {

    public DebugOptionsScreen(By locator) {
        super(locator, "Debug Screen");
    }

    public abstract void addCustomOpds();
}
