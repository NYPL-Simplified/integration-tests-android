package screens.agegate;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class AgeGateScreen extends Screen {
    protected AgeGateScreen(By locator) {
        super(locator, "Age Gate");
    }

    public abstract void approveAge();
}
