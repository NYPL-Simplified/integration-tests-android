package screens.account.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.AuthorizationTimeouts;
import constants.application.timeouts.BooksTimeouts;
import constants.localization.application.account.AccountScreenLoginStatus;
import framework.configuration.Credentials;
import framework.utilities.keyboard.KeyboardUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import screens.account.AccountScreen;

import java.time.Duration;
import java.util.Collections;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountScreen extends AccountScreen {
    private static final String BTN_LOGIN_ID = "authBasicLogin";
    private static final String LOGIN_BTN_LOC_PATTERN = "//*[contains(@resource-id,\"" + BTN_LOGIN_ID + "\") and @text=\"%1$s\"]";

    private final IButton btnLogin = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AccountScreenLoginStatus.LOG_IN.i18n())),
            "Log in");
    private final IButton btnLoginAction = getElementFactory().getButton(By.id(BTN_LOGIN_ID), "Log ... action");
    private final IButton btnLogout = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AccountScreenLoginStatus.LOG_OUT.i18n())),
            "Log out");
    private final IButton btnLogInError = getElementFactory().getButton(By.id("accountLoginProgressText"), "Error info");
    private final ITextBox txbCard = getElementFactory().getTextBox(By.id("authBasicUserField"), "Card");
    private final ITextBox txbPin = getElementFactory().getTextBox(By.id("authBasicPassField"), "Pin");
    private final ILabel lblLoginFailed =
            getElementFactory().getLabel(By.id("accountLoginProgressText"), "Login Failed Message");

    public AndroidAccountScreen() {
        super(By.id("auth"));
    }

    @Override
    public void enterCredentials(Credentials credentials) {
        txbCard.clearAndType(credentials.getBarcode());
        txbPin.clearAndTypeSecret(credentials.getPin());
        btnLogin.click();
    }

    @Override
    public void enterCredentialsViaKeyboard(Credentials credentials) {
        enterDataViaKeyboard(txbCard, credentials.getBarcode());
        enterDataViaKeyboard(txbPin, credentials.getPin());
        KeyboardUtils.hideKeyboard();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> !KeyboardUtils.isKeyboardVisible()),
                "Checking that keyboard is not shown");
        btnLogin.click();
    }

    @Override
    public String getLoginFailedMessage() {
        return lblLoginFailed.state().isDisplayed() ? lblLoginFailed.getText() : "";
    }

    private void enterDataViaKeyboard(ITextBox textBox, String value) {
        textBox.click();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(KeyboardUtils::isKeyboardVisible),
                "Checking that keyboard is shown");
        textBox.sendKeys(value);
    }

    @Override
    public boolean isLoginSuccessful() {
        waitForLogout();
        if (btnLogInError.state().isDisplayed()) {
            return false;
        } else {
            btnLogout.state().waitForExist(Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
            return btnLoginAction.getText().equals(AccountScreenLoginStatus.LOG_OUT.i18n());
        }
    }

    private void waitForLogout() {
        AqualityServices.getConditionalWait().waitFor(() ->
                btnLoginAction.getText().equals(AccountScreenLoginStatus.LOG_OUT.i18n()) || btnLogInError.state().isDisplayed());
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
                Duration.ofMillis(AuthorizationTimeouts.USER_LOGGED_OUT.getTimeoutMillis()),
                Duration.ofMillis(AuthorizationTimeouts.USER_LOGGED_OUT.getPollingMillis()),
                Collections.singletonList(NoSuchElementException.class));
    }

    @Override
    public boolean isLogoutRequired() {
        return btnLogout.state().isDisplayed();
    }
}
