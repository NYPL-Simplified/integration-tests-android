package framework.configuration;

public class Credentials {
    private String barcode;
    private String pin;

    public Credentials(String barcode, String pin) {
        this.barcode = barcode;
        this.pin = pin;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
