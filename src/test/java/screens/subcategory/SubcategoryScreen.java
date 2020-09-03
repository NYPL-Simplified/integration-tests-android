package screens.subcategory;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class SubcategoryScreen extends Screen {
    protected SubcategoryScreen(By locator) {
        super(locator, "Subcategory");
    }

    public abstract String getSubcategoryName();

    public abstract String getFirstBookInfo();

    public abstract void openFirstBook();
}
