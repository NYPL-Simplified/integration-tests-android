package screens.search.page;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class SearchPage extends Screen {
    protected SearchPage(By locator) {
        super(locator, "Search page");
    }

    public abstract void selectFirstFoundBook();
}
