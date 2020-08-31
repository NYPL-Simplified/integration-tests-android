package screens.bookDetails;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class BookDetailsScreen extends Screen {
    protected BookDetailsScreen(By locator) {
        super(locator, "Book details");
    }

    public abstract void downloadBook();
}
