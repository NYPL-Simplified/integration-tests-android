package screens.catalog.screen.books.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.AndroidAttributes;
import constants.application.catalog.AndroidBookActionButtonKeys;
import constants.application.timeouts.BooksTimeouts;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;
import screens.catalog.screen.books.CatalogBooksScreen;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@ScreenType(platform = PlatformName.IOS)
public class IosCatalogBooksScreen extends CatalogBooksScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeCollectionView";

    private static final String BOOKS_LOC = ".//XCUIElementTypeCell";
    private static final String BOOK_BLOCK_BY_TITLE_LOC = "//XCUIElementTypeCell"
            + "[.//XCUIElementTypeStaticText[@name=\"%1$s\"]]";
    private static final String BOOK_BLOCK_BY_BUTTON_LOC = "//XCUIElementTypeCell[.//XCUIElementTypeButton[@name=\"%1$s\"]]";

    private static final String BOOK_IMAGE_LOC = "//XCUIElementTypeImage";
    private static final String BOOK_TITLE_LOC = "//XCUIElementTypeStaticText[@name][1]";
    private static final String BOOK_AUTHOR_LOC = "//XCUIElementTypeStaticText[@name][2]";
    private static final String BOOK_TYPE_LOC = ""; // does not exist on ios
    private static final String BOOK_ADD_BUTTON_LOC = "//XCUIElementTypeStaticText[@name=\"%1$s\"]";
    public static final String BOOK_OF_TYPE_WITH_BUTTON_LOCATOR_PATTERN = "";

    private final IButton btnDontAllowNotifications = getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Don’t Allow\"]"),
            "Dont allow notifications");
    private final ILabel lblFirstFoundBook = getElementFactory().getLabel(
            By.xpath(BOOKS_LOC), "First found book");

    public IosCatalogBooksScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void selectFirstFoundBook() {
        lblFirstFoundBook.click();
    }

    private List<ILabel> getFoundBooks() {
        return getElementFactory().findElements(By.xpath(BOOKS_LOC), ElementType.LABEL);
    }

    @Override
    public int getFoundBooksCount() {
        return getFoundBooks().size();
    }

    @Override
    public AndroidCatalogBookModel getBookInfo(final String title) {
        final AndroidCatalogBookModel androidCatalogBookModel = new AndroidCatalogBookModel();
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, title);

        androidCatalogBookModel
                .setImageTitle(Objects.requireNonNull(
                        getElementFactory().getLabel(By.xpath(blockLoc + BOOK_IMAGE_LOC),
                                "Book image content description").getAttribute(AndroidAttributes.CONTENT_DESC)
                ))
                .setTitle(getBookParameter(blockLoc, BOOK_TITLE_LOC, "Book title"))
                .setAuthor(getBookParameter(blockLoc, BOOK_AUTHOR_LOC, "Book author"))
                .setBookType(getBookParameter(blockLoc, BOOK_TYPE_LOC, "Book type"));
        return androidCatalogBookModel;
    }

    private String getBookParameter(String mainLocator, String subLocator, String name) {
        return Objects.requireNonNull(
                getElementFactory().getLabel(By.xpath(mainLocator + subLocator), name)
                        .getText());
    }


    @Override
    public AndroidCatalogBookModel scrollToTheBookAndClickAddButton(AndroidBookActionButtonKeys bookAddButtonKey) {
        return performActionOnBook(bookAddButtonKey);
    }

    @Override
    public void clickTheBookByTitleBtnWithKey(String title, AndroidBookActionButtonKeys key) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
        final IButton bookAddBtn = getElementFactory().getButton(
                By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC,
                        key.getKey())), String.format("Book %1$s button", key.getKey()));
        clickOnTheSpecificBookElement(bookAddBtn);

        if (btnDontAllowNotifications.state().waitForDisplayed()) {
            btnDontAllowNotifications.click();
        }
    }

    @Override
    public void openBookDetailsWithAction(AndroidBookActionButtonKeys action) {
        clickOnTheSpecificBookElement(getBookJacketWithGivenButtonLabel(action));
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(String bookTitle, AndroidBookActionButtonKeys key) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, bookTitle);
        final IButton bookAddBtn = getElementFactory().getButton(
                By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC, key.getKey())),
                String.format("Book %1$s button", key.getKey()));
        return bookAddBtn.state().waitForDisplayed(
                Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
    }

    @Override
    public AndroidCatalogBookModel scrollToTheBookAndClickAddButton(AndroidBookActionButtonKeys actionButtonKey, String bookType) {
        String key = actionButtonKey.getKey();
        IButton button = getElementFactory().getButton(By.xpath(getBookAddButtonLocatorWithGivenType(actionButtonKey, bookType)), key);
        if (!button.state().isDisplayed()) {
            button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        }
        String bookTitle =
                getElementFactory().getButton(By.xpath("" + getBookAddButtonLocatorWithGivenType(actionButtonKey, bookType) + ""), key).getText();
        AndroidCatalogBookModel androidCatalogBookModel = getBookInfo(bookTitle);
        button.click();
        return androidCatalogBookModel;
    }

    private AndroidCatalogBookModel performActionOnBook(AndroidBookActionButtonKeys buttonName) {
        IButton button = getAddBookButton(buttonName);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        String bookTitle =
                getBookJacketWithGivenButtonLabel(buttonName).findChildElement(By.xpath(BOOK_TITLE_LOC), ElementType.LABEL).getText();
        AndroidCatalogBookModel androidCatalogBookModel = getBookInfo(bookTitle);
        button.click();

        if (btnDontAllowNotifications.state().waitForDisplayed()) {
            btnDontAllowNotifications.click();
        }
        return androidCatalogBookModel;
    }

    private ILabel getBookJacketWithGivenButtonLabel(AndroidBookActionButtonKeys button) {
        String key = button.getKey();
        return getElementFactory().getLabel(By.xpath(String.format(BOOK_BLOCK_BY_BUTTON_LOC, key)),
                "Book jacket with btn " + key);
    }

    private void clickOnTheSpecificBookElement(IElement bookWithSpecificAddBtn) {
        bookWithSpecificAddBtn.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        bookWithSpecificAddBtn.click();
    }

    private String getBookAddButtonLocatorWithGivenType(AndroidBookActionButtonKeys actionButtonKey, String bookType) {
        String key = actionButtonKey.getKey();
        return String.format(BOOK_OF_TYPE_WITH_BUTTON_LOCATOR_PATTERN, bookType, key);
    }
}
