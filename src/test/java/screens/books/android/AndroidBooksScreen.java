package screens.books.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.catalog.BookActionButtonKeys;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.books.BooksScreen;

import java.util.List;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBooksScreen extends BooksScreen {
    private static final String MAIN_ELEMENT_LOC = "//android.widget.TextView[@content-desc=\"Search in Books…\"]";
    private static final String BOOK_ACTION_BUTTON_LOC =
            "//*[contains(@resource-id,\"bookCellIdleButtons\")]/android.widget.Button[@content-desc=\"%1$s\"]";
    private static final String BOOKS_WITH_ACTION_LOC =
            String.format("//*[contains(@resource-id,\"bookCellIdle\") and .%1$s]", BOOK_ACTION_BUTTON_LOC);
    public static final String BOOK_INFO_BUTTON_PATTERN =
            "%s//following-sibling::android.widget.LinearLayout/android.widget.Button[@content-desc=\"%s\"]";
    public static final String BOOKS_LABELS_XPATH = "//android.widget.ImageView[contains(@resource-id,\"bookCellIdleCover\")]";
    private final String BOOK_INFO_LOCATOR_PATTERN = "//android.widget.ImageView[@content-desc=\"%s\"]";

    private final ILabel lblNoBooks = getElementFactory().getLabel(By.id("feedEmptyText"), "No Books Present");
    private final IButton btnMenu =
            getElementFactory().getButton(By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                    "Menu");
    private final IButton btnRefresh = getElementFactory().getButton(By.id("title"), "Refresh");

    public AndroidBooksScreen() {
        super(By.xpath(MAIN_ELEMENT_LOC));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().waitForDisplayed();
    }

    @Override
    public boolean isBookPresent(CatalogBookModel bookInfo) {
        ILabel book = getElementFactory()
                .getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo.getImageTitle())),
                        "No Books Present");
        book.getTouchActions().scrollToElement(SwipeDirection.DOWN);

        return book.state().waitForDisplayed();
    }

    @Override
    public int getCountOfBooks() {
        return getElementFactory().findElements(By.xpath(BOOKS_LABELS_XPATH), ElementType.LABEL).size();
    }

    @Override
    public int getCountOfBooksWithAction(BookActionButtonKeys actionKey) {
        return getBooksWithAction(actionKey).size();
    }

    @Override
    public void openBookPage(int index, BookActionButtonKeys actionKey) {
        getListOfElements(actionKey, BOOKS_WITH_ACTION_LOC, ElementType.BUTTON)
                .get(index)
                .click();
    }

    @Override
    public void refreshList() {
        btnMenu.click();
        btnRefresh.click();
    }

    @Override
    public void readBook(CatalogBookModel bookInfo) {
        String readButtonName = BookActionButtonKeys.READ.i18n();
        getElementFactory().getButton(By.xpath(String.format(BOOK_INFO_BUTTON_PATTERN, String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo.getImageTitle()), readButtonName)), readButtonName).click();
    }

    @Override
    public void scrollForButtonWithAction(BookActionButtonKeys actionButton) {
        getElementFactory().getButton(By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, actionButton.i18n())), actionButton.i18n()).getTouchActions().scrollToElement(SwipeDirection.DOWN);
    }

    private List<IElement> getBooksWithAction(BookActionButtonKeys actionKey) {
        return getListOfElements(actionKey, BOOK_ACTION_BUTTON_LOC, ElementType.LABEL);
    }

    private List<IElement> getListOfElements(BookActionButtonKeys actionKey, String bookActionButtonLoc, ElementType label) {
        return getElementFactory().findElements(
                By.xpath(String.format(bookActionButtonLoc, actionKey.i18n())), label);
    }
}
