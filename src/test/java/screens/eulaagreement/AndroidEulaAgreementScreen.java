package screens.eulaagreement;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidEulaAgreementScreen extends EulaAgreementScreen {
    private final IButton btnAgree = getElementFactory().getButton(By.id("splashEulaAgree"), "Eula Agree");

    public AndroidEulaAgreementScreen() {
        super(By.id("splashEulaAgree"));
    }

    @Override
    public void clickAgree() {
        btnAgree.click();
    }
}
