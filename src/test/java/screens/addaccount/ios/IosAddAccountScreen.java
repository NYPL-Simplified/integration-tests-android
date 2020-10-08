package screens.addaccount.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.addaccount.AddAccountScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAddAccountScreen extends AddAccountScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeSheet[@name=\"Add Your Library\"]";

    private static final String LIBRARY_BUTTON_PATTERN_LOC = "//XCUIElementTypeButton[@name=\"%1$s\"]";

    public IosAddAccountScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void selectLibrary(String libraryName) {
        IButton buttonToWaitFor = getLibraryButton(libraryName);
        buttonToWaitFor.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        buttonToWaitFor.click();
    }

    private IButton getLibraryButton(String libraryName) {
        return getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_PATTERN_LOC, libraryName)), libraryName);
    }
}
