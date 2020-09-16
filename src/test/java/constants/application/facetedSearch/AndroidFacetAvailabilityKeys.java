package constants.application.facetedSearch;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;

public enum AndroidFacetAvailabilityKeys {
    ALL("All", "All"),
    AVAILABLE_NOW("Available now", "Available now"),
    YOURS_TO_KEEP("Yours to keep", "Yours to keep");

    private static final PlatformName platformName = AqualityServices.getApplication().getPlatformName();

    private String androidKey;
    private String iosKey;


    AndroidFacetAvailabilityKeys(String androidKey, String iosKey) {
        this.androidKey = androidKey;
        this.iosKey = iosKey;
    }

    public String getKey() {
        return platformName.equals(PlatformName.ANDROID) ? androidKey : iosKey;
    }
}