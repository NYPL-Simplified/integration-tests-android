package constants.android.catalog;

public enum AndroidBookActionButtonKeys {
    GET("Get Button"),
    DOWNLOAD("Download Button"),
    CANCEL("Cancel Reservation Button"),
    READ("Read Button"),
    RESERVE("Reserve Button"),
    DELETE("Delete Button"),
    RETURN("Return");

    private final String key;

    AndroidBookActionButtonKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}