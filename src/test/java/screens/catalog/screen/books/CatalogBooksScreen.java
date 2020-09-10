package screens.catalog.screen.books;

import aquality.appium.mobile.screens.Screen;
import constants.android.catalog.AndroidBookAddButtonKeys;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

public abstract class CatalogBooksScreen extends Screen {
    protected CatalogBooksScreen(By locator) {
        super(locator, "Catalog Books");
    }

    public abstract void selectFirstFoundBook();

    public abstract int getFoundBooksCount();

    public abstract AndroidCatalogBookModel getBookInfo(String title);

    public abstract AndroidCatalogBookModel scrollToTheBookAndClickAddButton(AndroidBookAddButtonKeys bookAddButtonKey);

    public abstract void clickTheBookByTitleBtnWithKey(String title, AndroidBookAddButtonKeys key);

    public abstract void openBookDetailsForReserve();

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, AndroidBookAddButtonKeys key);
}
