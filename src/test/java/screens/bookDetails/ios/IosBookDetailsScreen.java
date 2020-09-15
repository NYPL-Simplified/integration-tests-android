package screens.bookDetails.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.android.bookdetals.AndroidBookDetailsScreenInformationBlockKeys;
import constants.android.catalog.AndroidBookActionButtonKeys;
import constants.android.timeouts.BooksTimeouts;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;
import screens.bookDetails.BookDetailsScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.IOS)
public class IosBookDetailsScreen extends BookDetailsScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeStaticText[@name=//XCUIElementTypeNavigationBar/@name]";

    private static final String INFORMATION_TAB_LABELS_NAME_PART = "Information tab %1$s value";

    private static final String CONTENT_ATTRIBUTE_NAME = "content-desc";

    private static final String INFORMATION_TAB_VALUE_LOC_PART = "(//XCUIElementTypeStaticText[contains(@name, \"%1$s\")]"
            + "/following-sibling::XCUIElementTypeStaticText[@name])[1]";
    private static final String BOOK_ACTION_BUTTON_LOC = "//XCUIElementTypeButton[@name=\"%1$s\"]";


    private final ILabel lblBookInfo = getElementFactory().getLabel(By.xpath(""), "Cover Image"); // does not exist on the ios
    private final ILabel lblBookTitleInfo = getElementFactory().getLabel(By.xpath("(//XCUIElementTypeOther//XCUIElementTypeStaticText[@name])[1]"), "Book title");
    private final ILabel lblBookFormatInfo = getElementFactory().getLabel(By.xpath(""), "Book format"); // does not exist on the ios
    private final ILabel lblBookAuthorsInfo = getElementFactory().getLabel(
            By.xpath("(//XCUIElementTypeOther//XCUIElementTypeStaticText[@name])[2]"), "Book Authors");
    private final ILabel lblBookDescription = getElementFactory().getLabel(
            By.xpath("//XCUIElementTypeStaticText[@name=\"Description\"]/following-sibling::XCUIElementTypeTextView/XCUIElementTypeStaticText"),
            "Description");
    private final IButton btnDownload = getActionButton(AndroidBookActionButtonKeys.DOWNLOAD);
    private final IButton btnRead = getActionButton(AndroidBookActionButtonKeys.READ);
    private final IButton btnReserve = getActionButton(AndroidBookActionButtonKeys.RESERVE);
    private final IButton btnCancel = getActionButton(AndroidBookActionButtonKeys.CANCEL);
    private final IButton btnDelete = getActionButton(AndroidBookActionButtonKeys.DELETE);
    private final IButton btnRelatedBooks = getElementFactory().getButton(By.xpath(""), // does not exist on the ios
            "Related books button");

    public IosBookDetailsScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void downloadBook() {
        btnDownload.click();
        btnRead.state().waitForDisplayed();
    }

    @Override
    public AndroidCatalogBookModel getBookInfo() {
        return new AndroidCatalogBookModel()
                .setImageTitle(lblBookInfo.getAttribute(CONTENT_ATTRIBUTE_NAME))
                .setTitle(lblBookTitleInfo.getText())
                .setBookType(lblBookFormatInfo.getText())
                .setAuthor(lblBookAuthorsInfo.getText());
    }

    @Override
    public boolean isValueInTheInformationBlockPresent(AndroidBookDetailsScreenInformationBlockKeys key, String value) {
        ILabel lblInformationBlockValue = getElementFactory()
                .getLabel(By.xpath(String.format(INFORMATION_TAB_VALUE_LOC_PART, key.getKey())),
                        String.format(INFORMATION_TAB_LABELS_NAME_PART, key.getKey()));
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
    public boolean isBookAddButtonTextEqualTo(AndroidBookActionButtonKeys key) {
        final IButton bookAddBtn = getActionButton(key);
        return bookAddBtn
                .state()
                .waitForDisplayed(Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
    }

    @Override
    public void deleteBook() {
        btnDelete.click();
    }

    @Override
    public void clickActionButton(AndroidBookActionButtonKeys buttonKeys) {
        getActionButton(buttonKeys).click();
    }

    private IButton getActionButton(AndroidBookActionButtonKeys buttonKey) {
        String key = buttonKey.getKey();
        return getElementFactory().getButton(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, key)), key);
    }
}
