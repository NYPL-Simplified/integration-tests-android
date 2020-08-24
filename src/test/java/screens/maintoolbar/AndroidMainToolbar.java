package screens.maintoolbar;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidMainToolbar extends MainToolbar {
    private final IButton addBtn = getElementFactory().getButton(By.id("accountsMenuActionAccountAdd"), "Add account");

    public AndroidMainToolbar() {
        super(By.id("mainToolbar"));
    }

    @Override
    public void addAccount() {
        addBtn.click();
    }
}
