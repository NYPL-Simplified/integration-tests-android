package screens.alert.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.alert.AlertScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAlertScreen extends AlertScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeButton[contains(@name, \"trailing\")]";
    private final IButton btnOk =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name=\"OK\"]"), "OK");
    private final IButton btnNotNow =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name=\"Not Now\"]"), "Not Now");

    public IosAlertScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void accept() {
        getElementFactory().getButton(By.xpath(MAIN_ELEMENT), "Delete library").click();
    }

    @Override
    public void closeModalIfPresent() {
        if (btnOk.state().isDisplayed()) {
            btnOk.click();
        }
    }

    @Override
    public void closeNotNowModalIfPresent() {
        if (btnNotNow.state().isDisplayed()) {
            btnNotNow.click();
        }
    }
}
