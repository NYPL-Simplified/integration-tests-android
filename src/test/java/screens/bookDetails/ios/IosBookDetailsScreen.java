package screens.bookDetails.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.bookdetals.BookDetailsScreenInformationBlockKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import constants.application.timeouts.BooksTimeouts;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.bookDetails.BookDetailsScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.IOS)
public class IosBookDetailsScreen extends BookDetailsScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeStaticText[@name=//XCUIElementTypeNavigationBar/@name]";

    private static final String INFORMATION_TAB_LABELS_NAME_PART = "Information tab %1$s value";

    private static final String NAME_ATTRIBUTE_NAME = "name";

    private static final String INFORMATION_TAB_VALUE_LOC_PART = "(//XCUIElementTypeStaticText[contains(@name, \"%1$s\")]"
            + "/following-sibling::XCUIElementTypeStaticText[@name])[1]";
    private static final String BOOK_ACTION_BUTTON_LOC = "//XCUIElementTypeButton[@name=\"%1$s\"]";

    private static final String BTN_APPROVE_BOOK_ACTION = "//XCUIElementTypeScrollView[.//XCUIElementTypeStaticText[contains(@name, \"%1$s\")]]"
            + "/following-sibling::XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"%1$s\"]";


    private final ILabel lblBookInfo = getElementFactory().getLabel(By.xpath("//XCUIElementTypeImage[1]"), "Cover Image");
    private final ILabel lblBookTitleInfo = getElementFactory().getLabel(By.xpath("(//XCUIElementTypeOther//XCUIElementTypeStaticText[@name])[1]"), "Book title");
    private final ILabel lblBookFormatInfo = getElementFactory().getLabel(By.xpath(""), "Book format"); // does not exist on the ios
    private final ILabel lblBookAuthorsInfo = getElementFactory().getLabel(
            By.xpath("(//XCUIElementTypeOther//XCUIElementTypeStaticText[@name])[2]"), "Book Authors");
    private final ILabel lblBookDescription = getElementFactory().getLabel(
            By.xpath("//XCUIElementTypeStaticText[@name=\"Description\"]/following-sibling::XCUIElementTypeTextView/XCUIElementTypeStaticText"),
            "Description");
    private final IButton btnDownload = getActionButton(BookActionButtonKeys.DOWNLOAD);
    private final IButton btnRead = getActionButton(BookActionButtonKeys.READ);
    private final IButton btnDelete = getActionButton(BookActionButtonKeys.DELETE);
    private final IButton btnRelatedBooks = getElementFactory().getButton(By.xpath(""), // does not exist on the ios
            "Related books button");
    private final IButton btnDontAllowNotifications = getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Don’t Allow\"]"),
            "Dont allow notifications");
    private final IButton btnOkCannotAddBook = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeScrollView[.//XCUIElementTypeStaticText[@name=\"Borrowing failed\"]]"
                    + "/following-sibling::XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"OK\"]"),
            "Button ok");

    public IosBookDetailsScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void downloadBook() {
        btnDownload.click();
        btnRead.state().waitForDisplayed();
    }

    @Override
    public CatalogBookModel getBookInfo() {
        return new CatalogBookModel()
                .setTitle(lblBookTitleInfo.getText())
                .setAuthor(lblBookAuthorsInfo.getText());
    }

    @Override
    public boolean isValueInTheInformationBlockPresent(BookDetailsScreenInformationBlockKeys key, String value) {
        ILabel lblInformationBlockValue = getElementFactory()
                .getLabel(By.xpath(String.format(INFORMATION_TAB_VALUE_LOC_PART, key.i18n())),
                        String.format(INFORMATION_TAB_LABELS_NAME_PART, key.i18n()));
        lblInformationBlockValue.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return lblInformationBlockValue.state()
                .waitForDisplayed();
    }

    @Override
    public boolean isDescriptionPresent() {
        return lblBookDescription.state().waitForDisplayed();
    }

    @Override
    public String getDescriptionText() {
        return lblBookDescription.getText();
    }

    @Override
    public void clickRelatedBooks() {
        btnRelatedBooks.click();
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(BookActionButtonKeys key) {
        final IButton bookAddBtn = getActionButton(key);
        return bookAddBtn
                .state()
                .waitForDisplayed(Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
    }

    @Override
    public void deleteBook() {
        clickActionButton(BookActionButtonKeys.DELETE);
    }

    @Override
    public void clickActionButton(BookActionButtonKeys buttonKeys) {
        getActionButton(buttonKeys).click();
        if (btnDontAllowNotifications.state().waitForDisplayed()) {
            btnDontAllowNotifications.click();
        }
        if (btnOkCannotAddBook.state().isDisplayed()) {
            btnOkCannotAddBook.click();
        }

        IButton btnApproveAction = getElementFactory().getButton(By.xpath(String.format(BTN_APPROVE_BOOK_ACTION, buttonKeys.i18n())), buttonKeys.i18n());
        if (btnApproveAction.state().isDisplayed()) {
            btnApproveAction.click();
        }
    }

    private IButton getActionButton(BookActionButtonKeys buttonKey) {
        String key = buttonKey.i18n();
        return getElementFactory().getButton(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, key)), key);
    }
}
