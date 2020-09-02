package screens.bookDetails;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class BookDetailsScreen extends Screen {
    protected BookDetailsScreen(By locator) {
        super(locator, "Book details");
    }

    public abstract void downloadBook();

    public abstract void reserveBook();

    public abstract String getBookInfo();
}
