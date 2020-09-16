package constants.application.bookdetals;

public enum AndroidBookDetailsScreenInformationBlockKeys {
    PUBLISHED("Published"),
    PUBLISHER("Publisher"),
    DISTRIBUTOR("Distributor"),
    CATEGORIES("Categories"),
    UPDATED("Updated");

    private final String key;

    AndroidBookDetailsScreenInformationBlockKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
