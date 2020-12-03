package screens.addcustomopdsfeed;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class AddCustomOpdsFeedScreen extends Screen {
    public AddCustomOpdsFeedScreen(By locator) {
        super(locator, "Add custom OPDS");
    }

    public abstract void enterOpds(String opds);

    public abstract boolean isFeedAdded();
}
