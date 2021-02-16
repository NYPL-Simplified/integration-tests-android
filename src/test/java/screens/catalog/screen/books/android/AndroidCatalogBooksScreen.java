package screens.catalog.screen.books.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.attributes.AndroidAttributes;
import constants.application.timeouts.BooksTimeouts;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.swipe.SwipeElementUtils;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.catalog.screen.books.CatalogBooksScreen;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidCatalogBooksScreen extends CatalogBooksScreen {
    private static final String ADD_BOOK_BUTTON_PATTERN = "//android.widget.Button[@content-desc=\"%1$s\"]";
    private static final String BOOKS_LOC = ".//*[contains(@resource-id,\"bookCellIdle\")]";
    private static final String BOOK_BLOCK_BY_TITLE_LOC =
            "//*[contains(@resource-id,\"bookCellIdle\") and .//*[contains(@resource-id,\"bookCellIdleTitle\") and contains(@text, \"%1$s\")]]";
    private static final String BOOK_TITLE_LOC = "//*[contains(@resource-id,\"bookCellIdleTitle\")]";
    private static final String BOOK_AUTHOR_LOC = "//*[contains(@resource-id,\"bookCellIdleAuthor\")]";
    private static final String BOOK_TYPE_LOC = "//*[contains(@resource-id,\"bookCellIdleMeta\")]";
    private static final String BOOK_ADD_BUTTON_LOC =
            "//*[contains(@resource-id,\"bookCellIdleButtons\")]/android.widget.Button[@content-desc=\"%1$s\"]";
    private static final String BOOK_OF_TYPE_BUTTON_PATTERN =
            "//android.widget.TextView[contains(@resource-id,\"bookCellIdleMeta\") and @text=\"%1$s\"]"
                    + "/following-sibling::android.widget.LinearLayout/android.widget.Button[@content-desc=\"%2$s\"]";
    private static final String BOOK_BY_NAME_BUTTON_PATTERN =
            "//android.widget.TextView[contains(@resource-id,\"bookCellIdleTitle\") and @text=\"%1$s\"]/following-sibling::android.widget.LinearLayout/android.widget.Button[@content-desc=\"%2$s\"]";
    private static final String BOOK_COVER_LOCATOR = "//*[contains(@resource-id,\"bookCellIdleCover\")]";
    private static final String BOOK_JACKET_XPATH_PATTERN =
            "//*[contains(@resource-id,\"bookCellIdle\") and .//android.widget.Button[@content-desc=\"%1$s\"]]";
    private String RELATIVE_BOOK_TITLE_LOCATOR_PATTERN =
            "%s/../preceding-sibling::android.widget.TextView[contains(@resource-id,\"bookCellIdleTitle\")]";

    private final ILabel lblFirstFoundBook = getElementFactory().getLabel(By.xpath(BOOKS_LOC), "First found book");
    private final ILabel lblErrorDetails = getElementFactory().getLabel(By.id("errorDetails"), "Error details");
    private final IButton btnErrorDetails = getElementFactory().getButton(By.id("bookCellErrorButtonDetails"), "Error details");

    public AndroidCatalogBooksScreen() {
        super(By.id("feedWithGroups"));
    }

    @Override
    public CatalogBookModel selectFirstFoundBook() {
        CatalogBookModel catalogBookModel = getBookModel(BOOKS_LOC);
        lblFirstFoundBook.click();
        return catalogBookModel;
    }

    @Override
    public int getFoundBooksCount() {
        return getFoundBooks().size();
    }

    @Override
    public CatalogBookModel getBookInfo(final String title) {
        return getBookModel(getBlockLocator(title));
    }

    @Override
    public CatalogBookModel scrollToBookAndClickActionButton(BookActionButtonKeys bookAddButtonKey) {
        return performActionOnBook(bookAddButtonKey);
    }

    @Override
    public void clickBookByTitleButtonWithKey(String title, BookActionButtonKeys key) {
        String buttonName = key.i18n();
        IButton bookActionBtn =
                getElementFactory().getButton(By.xpath(getBlockLocator(title) + String.format(BOOK_ADD_BUTTON_LOC, buttonName)), buttonName);
        clickOnSpecificBookElement(bookActionBtn);
        bookActionBtn.state().waitForNotDisplayed();
    }

    @Override
    public void openBookWithGivenActionButtonDetails(BookActionButtonKeys action) {
        clickOnSpecificBookElement(getBookJacketWithGivenButtonLabel(action));
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key) {
        String buttonName = key.i18n();
        IButton btnBook =
                getElementFactory().getButton(By.xpath(getBookActionButtonLocatorWithGivenName(key, bookTitle)), buttonName);
        AqualityServices.getConditionalWait().waitFor(() ->
                btnBook.state().isDisplayed() || btnErrorDetails.state().isDisplayed(), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        return btnBook.state().isDisplayed();
    }

    @Override
    public CatalogBookModel scrollToBookByTypeAndClickAddButton(BookActionButtonKeys actionButtonKey, String bookType) {
        String key = actionButtonKey.i18n();
        String bookAddButtonLocator = getBookAddButtonLocatorWithGivenType(actionButtonKey, bookType);
        IButton button = getElementFactory().getButton(By.xpath(bookAddButtonLocator), key);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);

        String bookTitle =
                getElementFactory().getButton(By.xpath(String.format(RELATIVE_BOOK_TITLE_LOCATOR_PATTERN, bookAddButtonLocator)), key).getText();
        return openBook(button, bookTitle);
    }

    @Override
    public CatalogBookModel scrollToBookByNameAndClickAddButton(BookActionButtonKeys actionButtonKey, String bookName) {
        String bookAddButtonLocator = getBookActionButtonLocatorWithGivenName(actionButtonKey, bookName);
        IButton button = getElementFactory().getButton(By.xpath(bookAddButtonLocator), actionButtonKey.i18n());
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return openBook(button, bookName);
    }

    @Override
    public String getErrorMessage() {
        if (isErrorButtonPresent()) {
            btnErrorDetails.click();
            lblErrorDetails.state().waitForDisplayed();
            return lblErrorDetails.getText();
        }
        AqualityServices.getLogger().info("Error details button is not present");
        return "";
    }

    @Override
    public boolean isErrorButtonPresent() {
        return btnErrorDetails.state().isDisplayed();
    }

    private CatalogBookModel performActionOnBook(BookActionButtonKeys buttonName) {
        SwipeElementUtils.swipeElementDown(lblFirstFoundBook);
        IButton button = getAddBookButton(buttonName);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        String bookTitle =
                getBookJacketWithGivenButtonLabel(buttonName).findChildElement(By.xpath(BOOK_TITLE_LOC), ElementType.LABEL).getText();
        return openBook(button, bookTitle);
    }

    private ILabel getBookJacketWithGivenButtonLabel(BookActionButtonKeys button) {
        String key = button.i18n();
        return getElementFactory().getLabel(By.xpath(String.format(BOOK_JACKET_XPATH_PATTERN, key)), "Book jacket with " + key);
    }

    private void clickOnSpecificBookElement(IElement bookWithSpecificAddBtn) {
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

    private String getBookActionButtonLocatorWithGivenName(BookActionButtonKeys actionButtonKey, String bookName) {
        String key = actionButtonKey.i18n();
        return String.format(BOOK_BY_NAME_BUTTON_PATTERN, bookName, key);
    }

    private CatalogBookModel openBook(IButton button, String bookTitle) {
        CatalogBookModel androidCatalogBookModel = getBookInfo(bookTitle);
        button.click();
        return androidCatalogBookModel;
    }

    private List<ILabel> getFoundBooks() {
        return getElementFactory().findElements(By.xpath(BOOK_COVER_LOCATOR), ElementType.LABEL);
    }

    private CatalogBookModel getBookModel(String mainLocator) {
        AqualityServices.getConditionalWait().waitFor(() -> getBookDescriptionFromImage(mainLocator) != null);
        return new CatalogBookModel()
                .setImageTitle(Objects.requireNonNull(getBookDescriptionFromImage(mainLocator)))
                .setTitle(getBookParameter(mainLocator, BOOK_TITLE_LOC, "Book title"))
                .setAuthor(getBookParameter(mainLocator, BOOK_AUTHOR_LOC, "Book author"))
                .setBookType(getBookParameter(mainLocator, BOOK_TYPE_LOC, "Book type"));
    }

    private String getBookDescriptionFromImage(String mainLocator) {
        return getElementFactory().getLabel(By.xpath(mainLocator + BOOK_COVER_LOCATOR), "Book image content description").getAttribute(AndroidAttributes.CONTENT_DESC);
    }

    private String getBookParameter(String mainLocator, String subLocator, String name) {
        return Objects.requireNonNull(getElementFactory().getLabel(By.xpath(mainLocator + subLocator), name).getText());
    }

    private String getBlockLocator(String title) {
        return String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
    }
}
