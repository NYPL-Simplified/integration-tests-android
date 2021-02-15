package screens.facetedSearch.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.localization.application.facetedSearch.FacetAvailabilityKeys;
import constants.localization.application.facetedSearch.FacetSortByKeys;
import org.openqa.selenium.By;
import screens.facetedSearch.FacetedSearchScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosFacetedSearchScreen extends FacetedSearchScreen {

    private static final String MAIN_ELEMENT = "//XCUIElementTypeCollectionView/preceding-sibling::XCUIElementTypeOther";
    private static final String FACET_SEARCH_SELECTION = "//XCUIElementTypeButton[@name=\"%1$s\"]";

    private final IButton availabilityButton =
            getElementFactory().getButton(By.xpath(String.format("(%1$s//XCUIElementTypeButton)[2]", MAIN_ELEMENT)), "Availability");
    private final IButton btnSortBy =
            getElementFactory().getButton(By.xpath(String.format("(%1$s//XCUIElementTypeButton)[1]", MAIN_ELEMENT)), "Sort by");

    public IosFacetedSearchScreen() {
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
