package screens.settings.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.AuthorizationTimeouts;
import org.openqa.selenium.By;
import screens.settings.SettingsScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSettingsScreen extends SettingsScreen {
    private final IButton accountsBtn =
            getElementFactory().getButton(By.xpath("//android.widget.RelativeLayout/android.widget.TextView[contains(@text, \"Accounts\")]"), "Accounts");
    private final IButton buildBtn =
            getElementFactory().getButton(By.xpath("//android.widget.TextView[contains(@text, \"Build\")]"), "Build");
    private final IButton btnDebugOptions =
            getElementFactory().getButton(By.xpath("//android.widget.TextView[contains(@text, \"Debug options\")]"), "Debug options");

    public AndroidSettingsScreen() {
        super(By.xpath("//android.widget.TextView[contains(@text, \"App info\")]"));
    }

    @Override
    public void openAccounts() {
        accountsBtn.click();
    }

    @Override
    public boolean openDebugButton() {
        return AqualityServices.getConditionalWait().waitFor(() -> {
            buildBtn.click();
            return btnDebugOptions.state().isDisplayed();
        }, Duration.ofMillis(AuthorizationTimeouts.DEBUG_MENU_IS_OPENED.getTimeoutMillis()));
    }

    @Override
    public void openDebugMode() {
        btnDebugOptions.click();
    }
}
