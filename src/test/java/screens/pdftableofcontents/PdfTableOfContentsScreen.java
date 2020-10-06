package screens.pdftableofcontents;

import aquality.appium.mobile.screens.Screen;
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
}
