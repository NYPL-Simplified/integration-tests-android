package constants.localization.application.account;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum AccountScreenLoginStatus implements LocalizedValue {
    LOG_IN("login"),
    LOG_OUT("logout");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("account.AccountScreenLoginStatus");

    private final String key;


    AccountScreenLoginStatus(String key) {
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
