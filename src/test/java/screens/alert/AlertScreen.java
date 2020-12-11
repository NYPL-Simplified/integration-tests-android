package screens.alert;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class AlertScreen extends Screen {
    protected AlertScreen(By locator) {
        super(locator, "Alert");
    }

    public abstract void accept();

    public abstract void closeModalIfPresent();

    public abstract void closeNotNowModalIfPresent();
}
