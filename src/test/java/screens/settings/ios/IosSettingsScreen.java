package screens.settings.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.settings.SettingsScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosSettingsScreen extends SettingsScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeNavigationBar[@name=\"Settings\"]";

    private final IButton accountsBtn = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeStaticText[@name=\"Accounts\"]"), "Accounts");

    public IosSettingsScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void openAccounts() {
        accountsBtn.click();
    }
}
