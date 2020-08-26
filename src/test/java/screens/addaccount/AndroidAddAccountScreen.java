package screens.addaccount;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAddAccountScreen extends AddAccountScreen {
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
        return getElementFactory().getButton(By.xpath("//android.widget.TextView[contains(@text, '" + libraryName + "')]"), libraryName);
    }

    @Override
    public void deleteLibrary(String libraryName) {
        IButton buttonToWaitFor = getLibraryButton(libraryName);
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.longPress(LongPressOptions.longPressOptions().withElement(ElementOption.element(buttonToWaitFor.getElement()))).release().perform();
    }
}
