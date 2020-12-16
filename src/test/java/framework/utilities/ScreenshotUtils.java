package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import org.openqa.selenium.OutputType;

public class ScreenshotUtils {
    public static byte[] getScreenshot() {
        AqualityServices.getApplication().getDriver().getPageSource();
        return AqualityServices.getApplication().getDriver().getScreenshotAs(OutputType.BYTES);
    }
}
