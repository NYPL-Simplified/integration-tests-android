package screens.bookDetails;

import aquality.appium.mobile.screens.Screen;
import constants.android.AndroidBookDetailsScreenInformationBlockKeys;
import constants.android.catalog.AndroidBookActionButtonKeys;
import org.openqa.selenium.By;

public abstract class BookDetailsScreen extends Screen {
    protected BookDetailsScreen(By locator) {
        super(locator, "Book details");
    }

    public abstract void downloadBook();

    public abstract void reserveBook();

    public abstract String getBookInfo();

    public abstract boolean isValueInTheInformationBlockPresent(
            AndroidBookDetailsScreenInformationBlockKeys key, String value);

    public abstract boolean isDescriptionPresent();

    public abstract String getDescriptionText();

    public abstract void clickRelatedBooks();

    public abstract void deleteBook();

    public abstract boolean isBookAddButtonTextEqualTo(AndroidBookActionButtonKeys key);
}
