package screens.alert.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.alert.AlertScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAlertScreen extends AlertScreen {
    private final IButton btnAccept = getElementFactory().getButton(By.id("android:id/button1"), "Accept");

    public AndroidAlertScreen() {
        super(By.id("android:id/message"));
    }

    @Override
    public void accept() {
        btnAccept.click();
    }
}
