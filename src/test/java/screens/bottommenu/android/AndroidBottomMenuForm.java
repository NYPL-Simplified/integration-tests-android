package screens.bottommenu.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBottomMenuForm extends BottomMenuForm {
    public AndroidBottomMenuForm() {
        super(By.id("bottomNavigator"));
    }

    @Override
    public void open(BottomMenu bottomMenuItem) {
        getElementFactory().getButton(By.id(bottomMenuItem.getItemName()), bottomMenuItem.getItemName()).click();
    }

    @Override
    public boolean isBottomMenuBtnVisible(BottomMenu bottomMenuItem) {
        return getElementFactory().getButton(By.id(bottomMenuItem.getItemName()), bottomMenuItem.getItemName()).state().isDisplayed();
    }
}
