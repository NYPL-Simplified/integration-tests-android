package screens.search.form;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class SearchForm extends Screen {
    protected SearchForm(By locator) {
        super(locator, "Search");
    }

    public abstract void openSearchModal();
}
