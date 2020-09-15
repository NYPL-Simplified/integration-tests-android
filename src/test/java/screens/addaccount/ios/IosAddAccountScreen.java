package screens.addaccount.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
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

    @Override
    public void deleteLibrary(String libraryName) {
        IButton buttonToWaitFor = getLibraryButton(libraryName);
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(buttonToWaitFor.getElement()))).release().perform();
    }
}
