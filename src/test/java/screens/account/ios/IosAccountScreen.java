package screens.account.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.AuthorizationTimeouts;
import constants.localization.application.account.AccountScreenLoginStatus;
import framework.configuration.Credentials;
import framework.utilities.keyboard.KeyboardUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import screens.account.AccountScreen;

import java.time.Duration;
import java.util.Collections;

@ScreenType(platform = PlatformName.IOS)
public class IosAccountScreen extends AccountScreen {
    private static final String LOGIN_BTN_LOC_PATTERN = "//XCUIElementTypeStaticText[@name=\"%1$s\"]\n";

    private final IButton btnLogin = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AccountScreenLoginStatus.LOG_IN.i18n())),
            "Log in");
    private final IButton btnLogout = getElementFactory().getButton(
            By.xpath(String.format(LOGIN_BTN_LOC_PATTERN, AccountScreenLoginStatus.LOG_OUT.i18n())),
            "Log out");
    private final IButton btnApproveSignOut = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeButton[@name=\"Sign Out\"]"),
            "Log out approve");
    private final ITextBox txbCard = getElementFactory().getTextBox(By.xpath("//XCUIElementTypeTextField[@value]"), "Card");
    private final ITextBox txbPin = getElementFactory().getTextBox(By.xpath("//XCUIElementTypeSecureTextField[@value]"), "Pin");

    public IosAccountScreen() {
        super(By.xpath("//XCUIElementTypeNavigationBar[@name=\"Account\"]"));
    }

    @Override
    public void enterCredentials(Credentials credentials) {
        if (!btnLogout.state().waitForDisplayed()) {
            txbCard.click();
            txbCard.clearAndType(credentials.getBarcode());
            txbPin.clearAndTypeSecret(credentials.getPin());
            btnLogin.click();
        }
    }

    @Override
    public void enterCredentialsViaKeyboard(Credentials credentials) {
        enterDataViaKeyboard(txbCard, credentials.getBarcode());
        enterDataViaKeyboard(txbPin, credentials.getPin());
        KeyboardUtils.hideKeyboard();
        btnLogin.click();
    }

    @Override
    public String getLoginFailedMessage() {
        return "";
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
                btnLogout.getText().equals(AccountScreenLoginStatus.LOG_OUT.i18n()));
    }

    @Override
    public boolean isLogoutSuccessful() {
        return AqualityServices.getConditionalWait().waitFor(() -> btnLogin.state().isDisplayed());
    }

    @Override
    public void logOut() {
        final String loginTextBeforeLogout = txbCard.getText();
        final String passwordTextBeforeLogout = txbPin.getText();
        btnLogout.click();
        btnApproveSignOut.click();
        AqualityServices.getConditionalWait()
                .waitFor(() -> btnLogin.getText().equals(AccountScreenLoginStatus.LOG_IN.i18n())
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
