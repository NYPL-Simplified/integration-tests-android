package screens.addaccounts;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAddAccountScreen extends AddAccountScreen {
    public AndroidAddAccountScreen() {
        super(By.id("accountRegistryTitle"));
    }

    @Override
    public void selectLibrary(String libraryName) {
        getElementFactory().getButton(By.xpath("//android.widget.TextView[contains(@text, '" + libraryName + "')]"), libraryName).click();
    }
}
