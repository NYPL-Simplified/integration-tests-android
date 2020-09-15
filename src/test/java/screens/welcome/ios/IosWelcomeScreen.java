package screens.welcome.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.welcome.WelcomeScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosWelcomeScreen extends WelcomeScreen {
    private static final String MAIN_ELEMENT = "//*[./XCUIElementTypeImage "
            + "and .//XCUIElementTypeButton[@name=\"Find Your Library\"] "
            + "and .//XCUIElementTypeButton[@name=\"Add a Library Later\"]]";

    private final IButton addLibraryLater = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeButton[@name=\"Add a Library Later\"]"), "Add a Library Later");

    public IosWelcomeScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void addALibraryLater() {
        addLibraryLater.click();
    }
}
