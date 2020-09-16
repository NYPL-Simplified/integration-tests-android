package screens.settings.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import framework.utilities.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import screens.settings.SettingsScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosSettingsScreen extends SettingsScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeNavigationBar[@name=\"Settings\"]";

    private final IButton accountsBtn = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeStaticText[@name=\"Accounts\"]"), "Accounts");
    private final IButton cancelSyncAgree = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeButton[@name=\"Not Now\"]"), "Cancel sync agree");

    public IosSettingsScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void openAccounts() {
        AqualityServices.getConditionalWait().waitFor(() ->
                accountsBtn.state().isDisplayed() || cancelSyncAgree.state().isDisplayed());
        if (accountsBtn.state().isDisplayed()) {
            accountsBtn.click();
        } else if (cancelSyncAgree.state().isDisplayed()) {
            cancelSyncAgree.click();
        } else {
            AqualityServices.getLogger().info("Account page was opened");
        }
    }

}
