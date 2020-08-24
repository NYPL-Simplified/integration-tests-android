package screens.bottommenu;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBottomMenuForm extends BottomMenuForm {
    public AndroidBottomMenuForm() {
        super(By.id("bottomNavigator"));
    }

    @Override
    public void open(BottomMenu bottomMenuItem) {
        getElementFactory().getButton(By.id(bottomMenuItem.toString()), bottomMenuItem.toString()).click();
    }
}
