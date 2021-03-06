package screens.catalog.screen.books;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.catalog.BookActionButtonKeys;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;

public abstract class CatalogBooksScreen extends Screen {
    protected CatalogBooksScreen(By locator) {
        super(locator, "Catalog Books");
    }

    public abstract CatalogBookModel selectFirstFoundBook();

    public abstract int getFoundBooksCount();

    public abstract CatalogBookModel getBookInfo(String title);

    public abstract CatalogBookModel scrollToBookAndClickActionButton(BookActionButtonKeys bookAddButtonKey);

    public abstract void clickBookByTitleButtonWithKey(String title, BookActionButtonKeys key);

    public abstract void openBookWithGivenActionButtonDetails(BookActionButtonKeys action);

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key);

    public abstract CatalogBookModel scrollToBookByTypeAndClickActionButton(BookActionButtonKeys actionButtonKey, String bookType);

    public abstract CatalogBookModel scrollToBookByNameAndClickActionButton(BookActionButtonKeys actionButtonKey, String bookName);

    public abstract String getErrorMessage();

    public abstract boolean isErrorButtonPresent();
}
