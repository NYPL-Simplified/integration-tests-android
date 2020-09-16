package screens.books;

import aquality.appium.mobile.screens.Screen;
import constants.application.catalog.AndroidBookActionButtonKeys;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

public abstract class BooksScreen extends Screen {
    protected BooksScreen(By locator) {
        super(locator, "Books");
    }

    public abstract boolean isNoBooksMessagePresent();

    public abstract boolean isBookPresent(AndroidCatalogBookModel bookInfo);

    public abstract int getCountOfBooks();

    public abstract int getCountOfBooksWithAction(AndroidBookActionButtonKeys actionKey);

    public abstract void openBookPage(int index, AndroidBookActionButtonKeys actionKey);

    public abstract void refreshList();

    public abstract void readBook(AndroidCatalogBookModel bookInfo);
}
