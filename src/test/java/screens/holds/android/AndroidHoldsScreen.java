package screens.holds.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.android.catalog.AndroidBookActionButtonKeys;
import constants.android.timeouts.BooksTimeouts;
import org.openqa.selenium.By;
import screens.holds.HoldsScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidHoldsScreen extends HoldsScreen {
    private final ILabel lblNoBooks = getElementFactory().getLabel(By.id("feedEmptyText"), "No Books Present");
    private final IButton btnCancel =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@text=\"Cancel Reservation\"]"), "Cancel");
    private final String BOOK_INFO_LOCATOR_PATTERN = "//android.widget.ImageView[@content-desc=\"%s\"]";

    private static final String BOOK_ACTION_BUTTON_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleButtons\"]"
            + "/android.widget.Button[@content-desc=\"%1$s\"]";
    private static final String BOOK_BLOCK_BY_TITLE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\" "
            + "and .//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleTitle\" and contains(@text, '%1$s')]]";

    private static final String BOOK_ADD_BUTTON_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleButtons\"]"
            + "/android.widget.Button[@content-desc=\"%1$s\"]";

    private static final String BOOKS_WITH_ACTION_LOC = String.format(
            "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\" and .%1$s]", BOOK_ACTION_BUTTON_LOC);

    public AndroidHoldsScreen() {
        super(By.xpath("//android.widget.TextView[@content-desc=\"Search in Holds…\"]"));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookPresent(String bookInfo) {
        return getElementFactory()
                .getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo)), "No Books Present")
                .state()
                .waitForDisplayed();
    }

    @Override
    public void cancelReservations() {
        btnCancel.click();
        btnCancel.state().waitForNotDisplayed();
    }

    @Override
    public void clickTheBookByTitleBtnWithKey(String title, AndroidBookActionButtonKeys key) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
        final IButton bookAddBtn = getElementFactory().getButton(
                By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC,
                        key.getKey())), String.format("Book %1$s button", key.getKey()));
        clickOnTheSpecificBookElement(bookAddBtn);
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


    private void clickOnTheSpecificBookElement(IElement bookWithSpecificAddBtn) {
        bookWithSpecificAddBtn.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        bookWithSpecificAddBtn.click();
    }
}