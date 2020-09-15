package screens.accounts.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.accounts.AccountsScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAccountsScreen extends AccountsScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeNavigationBar[@name=\"Accounts\"]";
    private static final String BUTTON_LIBRARY_LOC = "//XCUIElementTypeCell//XCUIElementTypeStaticText[@name=\"%1$s\"]";

    private final IButton addBtn = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeButton[@name=\"Add Library\"]"), "Add library");
    private final IButton btnFirstLibrary = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeCell[1]"), "First library");

    public IosAccountsScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public boolean isLibraryPresent(String libraryName) {
        return getLibraryButton(libraryName).state().waitForDisplayed();
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(By.xpath(String.format(BUTTON_LIBRARY_LOC, libraryName)), libraryName);
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
}
