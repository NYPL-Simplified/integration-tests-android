package screens.reader;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class ReaderScreen extends Screen {
    protected ReaderScreen(By locator) {
        super(locator, "Reader");
    }

    public abstract String getBookName();

    public abstract int getPageNumber();

    public abstract void scrollForBookStart();

    public abstract void swipeFromLeftToRight();

    public abstract void swipeFromRightToLeft();

    public abstract void clickLeftCorner();

    public abstract void clickRightCorner();

    public abstract String getPageNumberInfo();
}
