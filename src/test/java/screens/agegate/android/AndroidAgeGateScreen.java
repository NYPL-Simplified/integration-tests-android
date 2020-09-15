package screens.agegate.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.agegate.AgeGateScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAgeGateScreen extends AgeGateScreen {
    private final IButton btnApproveAge = getElementFactory().getButton(By.id("feedAgeGateOver"), "Approve Age");

    public AndroidAgeGateScreen() {
        super(By.id("feedCOPPAGate"));
    }

    @Override
    public void approveAge() {
        btnApproveAge.click();
    }
}
