package screens.alert.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.alert.AlertScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAlertScreen extends AlertScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeButton[contains(@name, \"trailing\")]";

    public IosAlertScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void accept() {
        getElementFactory().getButton(By.xpath(MAIN_ELEMENT), "Delete library").click();
    }
}
