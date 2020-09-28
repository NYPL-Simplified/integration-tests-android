package screens.pdfreader.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import framework.utilities.swipe.SwipeElementUtils;
import org.openqa.selenium.By;
import screens.pdfreader.PdfReaderScreen;
import screens.pdftableofcontents.PdfTableOfContentsScreen;

import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosPdfReaderScreen extends PdfReaderScreen {
    private final ILabel lblBookName = getElementFactory().getLabel(By.id(""), "Book Name");
    private final ILabel lblPageNumber = getElementFactory().getLabel(By.xpath(""), "Book Page number");
    private final ILabel lblPage = getElementFactory().getLabel(By.xpath(""), "Book Page");
    private final IButton btnChapters = getElementFactory().getButton(By.id(""), "Table of contents");

    public IosPdfReaderScreen() {
        super(By.xpath(""));
    }

    @Override
    public String getBookName() {
        return lblBookName.getText();
    }

    @Override
    public int getPageNumber() {
        return Integer.parseInt(lblPageNumber.getText());
    }

    @Override
    public void goToNextPage() {
        SwipeElementUtils.swipeFromRightToLeft(lblPage);
    }

    @Override
    public void goToPreviousPage() {
        SwipeElementUtils.swipeFromLeftToRight(lblPage);
    }

    @Override
    public Set<String> getListOfChapters() {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openTableOfContents();
        Set<String> bookNames = pdfTableOfContentsScreen.getListOfBookChapters();
        AqualityServices.getLogger().info(AqualityServices.getApplication().getDriver().getPageSource());
        AqualityServices.getApplication().getDriver().navigate().back();
        AqualityServices.getLogger().info("Found chapters - " + bookNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return bookNames;
    }

    @Override
    public void openChapter(String chapter) {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openTableOfContents();
        pdfTableOfContentsScreen.openChapter(chapter);
    }

    @Override
    public int getChapterPageNumber(String chapter) {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openTableOfContents();
        int chapterPageNumber = pdfTableOfContentsScreen.getChapterPageNumber(chapter);
        AqualityServices.getApplication().getDriver().navigate().back();
        return chapterPageNumber;
    }

    private PdfTableOfContentsScreen openTableOfContents() {
        btnChapters.click();
        PdfTableOfContentsScreen pdfTableOfContentsScreen =
                AqualityServices.getScreenFactory().getScreen(PdfTableOfContentsScreen.class);
        pdfTableOfContentsScreen.state().waitForExist();
        return pdfTableOfContentsScreen;
    }
}
