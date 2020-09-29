package screens.fontchoicesscreen;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.reader.ReaderSettingKeys;
import org.openqa.selenium.By;

public abstract class FontChoicesScreen extends Screen {
    protected FontChoicesScreen(By locator) {
        super(locator, "Font choices");
    }

    public abstract void setSetting(ReaderSettingKeys increaseFontSettings);

    public abstract void closeFontChoices();
}
