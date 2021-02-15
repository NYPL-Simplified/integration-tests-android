package screens.bookDetails.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.attributes.AndroidAttributes;
import constants.application.timeouts.BooksTimeouts;
import constants.localization.application.bookdetals.BookDetailsScreenInformationBlockKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.swipe.SwipeElementUtils;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.bookDetails.BookDetailsScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBookDetailsScreen extends BookDetailsScreen {
    private static final String INFORMATION_TAB_LABELS_NAME_PART = "Information tab %1$s value";

    private static final String INFORMATION_TAB_VALUE_LOC_PART =
            "//*[contains(@resource-id,\"key\") and @text=\"%1$s\"]/following-sibling::*[contains(@resource-id,\":id/value\")]";
    private static final String BOOK_ACTION_BUTTON_LOC = "//android.widget.Button[@content-desc=\"%1$s\"]";

    private final ILabel lblBookInfo = getElementFactory().getLabel(By.id("bookDetailCoverImage"), "Cover Image");
    private final ILabel lblErrorScreen = getElementFactory().getLabel(By.xpath("//android.widget.ScrollView"), "Error Screen");
    private final ILabel lblBookTitleInfo = getElementFactory().getLabel(By.id("bookDetailTitle"), "Book title");
    private final ILabel lblBookFormatInfo = getElementFactory().getLabel(By.id("bookDetailFormat"), "Book format");
    private final ILabel lblBookAuthorsInfo = getElementFactory().getLabel(By.id("bookDetailAuthors"), "Book Authors");
    private final ILabel lblBookDescription =
            getElementFactory().getLabel(By.xpath("//*[contains(@resource-id,\"bookDetailDescriptionText\")]"), "Description");
    private final ILabel lblErrorMessage = getElementFactory().getLabel(By.id("errorDetails"), "Error message");

    private final IButton btnDownload = getActionButton(BookActionButtonKeys.DOWNLOAD);
    private final IButton btnRead = getActionButton(BookActionButtonKeys.READ);
    private final IButton btnDelete = getActionButton(BookActionButtonKeys.DELETE);
    private final IButton btnRelatedBooks =
            getElementFactory().getButton(By.xpath("//*[contains(@resource-id,\"bookDetailRelated\")]"), "Related books");
    private final IButton btnErrorDetails =
            getElementFactory().getButton(By.xpath("//*[contains(@resource-id,'bookDetailButtons')]//*[contains(@text,'Details')]"), "Error");

    public AndroidBookDetailsScreen() {
        super(By.id("bookDetailCover"));
    }

    @Override
    public void downloadBook() {
        btnDownload.click();
        btnRead.state().waitForDisplayed();
    }

    @Override
    public CatalogBookModel getBookInfo() {
        return new CatalogBookModel()
                .setImageTitle(lblBookInfo.getAttribute(AndroidAttributes.CONTENT_DESC))
                .setTitle(lblBookTitleInfo.getText())
                .setBookType(lblBookFormatInfo.getText())
                .setAuthor(lblBookAuthorsInfo.getText());
    }

    @Override
    public boolean isValuePresentInInformationBlock(BookDetailsScreenInformationBlockKeys key, String value) {
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
    public boolean isRelatedBooksVisible() {
        return btnRelatedBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(BookActionButtonKeys key) {
        IButton button = getActionButton(key);
        AqualityServices.getConditionalWait().waitFor(() -> button.state().isDisplayed() || isErrorButtonPresent(), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        return button.state().isDisplayed();
    }

    @Override
    public void deleteBook() {
        btnDelete.click();
    }

    @Override
    public void clickActionButton(BookActionButtonKeys buttonKeys) {
        getActionButton(buttonKeys).click();
    }

    @Override
    public boolean isActionButtonPresent(BookActionButtonKeys actionButton) {
        return getActionButton(actionButton).state().waitForDisplayed();
    }

    @Override
    public String getErrorDetails() {
        if (lblErrorMessage.state().isDisplayed()) {
            return lblErrorMessage.getText();
        } else {
            return "";
        }
    }

    @Override
    public boolean isErrorButtonPresent() {
        return btnErrorDetails.state().isDisplayed();
    }

    @Override
    public void openErrorDetails() {
        btnErrorDetails.click();
    }

    @Override
    public void swipeError() {
        SwipeElementUtils.swipeThroughEntireElementUp(lblErrorScreen);
    }

    private IButton getActionButton(BookActionButtonKeys buttonKey) {
        String key = buttonKey.i18n();
        return getElementFactory().getButton(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, key)), key);
    }
}
