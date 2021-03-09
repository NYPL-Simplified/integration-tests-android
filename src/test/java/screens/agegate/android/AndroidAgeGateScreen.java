package screens.agegate.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.agegate.AgeGateScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAgeGateScreen extends AgeGateScreen {
    private final IButton btnApproveAge = getElementFactory().getButton(By.xpath("//android.widget.Button[@text=\"DONE\"]"), "Approve Age");
    private final IButton btnOpenSpinner = getElementFactory().getButton(By.id("birthYearSpinner"), "Spinner");
    private final IButton btnDate = getElementFactory().getButton(By.xpath("//android.widget.ListView/android.widget.TextView[@text=\"1990\"]"), "1990");

    public AndroidAgeGateScreen() {
        super(By.id("birthYearPromptText"));
    }

    @Override
    public void approveAge() {
        btnOpenSpinner.click();
        btnDate.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        btnDate.click();
        btnApproveAge.click();
    }
}
