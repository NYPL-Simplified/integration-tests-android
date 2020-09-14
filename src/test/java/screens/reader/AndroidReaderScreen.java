package screens.reader;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidReaderScreen extends ReaderScreen {
    private final ILabel lblBookName =
            getElementFactory().getLabel(By.xpath("//android.widget.TextView[@resource-id=\"org.nypl.simplified.simplye:id/reader_title_text\"]"), "Book Cover");

    public AndroidReaderScreen() {
        super(By.id("//android.view.View[@resource-id=\"reflowable-book-frame\"]"));
    }


    @Override
    public String getBookName() {
        String text = lblBookName.getText();
        AqualityServices.getLogger().info("Book name - " + text);
        return text;
    }
}
