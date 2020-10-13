package screens.pdfsearch;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.util.List;

public abstract class PdfSearchScreen extends Screen {
    protected PdfSearchScreen(By locator) {
        super(locator, "Pdf search");
    }

    public abstract void findTextInTheDocument(String textToBeFound);

    public abstract boolean isFoundItemsExist();

    public abstract List<String> getListOfFoundItems();

    public abstract void openSearchedItemByName(String itemName);

    public abstract String getSearchedItemPageNumber(String itemName);
}
