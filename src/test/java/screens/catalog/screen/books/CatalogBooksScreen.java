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

    public abstract CatalogBookModel scrollToTheBookAndClickAddButton(BookActionButtonKeys bookAddButtonKey);

    public abstract void clickTheBookByTitleBtnWithKey(String title, BookActionButtonKeys key);

    public abstract void openBookDetailsWithAction(BookActionButtonKeys action);

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key);

    public abstract CatalogBookModel scrollToTheBookByTypeAndClickAddButton(BookActionButtonKeys actionButtonKey, String bookType);

    public abstract CatalogBookModel scrollToTheBookByNameAndClickAddButton(BookActionButtonKeys actionButtonKey, String bookName);
}
