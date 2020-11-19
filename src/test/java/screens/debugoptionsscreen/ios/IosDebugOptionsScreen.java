package screens.debugoptionsscreen.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.debugoptionsscreen.DebugOptionsScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosDebugOptionsScreen extends DebugOptionsScreen {
    private final IButton btnBookName =
            getElementFactory().getButton(By.id(""), "Add Custom OPDS feed");

    public IosDebugOptionsScreen() {
        super(By.id(""));
    }

    @Override
    public void addCustomOpds() {
        btnBookName.click();
    }
}
