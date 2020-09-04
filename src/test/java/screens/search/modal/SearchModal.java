package screens.search.modal;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class SearchModal extends Screen {
    protected SearchModal(By locator) {
        super(locator, "Search modal");
    }

    public abstract void setSearchedText(String text);
    public abstract void applySearch();
}
