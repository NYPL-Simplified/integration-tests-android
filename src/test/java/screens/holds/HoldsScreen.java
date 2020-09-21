package screens.holds;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.catalog.BookActionButtonKeys;
import org.openqa.selenium.By;

public abstract class HoldsScreen extends Screen {
    protected HoldsScreen(By locator) {
        super(locator, "Holds");
    }

    public abstract boolean isNoBooksMessagePresent();

    public abstract boolean isBookPresent(String bookInfo);

    public abstract void cancelReservations();

    public abstract void clickTheBookByTitleBtnWithKey(String title, BookActionButtonKeys key);

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, BookActionButtonKeys key);
}
