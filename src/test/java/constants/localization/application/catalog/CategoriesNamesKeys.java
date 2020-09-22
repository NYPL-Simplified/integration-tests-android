package constants.localization.application.catalog;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum CategoriesNamesKeys implements LocalizedValue  {
    TITLE_RECOMMENDED_BY_NOVELIST("titleRecommendedByNovelist");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("catalog.CategoriesNamesKeys");

    private final String key;


    CategoriesNamesKeys(String key) {
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