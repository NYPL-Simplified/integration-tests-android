package screens.reader;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class ReaderScreen extends Screen {
    protected ReaderScreen(By locator) {
        super(locator, "Reader");
    }

    public abstract String getBookName();
}
