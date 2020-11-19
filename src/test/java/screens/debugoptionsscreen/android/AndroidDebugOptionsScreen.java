package screens.debugoptionsscreen.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.debugoptionsscreen.DebugOptionsScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidDebugOptionsScreen extends DebugOptionsScreen {
    private final IButton btnBookName =
            getElementFactory().getButton(By.id("settingsVersionDevCustomOPDS"), "Add Custom OPDS feed");

    public AndroidDebugOptionsScreen() {
        super(By.id("settingsVersionDevTitle"));
    }

    @Override
    public void addCustomOpds() {
        btnBookName.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        btnBookName.click();
    }
}
