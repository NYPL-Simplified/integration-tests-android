package screens.holds.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.catalog.AndroidBookActionButtonKeys;
import constants.application.timeouts.BooksTimeouts;
import org.openqa.selenium.By;
import screens.holds.HoldsScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.IOS)
public class IosHoldsScreen extends HoldsScreen {
    private static final String MAIN_ELEMENT_EXISTING_BOOKS_IN_HOLDS = "//XCUIElementTypeStaticText[@name=\"WAITING FOR AVAILABILITY\"]";
    private static final String LBL_NO_BOOKS_LOC = "//XCUIElementTypeStaticText"
            + "[@name=\"When you reserve a book from the catalog, it will show up here. "
            + "Look here from time to time to see if your book is available to download.\"]";
    private final ILabel lblNoBooks = getElementFactory().getLabel(By.xpath(LBL_NO_BOOKS_LOC),
            "No Books Present");
    private final IButton btnRemove =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeStaticText[@name=\"Remove\"]"), "Remove");
    private final IButton btnApproveRemove =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeScrollView[.//XCUIElementTypeStaticText[@name=\"Remove Reservation\"]]"
                    + "/following-sibling::XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"Remove\"]\n"), "Approve removal modal");

    private static final String BOOK_INFO_LOCATOR_PATTERN = "//XCUIElementTypeStaticText[@name=\"%1$s\"]";

    private static final String BOOK_ACTION_BUTTON_LOC = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final String BOOK_BLOCK_BY_TITLE_LOC = String.format(
            "//XCUIElementTypeCollectionView//XCUIElementTypeCell[.%1$s]", BOOK_INFO_LOCATOR_PATTERN);

    private static final String BOOK_ADD_BUTTON_LOC = "//XCUIElementTypeStaticText[@name=\"%1$s\"]";

    private static final String BOOKS_WITH_ACTION_LOC = String.format(
            "//XCUIElementTypeCollectionView//XCUIElementTypeCell[.%1$s]", BOOK_ACTION_BUTTON_LOC);

    public IosHoldsScreen() {
        super(By.xpath(MAIN_ELEMENT_EXISTING_BOOKS_IN_HOLDS + "|" + LBL_NO_BOOKS_LOC));
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
        btnRemove.click();
        btnApproveRemove.click();
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
