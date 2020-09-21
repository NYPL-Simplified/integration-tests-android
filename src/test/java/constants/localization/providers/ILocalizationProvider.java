package constants.localization.providers;

import lombok.NonNull;

import java.util.Locale;


public interface ILocalizationProvider {

    /**
     * getting localization for the default locale
     *
     * @param key
     * @return
     */
    String getLocalization(@NonNull String key);

    /**
     * getting localization by key for the specific locale
     *
     * @param key
     * @param locale
     * @return
     */
    String getLocalization(@NonNull String key, @NonNull Locale locale);

}
