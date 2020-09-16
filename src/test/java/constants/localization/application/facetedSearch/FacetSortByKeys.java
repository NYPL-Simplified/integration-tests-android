package constants.localization.application.facetedSearch;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum FacetSortByKeys implements LocalizedValue {
    TITLE("title"),
    AUTHOR("author"),
    RANDOM("random"),
    RECENTLY_ADDED("recently_added");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("facetedSearch.FacetSortByKeys");

    private final String key;


    FacetSortByKeys(String key) {
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