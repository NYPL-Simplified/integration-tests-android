package screens.account;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class AccountScreen extends Screen {
    public AccountScreen(By locator) {
        super(locator, "Account");
    }

    public abstract void enterCredentials(String ebookCardValue, String ebookPinValue);

    public abstract boolean isLoginSuccessful();

    public abstract void logOut();
}
