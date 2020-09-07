package constants.android.catalog;

public enum AndroidBookAddButtonKeys {
    GET("Get Button"),
    DOWNLOAD("Download Button"),
    CANCEL("Cancel Reservation Button"),
    READ("Read Button"),
    RESERVE("Reserve Button");

    private final String key;

    AndroidBookAddButtonKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
