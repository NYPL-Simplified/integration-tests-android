package screens.fontchoicesscreen.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.reader.ReaderSettingKeys;
import framework.utilities.CoordinatesClickUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
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
        ILabel fontChoicesScreen = getElementFactory().getLabel(By.id(MAIN_ELEMENT_ID), "Font choices screen");
        CoordinatesClickUtils.clickOutOfTheElement(fontChoicesScreen);
    }
}
