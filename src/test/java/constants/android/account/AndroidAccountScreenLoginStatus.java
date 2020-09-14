package constants.android.account;

public enum AndroidAccountScreenLoginStatus {
    LOG_IN("Log in"),
    LOG_OUT("Log out");

    private final String status;

    AndroidAccountScreenLoginStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
