package screens.books;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.catalog.BookActionButtonKeys;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;

public abstract class BooksScreen extends Screen {
    protected BooksScreen(By locator) {
        super(locator, "Books");
    }

    public abstract boolean isNoBooksMessagePresent();

    public abstract boolean isBookPresent(CatalogBookModel bookInfo);

    public abstract int getCountOfBooks();

    public abstract int getCountOfBooksWithAction(BookActionButtonKeys actionKey);

    public abstract void openBookPage(int index, BookActionButtonKeys actionKey);

    public abstract void refreshList();

    public abstract void readBook(CatalogBookModel bookInfo);

    public abstract void scrollForButtonWithAction(BookActionButtonKeys actionButton);
}
