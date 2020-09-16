package constants.application.account;

public enum AndroidAccountScreenLoginFields {
    BARCODE("Barcode"),
    PIN("PIN");

    private final String name;

    AndroidAccountScreenLoginFields(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
