package screens.bookDetails;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBookDetailsScreen extends BookDetailsScreen {
    private final IButton btnDownload =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Download Button\"]"), "Download");
    private final IButton btnRead =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Read Button\"]"), "Read");

    public AndroidBookDetailsScreen() {
        super(By.id("bookDetailCover"));
    }

    @Override
    public void downloadBook() {
        btnDownload.click();
        btnRead.state().waitForDisplayed();
    }
}
