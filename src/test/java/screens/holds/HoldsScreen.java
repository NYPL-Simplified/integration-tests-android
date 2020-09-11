package screens.holds;

import aquality.appium.mobile.screens.Screen;
import constants.android.catalog.AndroidBookActionButtonKeys;
import org.openqa.selenium.By;

public abstract class HoldsScreen extends Screen {
    protected HoldsScreen(By locator) {
        super(locator, "Holds");
    }

    public abstract boolean isNoBooksMessagePresent();

    public abstract boolean isBookPresent(String bookInfo);

    public abstract void cancelReservations();

    public abstract void clickTheBookByTitleBtnWithKey(String title, AndroidBookActionButtonKeys key);

    public abstract boolean isBookAddButtonTextEqualTo(String bookTitle, AndroidBookActionButtonKeys key);
}
