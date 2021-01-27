package screens.pdfreader.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.Attributes;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.RegEx;
import constants.application.timeouts.PdfTimeouts;
import constants.localization.application.reader.PdfButtons;
import framework.utilities.CoordinatesClickUtils;
import framework.utilities.RegExUtil;
import framework.utilities.swipe.SwipeElementUtils;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import framework.utilities.swipe.directions.EntireScreenDragDirection;
import org.openqa.selenium.By;
import screens.pdfreader.PdfReaderScreen;
import screens.pdftableofcontents.PdfTableOfContentsScreen;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosPdfReaderScreen extends PdfReaderScreen {
    private static final String BUTTON_LOC_PATTERN = "//XCUIElementTypeButton[@name=\"%1$s\"]";

    private final ILabel lblBookName =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeTextView"), "Book Name");
    private final ILabel lblPageNumber =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeOther/XCUIElementTypeStaticText[contains(@value,'/')]"), "Book Page number");
    private final ILabel lblPage = getElementFactory().getLabel(
            By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeTextView"),
            "Book Page");
    private final IButton btnChapters =
            getElementFactory().getButton(By.xpath(String.format(BUTTON_LOC_PATTERN, PdfButtons.CHAPTERS.i18n())), "Table of contents");
    private final IButton btnSearch =
            getElementFactory().getButton(By.xpath(String.format(BUTTON_LOC_PATTERN, PdfButtons.SEARCH.i18n())), "Search btn");

    public IosPdfReaderScreen() {
        super(By.xpath("//XCUIElementTypeScrollView/XCUIElementTypeTextView"));
    }

    @Override
    public String getBookName() {
        return lblBookName.getAttribute(Attributes.VALUE);
    }

    @Override
    public int getPageNumber() {
        if (!lblPageNumber.state().waitForDisplayed()) {
            CoordinatesClickUtils.clickOutOfElement(lblPage); // needed to expand navigation and labels
        }
        return Integer.parseInt(RegExUtil.getStringFromFirstGroup(lblPageNumber.getText(), RegEx.PDF_CURRENT_PAGE_REGEX));
    }

    @Override
    public void goToNextPage() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.RIGHT);
        CoordinatesClickUtils.clickOutOfElement(lblPage); // needed to expand navigation and labels

    }

    @Override
    public void goToPreviousPage() {
        SwipeElementUtils.swipeThroughEntireElement(lblPage, EntireElementSwipeDirection.LEFT);
        CoordinatesClickUtils.clickOutOfElement(lblPage); // needed to expand navigation and labels
    }

    @Override
    public Set<String> getListOfChapters() {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openTableOfContentsInListView();
        AqualityServices.getConditionalWait().waitFor(() -> pdfTableOfContentsScreen.getChaptersCount() > 0,
                Duration.ofMillis(PdfTimeouts.PDF_LIST_OF_CHAPTERS_APPEAR_TIMEOUT.getTimeoutMillis()));
        Set<String> bookNames = pdfTableOfContentsScreen.getListOfBookChapters();
        AqualityServices.getLogger().info(AqualityServices.getApplication().getDriver().getPageSource());
        AqualityServices.getApplication().getDriver().navigate().back();
        AqualityServices.getLogger().info("Found chapters - " + bookNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return bookNames;
    }

    @Override
    public void openChapter(String chapter) {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openTableOfContentsInListView();
        pdfTableOfContentsScreen.openChapter(chapter);
    }

    @Override
    public int getChapterPageNumber(String chapter) {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openTableOfContentsInListView();
        int chapterPageNumber = pdfTableOfContentsScreen.getChapterPageNumber(chapter);
        AqualityServices.getApplication().getDriver().navigate().back();
        return chapterPageNumber;
    }

    @Override
    public void slidePageSlider(EntireScreenDragDirection entireScreenDragDirection) {
        // not implemented on iOS
    }

    @Override
    public PdfTableOfContentsScreen openChaptersGallery() {
        btnChapters.click();
        PdfTableOfContentsScreen pdfTableOfContentsScreen =
                AqualityServices.getScreenFactory().getScreen(PdfTableOfContentsScreen.class);
        pdfTableOfContentsScreen.state().waitForExist();
        return pdfTableOfContentsScreen;
    }

    private PdfTableOfContentsScreen openTableOfContentsInListView() {
        PdfTableOfContentsScreen pdfTableOfContentsScreen = openChaptersGallery();
        pdfTableOfContentsScreen.switchToChaptersListView();
        return pdfTableOfContentsScreen;
    }

    @Override
    public void openSearchPdf() {
        btnSearch.click();
    }
}
