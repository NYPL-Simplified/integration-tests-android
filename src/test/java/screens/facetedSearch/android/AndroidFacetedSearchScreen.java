package screens.facetedSearch.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.facetedSearch.AndroidFacetAvailabilityKeys;
import org.openqa.selenium.By;
import screens.facetedSearch.FacetedSearchScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidFacetedSearchScreen extends FacetedSearchScreen {
    private static final String MAIN_ELEMENT = "//*[@resource-id=\"org.nypl.simplified.simplye:id/feedHeaderFacets\"]";
    private static final String AVAILABILITY_SELECTION = "//*[@resource-id=\"org.nypl.simplified.simplye:id/select_dialog_listview\"]"
            + "//*[@text=\"%1$s\"]";

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
    public void changeAvailabilityTo(AndroidFacetAvailabilityKeys key) {
        getElementFactory().getButton(By.xpath(String.format(AVAILABILITY_SELECTION, key.getKey())),
                "Availability type " + key.getKey()).click();
    }

}
