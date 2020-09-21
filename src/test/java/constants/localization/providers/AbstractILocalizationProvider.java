package constants.localization.providers;

import aquality.appium.mobile.application.AqualityServices;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractILocalizationProvider implements ILocalizationProvider {

    private static final String OS = AqualityServices.getApplication().getPlatformName().toString().toLowerCase();
    private static final String LOCALIZATION_PATH_PREFIX = "localization";
    private static final String LOCALIZATION_PATH_DELIMETER = ".";
    protected static final Locale DEFAULT_LOCALE = new Locale(OS, OS.toUpperCase());
    private final LocalizationController localizationController;

    /**
     * bundles cache
     */
    private final Map<Locale, ResourceBundle> bundles = new ConcurrentHashMap<>();

    /**
     * path to the file from the resources package.
     */
    private final String filename;

    public AbstractILocalizationProvider(@NonNull String filename) {
        localizationController = new LocalizationController(StandardCharsets.UTF_8);
        this.filename = filename;
        bundles.put(DEFAULT_LOCALE, ResourceBundle
                .getBundle(StringUtils.joinWith(LOCALIZATION_PATH_DELIMETER, LOCALIZATION_PATH_PREFIX, filename),
                        DEFAULT_LOCALE, localizationController));
    }

    @Override
    public String getLocalization(@NonNull String key) {
        return getLocalization(key, DEFAULT_LOCALE);
    }

    @Override
    public String getLocalization(@NonNull String key, @NonNull Locale locale) {
        return bundles.computeIfAbsent(locale, loc -> ResourceBundle
                .getBundle(StringUtils.joinWith(LOCALIZATION_PATH_DELIMETER, LOCALIZATION_PATH_PREFIX, filename),
                        loc, localizationController)).getString(key);
    }
}
