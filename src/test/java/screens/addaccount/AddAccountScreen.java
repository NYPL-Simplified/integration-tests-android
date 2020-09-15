package screens.addaccount;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class AddAccountScreen extends Screen {
    protected AddAccountScreen(By locator) {
        super(locator, "Add Accounts");
    }

    public abstract void selectLibrary(String libraryName);
}
