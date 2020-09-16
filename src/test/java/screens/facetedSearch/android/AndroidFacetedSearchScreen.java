package screens.facetedSearch.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.facetedSearch.FacetAvailabilityKeys;
import constants.localization.application.facetedSearch.FacetSortByKeys;
import org.openqa.selenium.By;
import screens.facetedSearch.FacetedSearchScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidFacetedSearchScreen extends FacetedSearchScreen {
    private static final String MAIN_ELEMENT = "//*[@resource-id=\"org.nypl.simplified.simplye:id/feedHeaderFacets\"]";
    private static final String FACET_SEARCH_SELECTION = "//*[@resource-id=\"org.nypl.simplified.simplye:id/select_dialog_listview\"]"
            + "//*[@text=\"%1$s\"]";
    private static final String SORTING_BUTTON_XPATH_PATTERN =
            "//android.widget.LinearLayout[@resource-id=\"org.nypl.simplified.simplye:id/feedHeaderFacets\"]/android.widget.Button";

    private final IButton btnSortBy = getElementFactory().getButton(By.xpath(SORTING_BUTTON_XPATH_PATTERN + "[2]"), "Sort By");
    private final IButton availabilityButton = getElementFactory().getButton(
            By.xpath(MAIN_ELEMENT + "/android.widget.TextView[contains(@text, \"Availability: \"]"
                    + "/following-sibling::android.widget.Button"),
            "Availability button");

    public AndroidFacetedSearchScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void openAvailabilityMenu() {
        availabilityButton.click();
    }

    @Override
    public void changeAvailabilityTo(FacetAvailabilityKeys key) {
        setFacetSearchSelection(key.i18n());
    }

    @Override
    public void sortBy() {
        btnSortBy.click();
    }

    @Override
    public void changeSortByTo(FacetSortByKeys key) {
        setFacetSearchSelection(key.i18n());
    }

    private void setFacetSearchSelection(String value) {
        getElementFactory().getButton(By.xpath(String.format(FACET_SEARCH_SELECTION, value)),
                "Facet search value " + value).click();
    }

}
