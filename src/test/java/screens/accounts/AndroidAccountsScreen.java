package screens.accounts;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.maintoolbar.AndroidMainToolbar;
import screens.maintoolbar.MainToolbar;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountsScreen extends AccountsScreen {
    public AndroidAccountsScreen() {
        super(By.id("accountOthers"));
    }

    @Override
    public MainToolbar getToolbar() {
        return new AndroidMainToolbar();
    }

    @Override
    public boolean isLibraryPresent(String libraryName) {
        return getElementFactory().getButton(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[contains(@text, '" + libraryName + "')]"), libraryName).state().waitForDisplayed();
    }
}
