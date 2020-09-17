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

    private static final String BOOKS_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\"]";

    private static final String BOOK_IMAGE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleCover\"]";
    private static final String BOOK_TITLE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleTitle\"]";
    private static final String BOOK_AUTHOR_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleAuthor\"]";
    private static final String BOOK_TYPE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleMeta\"]";
    private static final String BOOK_ACTION_BUTTON_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleButtons\"]"
            + "/android.widget.Button[@content-desc=\"%1$s\"]";

    private static final String BOOKS_WITH_ACTION_LOC = String.format(
            "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\" and .%1$s]", BOOK_ACTION_BUTTON_LOC);
    public static final String BOOK_INFO_BUTTON_PATTERN =
            "%s//following-sibling::android.widget.LinearLayout/android.widget.Button[@content-desc=\"%s\"]";

    private final ILabel lblNoBooks = getElementFactory().getLabel(By.id("feedEmptyText"), "No Books Present");
    private final IButton btnMenu =
            getElementFactory().getButton(By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"),
                    "Menu");
    private final IButton btnRefresh = getElementFactory().getButton(By.id("title"), "Refresh");
    private final String BOOK_INFO_LOCATOR_PATTERN = "//android.widget.ImageView[@content-desc=\"%s\"]";
    private final List<IElement> booksList = getElementFactory().findElements(
            By.xpath("//android.widget.ImageView[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleCover\"]"),
            ElementType.LABEL);

    public AndroidBooksScreen() {
        super(By.xpath(MAIN_ELEMENT_LOC));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookPresent(CatalogBookModel bookInfo) {
        ILabel book = getElementFactory()
                .getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo.getImageTitle())),
                        "No Books Present");
        if (!book.state().waitForDisplayed()) {
            book.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        }
        return book.state().waitForDisplayed();
    }

    @Override
    public int getCountOfBooks() {
        return booksList.size();
    }

    @Override
    public int getCountOfBooksWithAction(BookActionButtonKeys actionKey) {
        return getElementFactory().findElements(
                By.xpath(String.format(BOOK_ACTION_BUTTON_LOC, actionKey)), ElementType.LABEL).size();
    }

    @Override
    public void openBookPage(int index, BookActionButtonKeys actionKey) {
        getElementFactory()
                .findElements(By.xpath(String.format(BOOKS_WITH_ACTION_LOC, actionKey)), ElementType.BUTTON)
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
}
