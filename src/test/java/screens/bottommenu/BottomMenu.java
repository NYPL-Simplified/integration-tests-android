package screens.bottommenu;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;

public enum BottomMenu {
    SETTINGS("tabSettings", "Settings"),
    HOLDS("tabHolds", null),
    BOOKS("tabBooks", "My Books"),
    CATALOG("tabCatalog", "Catalog");

    private static final PlatformName platformName = AqualityServices.getApplication().getPlatformName();

    private String androidCatalogName;
    private String iosCatalogName;

    BottomMenu(String androidCatalogName, String iosCatalogName) {
        this.androidCatalogName = androidCatalogName;
        this.iosCatalogName = iosCatalogName;
    }

    public String getItemName() {
        return platformName.equals(PlatformName.ANDROID) ? androidCatalogName : iosCatalogName;
    }
}
