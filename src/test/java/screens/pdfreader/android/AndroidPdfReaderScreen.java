package screens.pdfreader.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.logging.Logger;
import framework.utilities.swipe.SwipeElementUtils;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import framework.utilities.swipe.directions.EntireScreenDragDirection;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import screens.pdfreader.PdfReaderScreen;
import screens.pdftableofcontents.PdfTableOfContentsScreen;

import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidPdfReaderScreen extends PdfReaderScreen {
    private final ILabel lblBookName = getElementFactory().getLabel(By.id("title_textView"), "Book Name");
    private final ILabel lblPageNumberSlider =
            getElementFactory().getLabel(By.xpath("//*[contains(@resource-id,\"pdfView\")]//android.widget.TextView"), "Book Page number");
    private final ILabel lblPage = getElementFactory().getLabel(By.xpath("//android.widget.FrameLayout"), "Book Page");
    private final IButton btnChapters = getElementFactory().getButton(By.id("reader_toc"), "Table of contents");

    private Logger logger = AqualityServices.getLogger();

    public AndroidPdfReaderScreen() {
        super(By.id("pdf_reader_hud_container"));
    }

    @Override
    public String getBookName() {
        return lblBookName.getText();
    }

    @Override
    public int getPageNumber() {
        return Integer.parseInt(lblPageNumberSlider.getText());
    }

    @Override
    public void openMenu() {

    }

    @Override
    public void goToNextPage() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.RIGHT);
    }

    @Override
    public void goToPreviousPage() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.LEFT);
    }

    @Override
    public Set<String> getListOfChapters() {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openTableOfContents();
        Set<String> bookNames = pdfTableOfContentsScreen.getListOfBookChapters();
        logger.info(AqualityServices.getApplication().getDriver().getPageSource());
        AqualityServices.getApplication().getDriver().navigate().back();
        logger.info("Found chapters - " + bookNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
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

    @Override
    public void slidePageSlider(EntireScreenDragDirection entireScreenDragDirection) {
        SwipeElementUtils.dragElementThroughEntireScreen(lblPageNumberSlider, entireScreenDragDirection);
    }

    @Override
    public PdfTableOfContentsScreen openChaptersGallery() {
        throw new NotImplementedException();
    }

    @Override
    public void openSearchPdf() {
        throw new NotImplementedException();
    }

    @Override
    public void closeReader() {

    }

    private PdfTableOfContentsScreen openTableOfContents() {
        btnChapters.click();
        PdfTableOfContentsScreen pdfTableOfContentsScreen =
                AqualityServices.getScreenFactory().getScreen(PdfTableOfContentsScreen.class);
        pdfTableOfContentsScreen.state().waitForExist();
        return pdfTableOfContentsScreen;
    }
}
