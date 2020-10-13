package constants.localization.application.reader;

import constants.localization.providers.AbstractILocalizationProvider;
import constants.localization.providers.LocalizationProviderFactory;
import constants.localization.providers.LocalizedValue;
import lombok.NonNull;

import java.util.Locale;

public enum PdfButtons implements LocalizedValue {
    CHAPTERS("chapters"),
    SEARCH("search");

    private String color;
    private static final AbstractILocalizationProvider localizationProvider =
            LocalizationProviderFactory.getProvider("reader.PdfButtons");

    PdfButtons(String color) {
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
