package constants.localization.application.catalog;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum TimerKeys implements LocalizedValue {
    END_OF_CHAPTER("endOfChapter");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("catalog.TimerKeys");
    private final String key;

    TimerKeys(String key) {
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
