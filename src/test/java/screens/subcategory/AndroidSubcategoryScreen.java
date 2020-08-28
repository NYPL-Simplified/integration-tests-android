package screens.subcategory;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSubcategoryScreen extends SubcategoryScreen {
    public AndroidSubcategoryScreen() {
        super(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"org.nypl.simplified.simplye:id/feedWithoutGroupsList\"]"));
    }
}
