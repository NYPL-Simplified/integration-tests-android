package constants.localization.application.facetedSearch;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum FacetAvailabilityKeys implements LocalizedValue {
    ALL("all"),
    AVAILABLE_NOW("available_now"),
    YOURS_TO_KEEP("yours_to_keep");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("facetedSearch.FacetAvailabilityKeys");

    private final String key;


    FacetAvailabilityKeys(String key) {
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