package screens.addcustomopdsfeed.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.addcustomopdsfeed.AddCustomOpdsFeedScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAddCustomOpdsFeedScreen extends AddCustomOpdsFeedScreen {
    protected IosAddCustomOpdsFeedScreen() {
        super(By.xpath(""));
    }

    @Override
    public void enterOpds(String opds) {
    }

    @Override
    public boolean isFeedAdded() {
        return false;
    }
}
