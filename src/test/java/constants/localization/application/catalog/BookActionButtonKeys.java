package constants.localization.application.catalog;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum BookActionButtonKeys implements LocalizedValue  {
    GET("get"),
    DOWNLOAD("download"),
    CANCEL("cancel"),
    READ("read"),
    RESERVE("reserve"),
    DELETE("delete"),
    RETURN("return"),
    LISTEN("listen"),
    CANCEL_POPUP("cancelPopup");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("catalog.BookActionButtonKeys");

    private final String key;


    BookActionButtonKeys(String key) {
        this.key = key;
    }

    @Override
    public String i18n() {
        return localizationProvider.getLocalization(key);
    }

    @Override
    public String i18n(@NonNull Locale locale) {
        return localizationProvider.getLocalization(key, locale);
    }
}