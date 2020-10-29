package screens.bottommenu.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;

@ScreenType(platform = PlatformName.IOS)
public class IosBottomMenuForm extends BottomMenuForm {
    private static final String BOTTOM_MENU_ELEMENT_PATTERN_LOC = "//XCUIElementTypeButton[@name=\"%1$s\"]";
    private static final String MAIN_ELEMENT = String.format(BOTTOM_MENU_ELEMENT_PATTERN_LOC, BottomMenu.CATALOG.getItemName())
            + "/parent::XCUIElementTypeTabBar";

    public IosBottomMenuForm() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void open(BottomMenu bottomMenuItem) {
        getButton(bottomMenuItem).click();
    }

    @Override
    public boolean isBottomMenuBtnVisible(BottomMenu bottomMenuItem) {
        return getButton(bottomMenuItem).state().isDisplayed();
    }

    private IButton getButton(BottomMenu bottomMenuItem) {
        String itemName = bottomMenuItem.getItemName();
        return getElementFactory().getButton(By.xpath(String.format(BOTTOM_MENU_ELEMENT_PATTERN_LOC, itemName)), itemName);
    }
}
