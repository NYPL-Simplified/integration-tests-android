package screens.addaccount.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.AuthorizationTimeouts;
import org.openqa.selenium.By;
import screens.addaccount.AddAccountScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAddAccountScreen extends AddAccountScreen {
    private IButton btnSearch = getElementFactory().getButton(By.id("accountMenuActionSearch"), "Search");
    private ITextBox txbSearch = getElementFactory().getTextBox(By.id("search_src_text"), "Search");
    private ILabel lblSelectLibrary =
            getElementFactory().getLabel(By.xpath("//*[@text=\"Please select a library…\" and contains(@resource-id,\"accountRegistryTitle\")]"), "Please select a library");

    public static final String LIBRARY_BUTTON_LOCATOR_PATTERN = "//android.widget.TextView[contains(@text, \"%s\")]";

    public AndroidAddAccountScreen() {
        super(By.id("accountRegistryTitle"));
    }

    @Override
    public void selectLibrary(String libraryName) {
        lblSelectLibrary.state().waitForDisplayed(Duration.ofMillis(AuthorizationTimeouts.DEBUG_MENU_IS_OPENED.getTimeoutMillis()));
        btnSearch.click();
        AqualityServices.getApplication().getDriver().hideKeyboard();
        txbSearch.clearAndType(libraryName);
        getLibraryButton(libraryName).click();
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN, libraryName)), libraryName);
    }
}
