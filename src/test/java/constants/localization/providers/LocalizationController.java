package constants.localization.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LocalizationController extends ResourceBundle.Control {

    private static final String FILE_SUFFIX = "properties";
    private final Charset charset;

    public LocalizationController(Charset charset) {
        this.charset = charset;
    }

    public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                    ClassLoader loader, boolean reload) throws IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, FILE_SUFFIX);
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
            try {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, charset));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }
}
