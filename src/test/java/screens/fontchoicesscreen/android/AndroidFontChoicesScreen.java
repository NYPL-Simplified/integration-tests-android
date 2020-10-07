package screens.fontchoicesscreen.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.reader.ReaderSettingKeys;
import org.openqa.selenium.By;
import screens.fontchoicesscreen.FontChoicesScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidFontChoicesScreen extends FontChoicesScreen {
    private static final String MAIN_ELEMENT_ID = "reader_settings_container";

    public AndroidFontChoicesScreen() {
        super(By.id(MAIN_ELEMENT_ID));
    }

    @Override
    public void setSetting(ReaderSettingKeys increaseFontSettings) {
        String setting = increaseFontSettings.i18n();
        getElementFactory().getButton(By.id(setting), setting).click();
    }

    @Override
    public void closeFontChoices() {
        AqualityServices.getApplication().getDriver().navigate().back();
    }
}
