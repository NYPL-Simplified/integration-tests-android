package screens.agegate.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.agegate.AgeGateScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAgeGateScreen extends AgeGateScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeStaticText[@name=\"Please enter your birth year\"]";
    public static final String DATE_TO_ENTER = "1990";

    private final ILabel pickerSelectYear = getElementFactory().getLabel(By.xpath("//XCUIElementTypePickerWheel"), "Age");
    private final IButton btnSelectYear =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeTextField[@value=\"Select Year\"]"), "Select Year");
    private final IButton btnNext =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Next\"]"), "Next");

    public IosAgeGateScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void approveAge() {
        btnSelectYear.click();
        pickerSelectYear.sendKeys(DATE_TO_ENTER);
        btnNext.click();
    }
}
