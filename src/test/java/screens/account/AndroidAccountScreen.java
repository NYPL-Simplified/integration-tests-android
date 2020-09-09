package screens.account;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.android.timeouts.AuthorizationTimeouts;
import org.openqa.selenium.By;

import java.time.Duration;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountScreen extends AccountScreen {
    public static final String LOG_IN_BUTTON_VALUE = "Log in";
    public static final String LOG_OUT_BUTTON_VALUE = "Log out";
    public static final String DEFAULT_LOGIN_TEXT = "Barcode";
    public static final String DEFAULT_PASS_TEXT = "PIN";
    private final IButton btnLogin = getElementFactory().getButton(By.id("accountLoginButton"), "Log in");
    private final ITextBox txbCard = getElementFactory().getTextBox(By.id("authBasicUserField"), "Card");
    private final ITextBox txbPin = getElementFactory().getTextBox(By.id("authBasicPassField"), "Pin");

    public AndroidAccountScreen() {
        super(By.id("auth"));
    }

    @Override
    public void enterCredentials(String ebookCardValue, String ebookPinValue) {
        txbCard.clearAndType(ebookCardValue);
        txbPin.clearAndType(ebookPinValue);
        btnLogin.click();
    }

    @Override
    public boolean isLoginSuccessful() {
        return AqualityServices.getConditionalWait().waitFor(() -> btnLogin.getText().equals(LOG_OUT_BUTTON_VALUE));
    }

    @Override
    public void logOut() {
        btnLogin.click();
        AqualityServices.getConditionalWait().waitFor(() -> btnLogin.getText().equals(LOG_IN_BUTTON_VALUE)
                        && txbCard.getText().equals(DEFAULT_LOGIN_TEXT)
                        && txbPin.getText().equals(DEFAULT_PASS_TEXT),
                Duration.ofMillis(AuthorizationTimeouts.TIMEOUT_USER_LOGGED_OUT.getTimeoutMillis()));
    }

}
