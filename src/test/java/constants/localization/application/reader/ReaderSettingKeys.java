package constants.localization.application.reader;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum ReaderSettingKeys implements LocalizedValue {
    SERIF("serif"),
    SANS_SERIF_ARIAL("sans_serif_arial"),
    ALTERNATIVE_SANS("alternative_sans"),
    WHITE_TEXT_ON_BLACK("white_text_on_black"),
    BLACK_TEXT_ON_WHITE("black_text_on_white"),
    BLACK_TEXT_ON_SEPIA("black_text_on_sepia"),
    DECREASE_FONT_SETTINGS("decrease_font_settings"),
    INCREASE_FONT_SETTINGS("increase_font_settings");

    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("reader.ReaderSettingKeys");
    private String key;

    ReaderSettingKeys(String key) {
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
