package constants.context;

public enum ContextLibrariesKeys {
    CANCEL_HOLD("librariesForCancelHold"),
    CANCEL_GET("librariesForCancelGet");

    private final String key;

    ContextLibrariesKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
