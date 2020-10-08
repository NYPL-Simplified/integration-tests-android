package screens.account;

import aquality.appium.mobile.screens.Screen;
import framework.configuration.Credentials;
import org.openqa.selenium.By;

public abstract class AccountScreen extends Screen {
    public AccountScreen(By locator) {
        super(locator, "Account");
    }

    public abstract void enterCredentials(Credentials credentials);

    public abstract void enterCredentialsViaKeyboard(String ebookCardValue, String ebookPinValue);

    public abstract boolean isLoginSuccessful();

    public abstract boolean isLogoutSuccessful();

    public abstract void logOut();

    public abstract boolean isLogoutRequired();
}
