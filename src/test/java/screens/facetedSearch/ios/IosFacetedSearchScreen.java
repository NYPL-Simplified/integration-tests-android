package screens.facetedSearch.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.facetedSearch.AndroidFacetAvailabilityKeys;
import org.openqa.selenium.By;
import screens.facetedSearch.FacetedSearchScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosFacetedSearchScreen extends FacetedSearchScreen {

    private static final String MAIN_ELEMENT = "//XCUIElementTypeCollectionView/preceding-sibling::XCUIElementTypeOther";
    private static final String AVAILABILITY_SELECTION = "//XCUIElementTypeButton[@name=\"%1$s\"]";

    private final IButton availabilityButton = getElementFactory().getButton(
            By.xpath(String.format("(%1$s//XCUIElementTypeButton)[2]", MAIN_ELEMENT)),
            "Availability button");




    public IosFacetedSearchScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void openAvailabilityMenu() {
        availabilityButton.click();
    }

    @Override
    public void changeAvailabilityTo(AndroidFacetAvailabilityKeys key) {
        getElementFactory().getButton(By.xpath(String.format(AVAILABILITY_SELECTION, key.getKey())),
                "Availability type " + key.getKey()).click();
    }
}
