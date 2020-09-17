package screens.eulaagreement.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.eulaagreement.EulaAgreementScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosEulaAgreementScreen extends EulaAgreementScreen {
    private final IButton btnAgree = getElementFactory().getButton(null, "Eula Agree");

    public IosEulaAgreementScreen() {
        super(By.xpath(""));
    }

    @Override
    public void clickAgree() {
        btnAgree.click();
    }

    @Override
    public boolean isVanillaApp() {
        return false;
    }
}
