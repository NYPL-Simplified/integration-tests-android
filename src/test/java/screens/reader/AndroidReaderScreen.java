package screens.reader;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

import java.util.Set;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidReaderScreen extends ReaderScreen {
    private final ILabel lblCover =
            getElementFactory().getLabel(By.xpath("//android.view.View[@resource-id=\"cover-image\"]"), "Book Cover");
    private final ILabel lblItem =
            getElementFactory().getLabel(By.xpath("//android.webkit.WebView"), "Book Cover");

    public AndroidReaderScreen() {
        super(By.id("//android.view.View[@resource-id=\"reflowable-book-frame\"]"));
    }

    public boolean isBookCoverPresent() {
        //lblItem.state().waitForDisplayed();
        //lblItem.state().waitForExist();
        AqualityServices.getConditionalWait().waitFor(() -> {
            Set<String> contextNames = AqualityServices.getApplication().getDriver().getContextHandles();
            for (String contextName : contextNames) {
                AqualityServices.getLogger().info("context - " + contextName); //prints out something like NATIVE_APP \n WEBVIEW_1
            }
            return contextNames.size() > 1;
        });
        Set<String> contextNames = AqualityServices.getApplication().getDriver().getContextHandles();
        AqualityServices.getLogger().info(AqualityServices.getApplication().getDriver().getPageSource());
        AqualityServices.getApplication().getDriver().context((String) contextNames.toArray()[1]);
        boolean isLabelPresent = lblCover.state().waitForExist();
        AqualityServices.getApplication().getDriver().context((String) contextNames.toArray()[0]);
        return isLabelPresent;
    }
}
