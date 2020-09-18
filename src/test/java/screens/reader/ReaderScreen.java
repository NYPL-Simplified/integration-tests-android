package screens.reader;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.util.Set;

public abstract class ReaderScreen extends Screen {
    protected ReaderScreen(By locator) {
        super(locator, "Reader");
    }

    public abstract String getBookName();

    public abstract void swipeFromLeftToRight();

    public abstract void swipeFromRightToLeft();

    public abstract void clickLeftCorner();

    public abstract void clickRightCorner();

    public abstract String getPageNumberInfo();

    public abstract Set<String> getListOfChapters();

    public abstract void openChapter(String chapter);
}
