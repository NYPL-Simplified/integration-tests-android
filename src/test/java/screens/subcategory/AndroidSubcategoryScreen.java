package screens.subcategory;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSubcategoryScreen extends SubcategoryScreen {
    private final ILabel lblSubcategoryName =
            getElementFactory().getLabel(By.xpath("//android.view.ViewGroup[@resource-id=\"org.nypl.simplified.simplye:id/mainToolbar\"]//android.widget.TextView[1]"), "Category name");
    private final ILabel lblFirstBookInfo =
            getElementFactory().getLabel(By.xpath("//android.widget.ImageView[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleCover\"]"), "First book info");

    public AndroidSubcategoryScreen() {
        super(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"org.nypl.simplified.simplye:id/feedWithoutGroupsList\"]"));
    }

    @Override
    public String getSubcategoryName() {
        return lblSubcategoryName.getText();
    }

    @Override
    public String getFirstBookInfo() {
        return lblFirstBookInfo.getAttribute("content-desc");
    }

    @Override
    public void openFirstBook() {
        lblFirstBookInfo.click();
    }
}
