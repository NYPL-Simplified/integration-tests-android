package screens.agegate.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.agegate.AgeGateScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAgeGateScreen extends AgeGateScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeAlert[@name=\"Age Verification\"]";

    private final IButton btnApproveAge = getElementFactory().getButton(
            By.xpath(MAIN_ELEMENT + "//XCUIElementTypeButton[@name=\"13 or Older\"]"), "Approve Age");

    public IosAgeGateScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void approveAge() {
        btnApproveAge.click();
    }
}
