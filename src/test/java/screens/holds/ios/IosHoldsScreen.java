package screens.holds.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.BooksTimeouts;
import constants.localization.application.catalog.BookActionButtonKeys;
import org.openqa.selenium.By;
import screens.holds.HoldsScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.IOS)
public class IosHoldsScreen extends HoldsScreen {
    private static final String MAIN_ELEMENT_EXISTING_BOOKS_IN_HOLDS = "//XCUIElementTypeStaticText[@name=\"WAITING FOR AVAILABILITY\"]";
    private static final String LBL_NO_BOOKS_LOC =
            "//XCUIElementTypeStaticText[@name=\"When you reserve a book from the catalog, it will show up here. Look here from time to time to see if your book is available to download.\"]";
    private static final String BOOK_INFO_LOCATOR_PATTERN = "//XCUIElementTypeStaticText[contains(@name,\"%1$s\")]";
    private static final String BOOK_BLOCK_BY_TITLE_LOC =
            String.format("//XCUIElementTypeCollectionView//XCUIElementTypeCell[.%1$s]", BOOK_INFO_LOCATOR_PATTERN);
    private static final String BOOK_ADD_BUTTON_LOC = "//XCUIElementTypeStaticText[@name=\"%1$s\"]";

    private final ILabel lblNoBooks = getElementFactory().getLabel(By.xpath(LBL_NO_BOOKS_LOC),
            "No Books Present");
    private final IButton btnRemove =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeStaticText[@name=\"Remove\"]"), "Remove");
    private final IButton btnApproveRemove =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Remove\"]"), "Approve Removal");

    public IosHoldsScreen() {
        super(By.xpath(MAIN_ELEMENT_EXISTING_BOOKS_IN_HOLDS + "|" + LBL_NO_BOOKS_LOC));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookPresent(String bookInfo) {
        return getBook(bookInfo).state().waitForDisplayed();
    }

    @Override
    public boolean isBookNotPresent(String bookInfo) {
        return getBook(bookInfo).state().waitForNotDisplayed();
    }

    @Override
    public void cancelReservations() {
        btnRemove.click();
        btnApproveRemove.click();
    }

    @Override
    public void clickBookByTitleButtonWithKey(String title, BookActionButtonKeys key) {
        clickOnSpecificBookElement(getBookActionButton(key, getBookBlockLocator(title)));
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key) {
        IButton btnBookAdd = getBookActionButton(key, getBookBlockLocator(bookTitle));
        return btnBookAdd.state().waitForDisplayed(Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
    }

    @Override
    public boolean isBookForCancelPresent() {
        return btnRemove.state().isDisplayed();
    }

    private void clickOnSpecificBookElement(IElement bookWithSpecificAddBtn) {
        bookWithSpecificAddBtn.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        bookWithSpecificAddBtn.click();
    }

    private ILabel getBook(String bookInfo) {
        return getElementFactory().getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo)), bookInfo);
    }

    private IButton getBookActionButton(BookActionButtonKeys key, String blockLoc) {
        return getElementFactory().getButton(By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC, key.i18n())), String.format("Book %1$s", key.i18n()));
    }

    private String getBookBlockLocator(String title) {
        return String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
    }
}
