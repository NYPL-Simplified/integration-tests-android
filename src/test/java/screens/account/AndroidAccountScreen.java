package screens.account;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountScreen extends AccountScreen {
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
        return AqualityServices.getConditionalWait().waitFor(() -> btnLogin.getText().equals("Log out"));
    }

    @Override
    public void logOut() {
        btnLogin.click();
        AqualityServices.getConditionalWait().waitFor(() -> btnLogin.getText().equals("Log in"));
    }
}
