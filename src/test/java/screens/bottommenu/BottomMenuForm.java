package screens.bottommenu;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class BottomMenuForm extends Screen {
    protected BottomMenuForm(By locator) {
        super(locator, "Menu");
    }

    public abstract void open(BottomMenu bottomMenuItem);
}
