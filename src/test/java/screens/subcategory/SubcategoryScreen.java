package screens.subcategory;

import aquality.appium.mobile.screens.Screen;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

import java.util.List;

public abstract class SubcategoryScreen extends Screen {
    protected SubcategoryScreen(By locator) {
        super(locator, "Subcategory");
    }

    public abstract String getSubcategoryName();

    public abstract String getFirstBookInfo();

    public abstract void openFirstBook();

    public abstract void sortBy(String sortingCategory);

    public abstract List<String> getBooksInfo();

    public abstract void sortByAvailability(String sortingCategory);

    public abstract List<String> getAllButtonsNames();

    public abstract List<String> getAuthorsInfo();

    public abstract List<String> getTitlesInfo();

    public abstract void openBook(AndroidCatalogBookModel bookInfo);
}
