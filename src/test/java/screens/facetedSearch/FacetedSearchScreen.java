package screens.facetedSearch;

import aquality.appium.mobile.screens.Screen;
import constants.application.facetedSearch.AndroidFacetAvailabilityKeys;
import org.openqa.selenium.By;

public abstract class FacetedSearchScreen extends Screen {
    protected FacetedSearchScreen(By locator) {
        super(locator, "Faceted search");
    }

    public abstract void openAvailabilityMenu();

    public abstract void changeAvailabilityTo(AndroidFacetAvailabilityKeys key);
}
