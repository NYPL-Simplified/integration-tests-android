package constants.localization.providers;

import lombok.NonNull;

import java.util.Locale;

public interface LocalizedValue {

    /**
     * Localized value in the default locale
     *
     * @return localized value
     */
    String i18n();

    /**
     * Localized value in the specific locale
     *
     * @param locale specific locale
     * @return localized value
     */
    String i18n(@NonNull Locale locale);
}
