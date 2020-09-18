package screens.accounts.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import screens.accounts.AccountsScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAccountsScreen extends AccountsScreen {
    private final IButton addBtn = getElementFactory().getButton(By.id("accountsMenuActionAccountAdd"), "Add account");
    private IButton btnFirstLibrary =
            getElementFactory().getButton(By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView[1]"), "First library");

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
        btnFirstLibrary.click();
    }

    @Override
    public void deleteLibrary(String libraryName) {
        IButton buttonToWaitFor = getLibraryButton(libraryName);
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(buttonToWaitFor.getElement()))).release().perform();
    }
}
