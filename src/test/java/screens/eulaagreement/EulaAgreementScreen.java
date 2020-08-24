package screens.eulaagreement;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class EulaAgreementScreen extends Screen {
    public EulaAgreementScreen(By locator) {
        super(locator, "Eula Agreement");
    }

    public abstract void clickAgree();
}
