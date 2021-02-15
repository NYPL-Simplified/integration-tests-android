package screens.holds.android;

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

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidHoldsScreen extends HoldsScreen {
    private final ILabel lblNoBooks = getElementFactory().getLabel(By.id("feedEmptyText"), "No Books Present");
    private final IButton btnCancel =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@text=\"Cancel Reservation\"]"), "Cancel");
    private final String BOOK_INFO_LOCATOR_PATTERN = "//android.widget.ImageView[@content-desc=\"%s\"]";

    private static final String BOOK_BLOCK_BY_TITLE_LOC =
            "//*[contains(@resource-id,\"bookCellIdle\") and .//*[contains(@resource-id,\"bookCellIdleTitle\") and contains(@text, \"%1$s\")]]";

    private static final String BOOK_ADD_BUTTON_LOC =
            "//*[contains(@resource-id,\"bookCellIdleButtons\")]/android.widget.Button[@content-desc=\"%1$s\"]";

    public AndroidHoldsScreen() {
        super(By.xpath("//android.widget.TextView[contains(@text,\"Holds\")]"));
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
        btnCancel.click();
        btnCancel.state().waitForNotDisplayed();
    }

    @Override
    public void clickBookByTitleButtonWithKey(String title, BookActionButtonKeys key) {
        clickOnSpecificBookElement(getActionButton(key, getBookTitleLocator(title)));
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key) {
        IButton btnBookAction = getActionButton(key, getBookTitleLocator(bookTitle));
        return btnBookAction.state().waitForDisplayed(Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
    }

    @Override
    public boolean isBookForCancelPresent() {
        return btnCancel.state().waitForDisplayed();
    }

    private void clickOnSpecificBookElement(IElement bookWithSpecificAddBtn) {
        bookWithSpecificAddBtn.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        bookWithSpecificAddBtn.click();
    }

    private ILabel getBook(String bookInfo) {
        ILabel book =
                getElementFactory().getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo)), "No Books Present");
        book.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return book;
    }

    private IButton getActionButton(BookActionButtonKeys key, String blockLoc) {
        String buttonName = key.i18n();
        return getElementFactory().getButton(By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC, buttonName)), buttonName);
    }

    private String getBookTitleLocator(String title) {
        return String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
    }
}
