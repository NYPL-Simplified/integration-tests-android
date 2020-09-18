package screens.catalog.screen.books;

import aquality.appium.mobile.screens.Screen;
import constants.application.catalog.AndroidBookActionButtonKeys;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

public abstract class CatalogBooksScreen extends Screen {
    protected CatalogBooksScreen(By locator) {
        super(locator, "Catalog Books");
    }

    public abstract void selectFirstFoundBook();

    public abstract int getFoundBooksCount();

    public abstract AndroidCatalogBookModel getBookInfo(String title);

    public abstract AndroidCatalogBookModel scrollToTheBookAndClickAddButton(AndroidBookActionButtonKeys bookAddButtonKey);

    public abstract void clickTheBookByTitleBtnWithKey(String title, AndroidBookActionButtonKeys key);

    public abstract void openBookDetailsWithAction(AndroidBookActionButtonKeys action);

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, AndroidBookActionButtonKeys key);

    public abstract AndroidCatalogBookModel scrollToTheBookAndClickAddButton(AndroidBookActionButtonKeys actionButtonKey, String bookType);
}
