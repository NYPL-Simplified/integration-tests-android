package screens.notifications;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.catalog.BookActionButtonKeys;
import org.openqa.selenium.By;

public abstract class NotificationModal extends Screen {
    protected NotificationModal(By locator) {
        super(locator, "Notification modal");
    }

    public abstract void closeSyncNotificationIfDisplayed();

    public abstract void closeCannotAddBookModalIfDisplayed();

    public abstract void clickBookActionPopupIfDisplayed(BookActionButtonKeys headerName, BookActionButtonKeys buttonName);

    public abstract void handleBookActionsAndNotificationPopups(BookActionButtonKeys buttonName);

    public abstract boolean isModalPresent();
}
