package screens.account.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.account.AccountScreenLoginStatus;
import constants.application.timeouts.AuthorizationTimeouts;
import framework.utilities.keyboard.KeyboardUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import screens.account.AccountScreen;

import java.time.Duration;

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
                btnLogout.getText().equals(AccountScreenLoginStatus.LOG_OUT.i18n()));
    }

    @Override
    public boolean isLogoutSuccessful() {
        return AqualityServices.getConditionalWait().waitFor(() ->
                btnLogin.getText().equals(AccountScreenLoginStatus.LOG_IN.i18n()));
    }

    @Override
    public void logOut() {
        final String loginTextBeforeLogout = txbCard.getText();
        final String passwordTextBeforeLogout = txbPin.getText();
        btnLogout.click();
        btnApproveSignOut.click();
        AqualityServices.getConditionalWait().waitFor(() ->
                        btnLogin.getText().equals(AccountScreenLoginStatus.LOG_IN.i18n())
                                && !txbCard.getText().equals(loginTextBeforeLogout)
                                && !txbPin.getText().equals(passwordTextBeforeLogout),
                Duration.ofMillis(AuthorizationTimeouts.TIMEOUT_USER_LOGGED_OUT.getTimeoutMillis()));
    }

}
