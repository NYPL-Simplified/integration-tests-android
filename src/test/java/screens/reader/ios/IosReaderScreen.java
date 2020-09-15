package screens.reader.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.reader.ReaderScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosReaderScreen extends ReaderScreen {
    private final ILabel lblBookName =
            getElementFactory().getLabel(null, "Book Cover");

    public IosReaderScreen() {
        super(By.xpath(""));
    }

    @Override
    public String getBookName() {
        String text = lblBookName.getText();
        AqualityServices.getLogger().info("Book name - " + text);
        return text;
    }
}
