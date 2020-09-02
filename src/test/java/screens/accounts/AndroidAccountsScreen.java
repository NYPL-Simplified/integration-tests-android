package screens.accounts;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountsScreen extends AccountsScreen {
    private final IButton addBtn = getElementFactory().getButton(By.id("accountsMenuActionAccountAdd"), "Add account");

    public AndroidAccountsScreen() {
        super(By.id("accountOthers"));
    }

    @Override
    public boolean isLibraryPresent(String libraryName) {
        return getLibraryButton(libraryName).state().waitForDisplayed();
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[contains(@text, '" + libraryName + "')]"), libraryName);
    }

    @Override
    public void openAccount(String libraryName) {
        getLibraryButton(libraryName).click();
    }

    @Override
    public void addAccount() {
        addBtn.click();
    }

    @Override
    public void openFirstLibrary() {
        getElementFactory().getButton(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[1]"), "First library").click();
    }
}
