package constants.localization.application.bookdetals;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum BookDetailsScreenInformationBlockKeys implements LocalizedValue {
    PUBLISHED("published"),
    PUBLISHER("publisher"),
    DISTRIBUTOR("distributor"),
    CATEGORIES("categories"),
    UPDATED("updated");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("bookdetals.BookDetailsScreenInformationBlockKeys");

    private final String key;


    BookDetailsScreenInformationBlockKeys(String key) {
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
