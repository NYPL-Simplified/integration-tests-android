package screens.account;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.android.account.AndroidAccountScreenLoginFields;
import constants.android.account.AndroidAccountScreenLoginStatus;
import constants.android.timeouts.AuthorizationTimeouts;
import org.openqa.selenium.By;

import java.time.Duration;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountScreen extends AccountScreen {
    private static final String LOGIN_BTN_LOC_PATTERN =
            "//*[@resource-id=\"org.nypl.simplified.simplye:id/accountLoginButton\" and @text=\"%1$s\"]";

    private final IButton btnLogin = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AndroidAccountScreenLoginStatus.LOG_IN.getStatus())),
            "Log in");
    private final IButton btnLogout = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AndroidAccountScreenLoginStatus.LOG_OUT.getStatus())),
            "Log out");
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
        return AqualityServices.getConditionalWait().waitFor(() ->
                btnLogin.getText().equals(AndroidAccountScreenLoginStatus.LOG_IN.getStatus()));
    }

    @Override
    public void logOut() {
        btnLogout.click();
        AqualityServices.getConditionalWait().waitFor(() ->
                        btnLogin.getText().equals(AndroidAccountScreenLoginStatus.LOG_IN.getStatus())
                                && txbCard.getText().equals(AndroidAccountScreenLoginFields.BARCODE.getName())
                                && txbPin.getText().equals(AndroidAccountScreenLoginFields.PIN.getName()),
                Duration.ofMillis(AuthorizationTimeouts.TIMEOUT_USER_LOGGED_OUT.getTimeoutMillis()));
    }

}
