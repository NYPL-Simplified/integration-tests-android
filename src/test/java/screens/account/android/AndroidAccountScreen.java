package screens.account.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.AuthorizationTimeouts;
import constants.application.timeouts.BooksTimeouts;
import constants.localization.application.account.AccountScreenLoginStatus;
import framework.utilities.keyboard.KeyboardUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import screens.account.AccountScreen;

import java.time.Duration;
import java.util.Collections;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountScreen extends AccountScreen {
    private static final String LOGIN_BTN_LOC_PATTERN = "//*[contains(@resource-id,\"accountLoginButton\") and @text=\"%1$s\"]";

    private final IButton btnLogin = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AccountScreenLoginStatus.LOG_IN.i18n())),
            "Log in");
    private final IButton btnLoginAction = getElementFactory().getButton(By.id("accountLoginButton"), "Log ... action");
    private final IButton btnLogout = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AccountScreenLoginStatus.LOG_OUT.i18n())),
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
        btnLogout.state().waitForExist(Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        return AqualityServices.getConditionalWait().waitFor(() ->
                btnLoginAction.getText().equals(AccountScreenLoginStatus.LOG_OUT.i18n()));
    }

    @Override
    public boolean isLogoutSuccessful() {
        return AqualityServices.getConditionalWait().waitFor(() ->
                btnLoginAction.getText().equals(AccountScreenLoginStatus.LOG_IN.i18n()));
    }

    @Override
    public void logOut() {
        final String loginTextBeforeLogout = txbCard.getText();
        final String passwordTextBeforeLogout = txbPin.getText();
        btnLogout.click();
        AqualityServices.getConditionalWait().waitFor(() ->
                        btnLogin.getText().equals(AccountScreenLoginStatus.LOG_IN.i18n())
                                && !txbCard.getText().equals(loginTextBeforeLogout)
                                && !txbPin.getText().equals(passwordTextBeforeLogout),
                Duration.ofMillis(AuthorizationTimeouts.TIMEOUT_USER_LOGGED_OUT.getTimeoutMillis()),
                Duration.ofMillis(AuthorizationTimeouts.TIMEOUT_USER_LOGGED_OUT.getPollingMillis()),
                Collections.singletonList(NoSuchElementException.class));
    }

    @Override
    public boolean isLogoutRequired() {
        return btnLogout.state().isDisplayed();
    }
}
