package framework.configuration;

public class Configuration {

    private Configuration() {
    }

    public static String getEbookCardValue() {
        return Environment.getEnvironment().getValue("/ebookcard").toString();
    }

    public static String getEbookPinValue() {
        return Environment.getEnvironment().getValue("/ebookpin").toString();
    }
}
