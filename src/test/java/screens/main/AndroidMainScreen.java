package screens.main;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidMainScreen extends MainScreen {
    private final IButton agreeBtn = getElementFactory().getButton(By.id("selectionAlternateButton"), "Add a Library Later");

    public AndroidMainScreen() {
        super(By.id("selectionAlternateButton"));
    }

    @Override
    public void addALibraryLater() {
        agreeBtn.click();
    }
}
