package screens.catalog;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.util.List;

public abstract class CatalogScreen extends Screen {
    protected CatalogScreen(By locator) {
        super(locator, "Catalog");
    }

    public abstract List<String> getListOfBooksNames();

    public abstract void openLibrary(String libraryName);

    public abstract String getBookName(int index);

    public abstract void clickBook(int index);
}
