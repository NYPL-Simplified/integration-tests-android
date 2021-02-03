package screens.bookDetails;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.bookdetals.BookDetailsScreenInformationBlockKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;

public abstract class BookDetailsScreen extends Screen {
    protected BookDetailsScreen(By locator) {
        super(locator, "Book details");
    }

    public abstract void downloadBook();

    public abstract CatalogBookModel getBookInfo();

    public abstract boolean isValuePresentInInformationBlock(
            BookDetailsScreenInformationBlockKeys key, String value);

    public abstract boolean isDescriptionPresent();

    public abstract String getDescriptionText();

    public abstract void clickRelatedBooks();

    public abstract void deleteBook();

    public abstract boolean isRelatedBooksVisible();

    public abstract boolean isBookAddButtonTextEqualTo(BookActionButtonKeys key);

    public abstract void clickActionButton(BookActionButtonKeys buttonKeys);

    public abstract boolean isActionButtonPresent(BookActionButtonKeys actionButton);

    public abstract String getErrorDetails();

    public abstract boolean isErrorButtonPresent();

    public abstract void openErrorDetails();

    public abstract void swipeError();
}
