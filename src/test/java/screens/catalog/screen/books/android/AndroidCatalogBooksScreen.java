package screens.catalog.screen.books.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.AndroidAttributes;
import constants.localization.application.catalog.BookActionButtonKeys;
import constants.application.timeouts.BooksTimeouts;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.catalog.screen.books.CatalogBooksScreen;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidCatalogBooksScreen extends CatalogBooksScreen {
    private static final String MAIN_ELEMENT = "//android.widget.TextView[contains(@content-desc, "
            + "concat(\"Search in \", "
            + "//*[contains(@resource-id,\"mainToolbar\")]/android.widget.TextView[1]/@text))]";

    private static final String ADD_BOOK_BUTTON_PATTERN = "//android.widget.Button[@content-desc=\"%1$s\"]";

    private static final String BOOKS_LOC = ".//*[contains(@resource-id,\"bookCellIdle\")]";
    private static final String BOOK_BLOCK_BY_TITLE_LOC = "//*[contains(@resource-id,\"bookCellIdle\") "
            + "and .//*[contains(@resource-id,\"bookCellIdleTitle\") and contains(@text, '%1$s')]]";

    private static final String BOOK_IMAGE_LOC = "//*[contains(@resource-id,\"bookCellIdleCover\")]";
    private static final String BOOK_TITLE_LOC = "//*[contains(@resource-id,\"bookCellIdleTitle\")]";
    private static final String BOOK_AUTHOR_LOC = "//*[contains(@resource-id,\"bookCellIdleAuthor\")]";
    private static final String BOOK_TYPE_LOC = "//*[contains(@resource-id,\"bookCellIdleMeta\")]";
    private static final String BOOK_ADD_BUTTON_LOC =
            "//*[contains(@resource-id,\"bookCellIdleButtons\")]/android.widget.Button[@content-desc=\"%1$s\"]";
    public static final String BOOK_OF_TYPE_BUTTON_PATTERN =
            "//android.widget.TextView[contains(@resource-id,\"bookCellIdleMeta\") and @text=\"%1$s\"]/following-sibling::android.widget.LinearLayout/android.widget.Button[@content-desc=\"%2$s\"]";

    private final ILabel lblFirstFoundBook = getElementFactory().getLabel(
            By.xpath(BOOKS_LOC), "First found book");
    private String RELATIVE_BOOK_TITLE_LOCATOR_PATTERN =
            "//android.view.ViewGroup[%s]//*[contains(@resource-id,\"bookCellIdle\")]";

    public AndroidCatalogBooksScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public CatalogBookModel selectFirstFoundBook() {
        CatalogBookModel catalogBookModel = getBookModel(BOOKS_LOC);
        lblFirstFoundBook.click();
        return catalogBookModel;
    }

    private List<ILabel> getFoundBooks() {
        return getElementFactory().findElements(By.xpath(BOOKS_LOC), ElementType.LABEL);
    }

    @Override
    public int getFoundBooksCount() {
        return getFoundBooks().size();
    }

    @Override
    public CatalogBookModel getBookInfo(final String title) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
        return getBookModel(blockLoc);
    }


    private CatalogBookModel getBookModel(String mainLocator) {
        return new CatalogBookModel()
                .setImageTitle(Objects.requireNonNull(
                        getElementFactory().getLabel(By.xpath(mainLocator + BOOK_IMAGE_LOC),
                                "Book image content description").getAttribute(AndroidAttributes.CONTENT_DESC)
                ))
                .setTitle(getBookParameter(mainLocator, BOOK_TITLE_LOC, "Book title"))
                .setAuthor(getBookParameter(mainLocator, BOOK_AUTHOR_LOC, "Book author"))
                .setBookType(getBookParameter(mainLocator, BOOK_TYPE_LOC, "Book type"));
    }

    private String getBookParameter(String mainLocator, String subLocator, String name) {
        return Objects.requireNonNull(
                getElementFactory().getLabel(By.xpath(mainLocator + subLocator), name)
                        .getText());
    }


    @Override
    public CatalogBookModel scrollToTheBookAndClickAddButton(BookActionButtonKeys bookAddButtonKey) {
        return performActionOnBook(bookAddButtonKey);
    }

    @Override
    public void clickTheBookByTitleBtnWithKey(String title, BookActionButtonKeys key) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
        final IButton bookAddBtn = getElementFactory().getButton(
                By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC,
                        key.i18n())), String.format("Book %1$s button", key.i18n()));
        clickOnTheSpecificBookElement(bookAddBtn);
    }

    @Override
    public void openBookDetailsWithAction(BookActionButtonKeys action) {
        clickOnTheSpecificBookElement(getBookJacketWithGivenButtonLabel(action));
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, bookTitle);
        final IButton bookAddBtn = getElementFactory().getButton(
                By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC, key.i18n())),
                String.format("Book %1$s button", key.i18n()));
        return bookAddBtn.state().waitForDisplayed(
                Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
    }

    @Override
    public CatalogBookModel scrollToTheBookAndClickAddButton(BookActionButtonKeys actionButtonKey, String bookType) {
        String key = actionButtonKey.i18n();
        IButton button = getElementFactory().getButton(By.xpath(getBookAddButtonLocatorWithGivenType(actionButtonKey, bookType)), key);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);

        String bookTitle =
                getElementFactory().getButton(By.xpath(String.format(RELATIVE_BOOK_TITLE_LOCATOR_PATTERN, getBookAddButtonLocatorWithGivenType(actionButtonKey, bookType))), key).getText();
        CatalogBookModel androidCatalogBookModel = getBookInfo(bookTitle);
        button.click();
        return androidCatalogBookModel;
    }

    private CatalogBookModel performActionOnBook(BookActionButtonKeys buttonName) {
        IButton button = getAddBookButton(buttonName);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        String bookTitle =
                getBookJacketWithGivenButtonLabel(buttonName).findChildElement(By.xpath(BOOK_TITLE_LOC), ElementType.LABEL).getText();
        CatalogBookModel catalogBookModel = getBookInfo(bookTitle);
        button.click();
        return catalogBookModel;
    }

    private ILabel getBookJacketWithGivenButtonLabel(BookActionButtonKeys button) {
        String key = button.i18n();
        return getElementFactory().getLabel(By.xpath(String.format("//*[contains(@resource-id,\"bookCellIdle\") " + "and .//android.widget.Button[@content-desc=\"%1$s\"]]", key)), "Book jacket with" + key);
    }

    private void clickOnTheSpecificBookElement(IElement bookWithSpecificAddBtn) {
        bookWithSpecificAddBtn.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        bookWithSpecificAddBtn.click();
    }

    private IButton getAddBookButton(BookActionButtonKeys button) {
        String key = button.i18n();
        return getElementFactory().getButton(By.xpath(String.format(ADD_BOOK_BUTTON_PATTERN, key)), key);
    }

    private String getBookAddButtonLocatorWithGivenType(BookActionButtonKeys actionButtonKey, String bookType) {
        String key = actionButtonKey.i18n();
        return String.format(BOOK_OF_TYPE_BUTTON_PATTERN, bookType, key);
    }
}
