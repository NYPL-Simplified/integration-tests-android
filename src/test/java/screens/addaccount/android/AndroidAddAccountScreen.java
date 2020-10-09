package screens.addaccount.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.addaccount.AddAccountScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAddAccountScreen extends AddAccountScreen {

    public static final String LIBRARY_BUTTON_LOCATOR_PATTERN = "//android.widget.TextView[contains(@text, \"%s\")]";

    public AndroidAddAccountScreen() {
        super(By.id("accountRegistryTitle"));
    }

    @Override
    public void selectLibrary(String libraryName) {
        IButton buttonToWaitFor = getLibraryButton(libraryName);
        buttonToWaitFor.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        buttonToWaitFor.click();
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN, libraryName)), libraryName);
    }
}
