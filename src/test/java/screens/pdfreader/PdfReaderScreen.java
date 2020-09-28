package screens.pdfreader;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.util.Set;

public abstract class PdfReaderScreen extends Screen {
    protected PdfReaderScreen(By locator) {
        super(locator, "Pdf reader");
    }

    public abstract String getBookName();

    public abstract int getPageNumber();

    public abstract void goToNextPage();

    public abstract void goToPreviousPage();

    public abstract Set<String> getListOfChapters();

    public abstract void openChapter(String chapter);

    public abstract int getChapterPageNumber(String chapter);
}
