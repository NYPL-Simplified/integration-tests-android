package screens.catalog.screen.books.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.BooksTimeouts;
import constants.localization.application.catalog.BookActionButtonKeys;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.catalog.screen.books.CatalogBooksScreen;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@ScreenType(platform = PlatformName.IOS)
public class IosCatalogBooksScreen extends CatalogBooksScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeCollectionView";

    private static final String ADD_BOOK_BUTTON_PATTERN = "//XCUIElementTypeStaticText[@name=\"%1$s\"]";
    private static final String BOOKS_LOC = ".//XCUIElementTypeCell";
    private static final String BOOK_BLOCK_BY_BUTTON_LOC = "//XCUIElementTypeCell[.//XCUIElementTypeButton[@name=\"%1$s\"]]";
    private static final String BOOK_TITLE_LOC = "//XCUIElementTypeStaticText[@name][1]";
    private static final String BOOK_AUTHOR_LOC = "//XCUIElementTypeStaticText[@name][2]";
    private static final String AUTHOR_NAME_XPATH_PATTERN =
            "//XCUIElementTypeStaticText[@name=\"%s\"]//following-sibling::XCUIElementTypeStaticText";
    private static final String ELEMENTS_TO_WAIT_FOR_XPATH = "//XCUIElementTypeCell//XCUIElementTypeOther//XCUIElementTypeStaticText";
    private static final int COUNT_OF_BUTTONS_TO_WAIT_FOR = 5;
    private static final String BUTTON_FOR_GIVEN_BOOK_XPATH_PATTERN =
            "//XCUIElementTypeStaticText[contains(@name,\"%1$s\")]//following-sibling::XCUIElementTypeOther//XCUIElementTypeStaticText[contains(@name,\"%2$s\")]";
    private static final String LIBRARY_BUTTON_LOCATOR_PATTERN = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final int MILLIS_TO_WAIT_FOR_SEARCH_LOADING = 40000;

    private final ILabel lblFirstFoundBook = getElementFactory().getLabel(
            By.xpath(BOOKS_LOC), "First found book");


    public IosCatalogBooksScreen() {
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
        return new CatalogBookModel()
                .setTitle(title)
                .setAuthor(getAuthorsName(title));
    }

    @Override
    public CatalogBookModel scrollToBookAndClickActionButton(BookActionButtonKeys bookAddButtonKey) {
        return performActionOnBook(bookAddButtonKey);
    }

    @Override
    public void clickBookByTitleButtonWithKey(String title, BookActionButtonKeys actionButton) {
        String key = actionButton.i18n();
        IButton bookAddBtn =
                getButtonForBookWithAction(title, key);
        clickOnSpecificBookElement(bookAddBtn);
    }

    @Override
    public void openBookWithGivenActionButtonDetails(BookActionButtonKeys action) {
        state().waitForDisplayed();
        waitForPageLoading();
        clickOnSpecificBookElement(getBookJacketWithGivenButtonName(action));
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys buttonKeys) {
        String key = buttonKeys.i18n();
        IButton bookAddBtn = getButtonForBookWithAction(bookTitle, key);
        return bookAddBtn.state().waitForDisplayed(Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
    }

    @Override
    public CatalogBookModel scrollToBookByTypeAndClickActionButton(BookActionButtonKeys actionButtonKey, String bookType) {
        String key = actionButtonKey.i18n();
        IButton button = getElementFactory().getButton(By.xpath(getBookAddButtonLocatorWithGivenType(actionButtonKey, bookType)), key);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);

        String bookTitle =
                getElementFactory().getButton(By.xpath(getBookAddButtonLocatorWithGivenType(actionButtonKey, bookType)), key).getText();
        //testing fix for misclick
        switchToEbooksTab();
        waitForPageLoading();
        //ends here
        return openBook(button, bookTitle);
    }

    @Override
    public CatalogBookModel scrollToBookByNameAndClickActionButton(BookActionButtonKeys actionButtonKey, String bookName) {
        String key = actionButtonKey.i18n();
        IButton actionButton = getButtonForBookWithAction(bookName, key);
        switchToEbooksTab();
        if (!actionButton.state().waitForDisplayed()) {
            actionButton.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        }
        AqualityServices.getLogger().info("Started Thread.sleep");
        try {
            Thread.sleep(MILLIS_TO_WAIT_FOR_SEARCH_LOADING);
        } catch (InterruptedException e) {
            AqualityServices.getLogger().debug("Thread.sleep issue happened. " + e.getLocalizedMessage());
        }
        AqualityServices.getLogger().info("Finished Thread.sleep");
        return openBook(actionButton, bookName);
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public boolean isErrorButtonPresent() {
        return false;
    }

    private CatalogBookModel performActionOnBook(BookActionButtonKeys buttonName) {
        IButton button = getAddBookButton(buttonName);
        waitForPageLoading();
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        String bookTitle =
                getBookJacketWithGivenButtonName(buttonName).findChildElement(By.xpath(BOOK_TITLE_LOC), ElementType.LABEL).getText();
        return openBook(button, bookTitle);
    }

    private ILabel getBookJacketWithGivenButtonName(BookActionButtonKeys button) {
        String key = button.i18n();
        return getElementFactory().getLabel(By.xpath(String.format(BOOK_BLOCK_BY_BUTTON_LOC, key)), "Book jacket with button " + key);
    }

    private void clickOnSpecificBookElement(IElement bookWithSpecificAddBtn) {
        bookWithSpecificAddBtn.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        bookWithSpecificAddBtn.click();
    }

    private String getBookAddButtonLocatorWithGivenType(BookActionButtonKeys actionButtonKey, String bookType) {
        String key = actionButtonKey.i18n();
        return String.format("", bookType, key);
    }

    private IButton getAddBookButton(BookActionButtonKeys button) {
        String key = button.i18n();
        return getElementFactory().getButton(By.xpath(String.format(ADD_BOOK_BUTTON_PATTERN, key)), key);
    }

    private CatalogBookModel openBook(IButton button, String bookTitle) {
        CatalogBookModel bookInfo = getBookInfo(bookTitle);
        button.state().waitForDisplayed();
        button.state().waitForClickable();
        button.click();
        button.state().waitForNotExist();
        return bookInfo;
    }

    private IButton getButtonForBookWithAction(String title, String key) {
        return getElementFactory().getButton(By.xpath(String.format(BUTTON_FOR_GIVEN_BOOK_XPATH_PATTERN, title, key)), key);
    }

    private void waitForPageLoading() {
        AqualityServices.getConditionalWait().waitFor(() -> getElementFactory().findElements(By.xpath(ELEMENTS_TO_WAIT_FOR_XPATH), ElementType.BUTTON).size() >= COUNT_OF_BUTTONS_TO_WAIT_FOR);
    }

    private String getAuthorsName(String title) {
        ILabel label = getElementFactory().getLabel(By.xpath(String.format(AUTHOR_NAME_XPATH_PATTERN, title)), title);
        return label.state().isDisplayed() ? label.getText() : "";
    }

    private CatalogBookModel getBookModel(String mainLocator) {
        return new CatalogBookModel()
                .setTitle(getBookParameter(mainLocator, BOOK_TITLE_LOC, "Book title"))
                .setAuthor(getBookParameter(mainLocator, BOOK_AUTHOR_LOC, "Book author"));
    }

    private String getBookParameter(String mainLocator, String subLocator, String name) {
        return Objects.requireNonNull(getElementFactory().getLabel(By.xpath(mainLocator + subLocator), name).getText());
    }

    private void switchToEbooksTab() {
        getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN, "eBooks")), "eBooks").click();
    }
}
