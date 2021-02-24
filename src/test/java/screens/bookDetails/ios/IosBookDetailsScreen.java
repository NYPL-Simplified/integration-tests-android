package screens.bookDetails.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import constants.application.BookDetailsScreenConstants;
import constants.application.timeouts.BooksTimeouts;
import constants.localization.application.bookdetals.BookDetailsScreenInformationBlockKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import org.testng.Assert;
import screens.bookDetails.BookDetailsScreen;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosBookDetailsScreen extends BookDetailsScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeStaticText[@name=//XCUIElementTypeNavigationBar/@name]";

    private static final String BOOK_MAIN_INFO = "//XCUIElementTypeStaticText[@name=\"Description\"]//preceding-sibling::XCUIElementTypeStaticText[@name]";
    private static final String INFORMATION_TAB_LABELS_NAME_PART = "Information tab %1$s value";

    private static final String INFORMATION_TAB_VALUE_LOC_PART = "(//XCUIElementTypeStaticText[contains(@name, \"%1$s\")]"
            + "/following-sibling::XCUIElementTypeStaticText[@name])[1]";
    private static final String BOOK_ACTION_BUTTON_LOC = "//XCUIElementTypeButton[contains(@name,\"%1$s\")]";

    private static final String LBL_BOOK_AUTHORS_INFO = String.format("(%1$s)[%%d]", BOOK_MAIN_INFO);
    private static final String DESCRIPTIONS_LOC =
            "//XCUIElementTypeStaticText[@name=\"Description\"]/following-sibling::XCUIElementTypeTextView/*";

    private final ILabel lblBookTitleInfo = getElementFactory().getLabel(By.xpath("(//XCUIElementTypeOther//XCUIElementTypeStaticText[@name])[1]"), "Book title");
    private final IButton btnDownload = getActionButton(BookActionButtonKeys.DOWNLOAD);
    private final IButton btnRead = getActionButton(BookActionButtonKeys.READ);
    private final IButton btnRelatedBooks =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeStaticText[@name=\"Information\"]/following-sibling::XCUIElementTypeTable"), "Related books");
    private final IButton btnMore =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeStaticText[@name=\"Description\"]//following-sibling::XCUIElementTypeButton[@name=\"More…\"]"), "Show more description");
    private final IButton lblErrorDetails =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeStaticText"), "Error details");

    public IosBookDetailsScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    public List<ILabel> getDescriptions() {
        return getElementFactory().findElements(By.xpath(DESCRIPTIONS_LOC), ElementType.LABEL);
    }

    public List<ILabel> getBookMainInfo() {
        return getElementFactory().findElements(By.xpath(BOOK_MAIN_INFO), ElementType.LABEL);
    }

    @Override
    public void downloadBook() {
        btnDownload.click();
        btnRead.state().waitForDisplayed();
    }

    @Override
    public CatalogBookModel getBookInfo() {
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() ->
                        getBookMainInfo().size() > 0,
                Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_PAGE_LOADED.getTimeoutMillis())),
                "Book info was not loaded");
        return new CatalogBookModel()
                .setTitle(lblBookTitleInfo.getText())
                .setAuthor(getElementFactory().getLabel(By.xpath(String.format(LBL_BOOK_AUTHORS_INFO,
                        getBookMainInfo().size())), "Author").getText());
    }

    @Override
    public boolean isValuePresentInInformationBlock(BookDetailsScreenInformationBlockKeys key, String value) {
        ILabel lblInformationBlockValue = getElementFactory()
                .getLabel(By.xpath(String.format(INFORMATION_TAB_VALUE_LOC_PART, key.i18n())),
                        String.format(INFORMATION_TAB_LABELS_NAME_PART, key.i18n()));
        lblInformationBlockValue.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return lblInformationBlockValue.state().isDisplayed();
    }

    @Override
    public boolean isDescriptionPresent() {
        return AqualityServices.getConditionalWait().waitFor(() -> getDescriptions().size() > 0);
    }

    @Override
    public String getDescriptionText() {
        if (btnMore.state().isDisplayed()) {
            btnMore.click();
            btnMore.state().waitForNotDisplayed();
        }
        return getDescription();
    }

    private String getDescription() {
        return getDescriptions()
                .stream()
                .map(IElement::getText)
                .collect(Collectors.joining(BookDetailsScreenConstants.DESCRIPTION_DELIMITER));
    }

    @Override
    public void clickRelatedBooks() {
        btnRelatedBooks.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        AqualityServices.getLogger().info("Click does not need on the ios. Just scrolled to");
    }

    @Override
    public boolean isRelatedBooksVisible() {
        return btnRelatedBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(BookActionButtonKeys key) {
        AqualityServices.getConditionalWait().waitFor(() ->
                getActionButton(key).state().isDisplayed() || lblErrorDetails.state().isDisplayed(), Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        return getActionButton(key).state().isDisplayed();
    }

    @Override
    public void deleteBook() {
        clickActionButton(BookActionButtonKeys.DELETE);
    }

    @Override
    public void clickActionButton(BookActionButtonKeys buttonKeys) {
        IButton actionButton = getActionButton(buttonKeys);
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.tap(PointOption.point(actionButton.getElement().getCenter()));
        action.perform();
    }

    @Override
    public boolean isActionButtonPresent(BookActionButtonKeys actionButton) {
        return getActionButton(actionButton).state().isDisplayed();
    }

    @Override
    public String getErrorDetails() {
        if (isErrorButtonPresent()) {
            return lblErrorDetails.getText();
        }
        return "";
    }

    @Override
    public boolean isErrorButtonPresent() {
        return lblErrorDetails.state().isDisplayed();
    }

    @Override
    public void openErrorDetails() {

    }

    @Override
    public void swipeError() {

    }

    @Override
    public boolean isBookReadyToRead() {
        return false;
    }

    private IButton getActionButton(BookActionButtonKeys buttonKey) {
        String key = buttonKey.i18n();
        return getElementFactory().getButton(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, key)), key);
    }
}
