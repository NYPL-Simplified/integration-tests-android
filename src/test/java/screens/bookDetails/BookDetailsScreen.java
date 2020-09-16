package screens.bookDetails;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.bookdetals.BookDetailsScreenInformationBlockKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

public abstract class BookDetailsScreen extends Screen {
    protected BookDetailsScreen(By locator) {
        super(locator, "Book details");
    }

    public abstract void downloadBook();

    public abstract AndroidCatalogBookModel getBookInfo();

    public abstract boolean isValueInTheInformationBlockPresent(
            BookDetailsScreenInformationBlockKeys key, String value);

    public abstract boolean isDescriptionPresent();

    public abstract String getDescriptionText();

    public abstract void clickRelatedBooks();

    public abstract void deleteBook();

    public abstract boolean isBookAddButtonTextEqualTo(BookActionButtonKeys key);

    public abstract void clickActionButton(BookActionButtonKeys buttonKeys);
}
