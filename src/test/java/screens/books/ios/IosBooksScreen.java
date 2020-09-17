package screens.books.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.swipe.SwipeElementUtils;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.books.BooksScreen;

import java.util.List;

@ScreenType(platform = PlatformName.IOS)
public class IosBooksScreen extends BooksScreen {
    private static final String MAIN_ELEMENT_LOC = "//XCUIElementTypeButton[@name=\"All\"]";

    private static final String BOOKS_LOC = "//XCUIElementTypeCollectionView//XCUIElementTypeCell";

    private static final String BOOK_IMAGE_LOC = ""; // does not contain text on the ios
    private static final String BOOK_TYPE_LOC = ""; // does not exist on the ios
    private static final String BOOK_ACTION_BUTTON_LOC = "//XCUIElementTypeButton[@name=\"%1$s\"]\n";

    private static final String BOOKS_WITH_ACTION_LOC = String.format(
            "//XCUIElementTypeCollectionView//XCUIElementTypeCell[.%1$s]", BOOK_ACTION_BUTTON_LOC);
    public static final String BOOK_INFO_BUTTON_PATTERN = "";

    private final ILabel mainBooksElementColleciton = getElementFactory().getLabel(
            By.xpath("//XCUIElementTypeCollectionView"), "Elements collection container");
    private final ILabel lblNoBooks = getElementFactory().getLabel(
            By.xpath("//XCUIElementTypeStaticText[@name=\"Visit the Catalog to add books to My Books.\"]"),
            "No Books Present");
    private final String BOOK_INFO_LOCATOR_PATTERN = "//XCUIElementTypeStaticText[@name=\"%1$s\"]\n";
    private final List<IElement> booksList = getElementFactory().findElements(
            By.xpath("//XCUIElementTypeCollectionView//XCUIElementTypeCell"),
            ElementType.LABEL);

    public IosBooksScreen() {
        super(By.xpath(MAIN_ELEMENT_LOC));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookPresent(CatalogBookModel bookInfo) {
        ILabel book = getElementFactory()
                .getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo.getImageTitle())),
                        "No Books Present");
        return book
                .state()
                .waitForDisplayed();
    }

    @Override
    public int getCountOfBooks() {
        return booksList.size();
    }

    @Override
    public int getCountOfBooksWithAction(BookActionButtonKeys actionKey) {
        return getElementFactory().findElements(
                By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, actionKey)), ElementType.LABEL).size();
    }

    @Override
    public void openBookPage(int index, BookActionButtonKeys actionKey) {
        getElementFactory()
                .findElements(By.xpath(String.format(BOOKS_WITH_ACTION_LOC, actionKey)), ElementType.BUTTON)
                .get(index)
                .click();
    }

    @Override
    public void refreshList() {
        SwipeElementUtils.swipeElementDown(mainBooksElementColleciton);
    }

    @Override
    public void readBook(CatalogBookModel bookInfo) {
        String readButtonName = BookActionButtonKeys.READ.i18n();
        getElementFactory().getButton(By.xpath(String.format(BOOK_INFO_BUTTON_PATTERN, String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo.getImageTitle()), readButtonName)), readButtonName).click();
    }
}
