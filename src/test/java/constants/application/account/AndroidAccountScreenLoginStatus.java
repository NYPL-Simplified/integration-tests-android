package constants.application.account;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;

public enum AndroidAccountScreenLoginStatus {
    LOG_IN("Log in", "Log In"),
    LOG_OUT("Log out", "Sign Out");

    private static final PlatformName platformName = AqualityServices.getApplication().getPlatformName();

    private final String androidKey;
    private final String iosKey;

    AndroidAccountScreenLoginStatus(String androidKey, String iosKey) {
        this.androidKey = androidKey;
        this.iosKey = iosKey;
    }

    public String getStatus() {
        return platformName.equals(PlatformName.ANDROID) ? androidKey : iosKey;

    }
}
