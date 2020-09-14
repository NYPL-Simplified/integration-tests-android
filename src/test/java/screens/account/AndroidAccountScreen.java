package screens.account;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.android.account.AndroidAccountScreenLoginFields;
import constants.android.account.AndroidAccountScreenLoginStatus;
import constants.android.timeouts.AuthorizationTimeouts;
import framework.utilities.keyboard.KeyboardUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.testng.Assert;

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
    public void enterCredentialsViaKeyboard(String ebookCardValue, String ebookPinValue) {
        enterDataViaKeyboard(txbCard, ebookCardValue);
        enterDataViaKeyboard(txbPin, ebookPinValue);
        KeyboardUtils.hideKeyboard();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> !KeyboardUtils.isKeyboardVisible()),
                "Checking that keyboard is not shown");
        btnLogin.click();
    }

    private void enterDataViaKeyboard(ITextBox textBox, String value) {
        textBox.click();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(KeyboardUtils::isKeyboardVisible),
                "Checking that keyboard is shown");
        textBox.sendKeys(value);
    }

    @Override
    public boolean isLoginSuccessful() {
        return AqualityServices.getConditionalWait().waitFor(() ->
                btnLogout.getText().equals(AndroidAccountScreenLoginStatus.LOG_OUT.getStatus()));
    }

    @Override
    public boolean isLogoutSuccessful() {
        return AqualityServices.getConditionalWait().waitFor(() ->
                btnLogin.getText().equals(AndroidAccountScreenLoginStatus.LOG_IN.getStatus()));
    }

    @Override
    public void logOut() {
        final String loginTextBeforeLogout = txbCard.getText();
        final String passwordTextBeforeLogout = txbPin.getText();
        btnLogout.click();
        AqualityServices.getConditionalWait().waitFor(() ->
                        btnLogin.getText().equals(AndroidAccountScreenLoginStatus.LOG_IN.getStatus())
                                && !txbCard.getText().equals(loginTextBeforeLogout)
                                && !txbPin.getText().equals(passwordTextBeforeLogout),
                Duration.ofMillis(AuthorizationTimeouts.TIMEOUT_USER_LOGGED_OUT.getTimeoutMillis()));
    }

}
