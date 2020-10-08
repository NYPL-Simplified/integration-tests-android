package screens.pdftableofcontents;

import aquality.appium.mobile.screens.Screen;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import org.openqa.selenium.By;

import java.util.Set;

public abstract class PdfTableOfContentsScreen extends Screen {
    protected PdfTableOfContentsScreen(By locator) {
        super(locator, "Table of Contents");
    }

    public abstract void switchToTheChaptersListView();

    public abstract Set<String> getListOfBookChapters();

    public abstract void openChapter(String chapter);

    public abstract int getChapterPageNumber(String chapter);

    public abstract boolean isGalleryPagesLoad();

    public abstract int getCountOfTheBookPages();

    public abstract void scrollGallery(EntireElementSwipeDirection entireElementSwipeDirection);

    public abstract void openGalleryPage(int pageNumber);
}
