package screens.catalog.screen.books;

import aquality.appium.mobile.screens.Screen;
import constants.android.catalog.AndroidBookActionButtonKeys;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

public abstract class CatalogBooksScreen extends Screen {
    protected CatalogBooksScreen(By locator) {
        super(locator, "Catalog Books");
    }

    public abstract void selectFirstFoundBook();

    public abstract int getFoundBooksCount();

    public abstract AndroidCatalogBookModel getBookInfo(String title);

    public abstract AndroidCatalogBookModel reserveBook();

    public abstract void clickTheBookByTitleBtnWithKey(String title, AndroidBookActionButtonKeys key);

    public abstract void openBookDetailsForReserve();

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, AndroidBookActionButtonKeys key);

    public abstract AndroidCatalogBookModel downloadBook();

    public abstract AndroidCatalogBookModel borrowBook();
}
