package constants.localization.application.reader;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum FontNameKeys implements LocalizedValue {
    SERIF_FONT_NAME("serif"),
    SANS_SERIF_FONT_NAME("sans-serif"),
    ALTERNATIVE_SANS_FONT_NAME("openDyslexic3");

    private String color;
    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("reader.FontNameKeys");

    FontNameKeys(String color) {
        this.color = color;
    }

    @Override
    public String i18n() {
        return localizationProvider.getLocalization(color);
    }

    @Override
    public String i18n(@NonNull Locale locale) {
        return localizationProvider.getLocalization(color, locale);
    }
}
