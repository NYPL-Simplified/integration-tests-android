package screens.pdfreader;

import aquality.appium.mobile.screens.Screen;
import framework.utilities.swipe.directions.EntireScreenDragDirection;
import org.openqa.selenium.By;
import screens.pdftableofcontents.PdfTableOfContentsScreen;

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

    public abstract void slidePageSlider(EntireScreenDragDirection entireScreenDragDirection);

    public abstract PdfTableOfContentsScreen openChaptersGallery();

    public abstract void openSearchPdf();
}
