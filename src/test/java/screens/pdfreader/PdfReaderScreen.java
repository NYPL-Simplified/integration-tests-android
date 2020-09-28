package screens.pdfreader;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class PdfReaderScreen extends Screen {
    protected PdfReaderScreen(By locator) {
        super(locator, "Pdf reader");
    }

    public abstract String getBookName();

    public abstract int getPageNumber();

    public abstract void swipeFromRightToLeft();

    public abstract void swipeFromLeftToRight();
}
