package screens.books.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.swipe.SwipeElementUtils;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.books.BooksScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosBooksScreen extends BooksScreen {
    private static final String MAIN_ELEMENT_LOC = "//XCUIElementTypeButton[@name=\"All\"]";
    private static final String BOOK_ACTION_BUTTON_LOC = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final String BOOK_INFO_LOCATOR_PATTERN = "//XCUIElementTypeStaticText[@name=\"%1$s\"]";

    private static final String BOOKS_WITH_ACTION_LOC = String.format(
            "//XCUIElementTypeCollectionView//XCUIElementTypeCell[.%1$s]", BOOK_ACTION_BUTTON_LOC);
    private static final String BOOKS_BY_TITLE_LOC = String.format(
            "//XCUIElementTypeCollectionView//XCUIElementTypeCell[.%1$s]", BOOK_INFO_LOCATOR_PATTERN);
    public static final String BOOKS_LABELS_XPATH = "//XCUIElementTypeCollectionView//XCUIElementTypeCell";

    private final ILabel mainBooksElementCollection = getElementFactory().getLabel(
            By.xpath("//XCUIElementTypeCollectionView"), "Elements collection container");
    private final ILabel lblNoBooks =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[contains(@name,'Visit the Catalog')]"), "No Books Present");
    private final ILabel lblScreen = getElementFactory().getLabel(By.xpath("//XCUIElementTypeCollectionView"), "Screen");

    public IosBooksScreen() {
        super(By.xpath(MAIN_ELEMENT_LOC));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().waitForDisplayed();
    }

    @Override
    public boolean isBookPresent(CatalogBookModel bookInfo) {
        ILabel book = getElementFactory()
                .getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo.getTitle())),
                        "No Books Present");
        book.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return book
                .state()
                .waitForDisplayed();
    }

    @Override
    public int getCountOfBooks() {
        return getBooks().size();
    }

    @Override
    public int getCountOfBooksWithAction(BookActionButtonKeys actionKey) {
        return getElementFactory().findElements(
                By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, actionKey.i18n())), ElementType.LABEL).size();
    }

    @Override
    public void openBookPage(int index, BookActionButtonKeys actionKey) {
        getElementFactory()
                .findElements(By.xpath(String.format(BOOKS_WITH_ACTION_LOC, actionKey.i18n())), ElementType.BUTTON)
                .get(index)
                .click();
    }

    @Override
    public void refreshList() {
        SwipeElementUtils.swipeElementDown(mainBooksElementCollection);
    }

    @Override
    public void readBook(CatalogBookModel bookInfo) {
        String readButtonName = BookActionButtonKeys.READ.i18n();
        IButton btnBookName =
                getElementFactory().getButton(By.xpath(String.format(BOOKS_BY_TITLE_LOC, bookInfo.getTitle())), "The book " + bookInfo.getTitle());
        btnBookName.state().waitForDisplayed();
        btnBookName.findChildElement(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, readButtonName)), ElementType.BUTTON).click();
    }

    @Override
    public void scrollForButtonWithAction(BookActionButtonKeys actionButton) {
        String buttonName = actionButton.i18n();
        ILabel label = getElementFactory().getLabel(By.xpath(String.format(BOOKS_WITH_ACTION_LOC, buttonName)), buttonName);
        if (!label.state().isDisplayed()) {
            List<String> currentBooksNames = getBookNames();
            Set<String> bookNames = new HashSet<>();
            do {
                bookNames.addAll(currentBooksNames);
                SwipeElementUtils.swipeElementUp(lblScreen);
                currentBooksNames = getBookNames();
            } while (!bookNames.containsAll(currentBooksNames));
        }
    }

    private List<String> getBookNames() {
        return getBooks().stream().map(aquality.selenium.core.elements.interfaces.IElement::getText).collect(Collectors.toList());
    }

    private List<IElement> getBooks() {
        return getElementFactory().findElements(By.xpath(BOOKS_LABELS_XPATH), ElementType.LABEL);
    }
}
