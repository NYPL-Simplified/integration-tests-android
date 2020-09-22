package screens.fontchoicesscreen.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.reader.ReaderSettingKeys;
import org.openqa.selenium.By;
import screens.fontchoicesscreen.FontChoicesScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidFontChoicesScreen extends FontChoicesScreen {
    public AndroidFontChoicesScreen() {
        super(By.id("reader_settings_container"));
    }

    @Override
    public void setSetting(ReaderSettingKeys increaseFontSettings) {
        String setting = increaseFontSettings.i18n();
        getElementFactory().getButton(By.id(setting), setting).click();
    }
}
