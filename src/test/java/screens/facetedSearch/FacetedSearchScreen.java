package screens.facetedSearch;

import aquality.appium.mobile.screens.Screen;
import constants.localization.application.facetedSearch.FacetAvailabilityKeys;
import constants.localization.application.facetedSearch.FacetSortByKeys;
import org.openqa.selenium.By;

public abstract class FacetedSearchScreen extends Screen {
    protected FacetedSearchScreen(By locator) {
        super(locator, "Faceted search");
    }

    public abstract void openAvailabilityMenu();

    public abstract void changeAvailabilityTo(FacetAvailabilityKeys key);

    public abstract void sortBy();

    public abstract void changeSortByTo(FacetSortByKeys key);
}
