package screens.catalog.screen.books;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.catalog.BookActionButtonKeys;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

public abstract class CatalogBooksScreen extends Screen {
    protected CatalogBooksScreen(By locator) {
        super(locator, "Catalog Books");
    }

    public abstract void selectFirstFoundBook();

    public abstract int getFoundBooksCount();

    public abstract AndroidCatalogBookModel getBookInfo(String title);

    public abstract AndroidCatalogBookModel scrollToTheBookAndClickAddButton(BookActionButtonKeys bookAddButtonKey);

    public abstract void clickTheBookByTitleBtnWithKey(String title, BookActionButtonKeys key);

    public abstract void openBookDetailsWithAction(BookActionButtonKeys action);

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key);
}
