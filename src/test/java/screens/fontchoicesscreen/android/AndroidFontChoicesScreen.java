package screens.fontchoicesscreen.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.fontchoicesscreen.FontChoicesScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidFontChoicesScreen extends FontChoicesScreen {
    public AndroidFontChoicesScreen() {
        super(By.id("reader_settings_container"));
    }
}
