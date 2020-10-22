package screens.catalog.form;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class MainCatalogToolbarForm extends Screen {
    protected MainCatalogToolbarForm(By locator) {
        super(locator, "Main catalog toolbar");
    }

    public abstract void chooseAnotherLibrary();

    public abstract void goBack();

    public abstract String getCatalogName();

    public abstract String getCategoryName();

    public abstract void openSearchModal();
}
