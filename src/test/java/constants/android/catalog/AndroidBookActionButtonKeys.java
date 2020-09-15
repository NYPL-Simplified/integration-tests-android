package constants.android.catalog;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;

public enum AndroidBookActionButtonKeys {
    GET("Get Button", "Get"),
    DOWNLOAD("Download Button", "Get"),
    CANCEL("Cancel Reservation Button", "Cancel"),
    READ("Read Button", "Read"),
    RESERVE("Reserve Button", "Reserve"),
    DELETE("Delete Button", "Delete"),
    RETURN("Return Now Button", "Return");

    private static final PlatformName platformName = AqualityServices.getApplication().getPlatformName();

    private String androidKey;
    private String iosKey;


    AndroidBookActionButtonKeys(String androidKey, String iosKey) {
        this.androidKey = androidKey;
        this.iosKey = iosKey;
    }

    public String getKey() {
        return platformName.equals(PlatformName.ANDROID) ? androidKey : iosKey;
    }
}