package screens.catalog.screen.catalog;

import aquality.appium.mobile.screens.Screen;
import constants.application.ReaderType;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Set;

public abstract class CatalogScreen extends Screen {
    protected CatalogScreen(By locator) {
        super(locator, "Catalog");
    }

    public abstract List<String> getListOfBooksNames();

    public abstract boolean isCategoryPageLoad();

    public abstract void openLibrary(String libraryName);

    public abstract String getBookName(int index);

    public abstract void clickBook(int index);

    public abstract void openCategory(String categoryName);

    public abstract boolean isSubcategoryPresent(String subcategoryName);

    public abstract Set<String> getListOfAllBooksNamesInFirstLane();

    public abstract void switchToCatalogTab(String catalogTab);

    public abstract Set<String> getListOfAllBooksNamesInSubcategoryLane(String lineName);

    public abstract void openFirstBookFromLane(ReaderType readerType, String laneName);

    public abstract boolean isAnyBookPresentInLane(ReaderType readerType, String laneName);

    public abstract void openFirstBookOfType(ReaderType readerType, String bookInfoKey);
}
