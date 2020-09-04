package screens.bookDetails;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBookDetailsScreen extends BookDetailsScreen {
    public static final String CONTENT_ATTRIBUTE_NAME = "content-desc";
    private final IButton btnDownload =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Download Button\"]"), "Download");
    private final IButton btnRead =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Read Button\"]"), "Read");
    private final IButton btnReserve =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Reserve Button\"]"), "Read");
    private final ILabel lblBookInfo = getElementFactory().getLabel(By.id("bookDetailCoverImage"), "Cover Image");

    public AndroidBookDetailsScreen() {
        super(By.id("bookDetailCover"));
    }

    @Override
    public void downloadBook() {
        btnDownload.click();
        btnRead.state().waitForDisplayed();
    }

    @Override
    public void reserveBook() {
        btnReserve.click();
    }

    @Override
    public String getBookInfo() {
        return lblBookInfo.getAttribute(CONTENT_ATTRIBUTE_NAME);
    }
}
