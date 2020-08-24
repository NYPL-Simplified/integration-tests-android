package screens.maintoolbar;

import aquality.appium.mobile.elements.interfaces.IButton;
import org.openqa.selenium.By;

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
