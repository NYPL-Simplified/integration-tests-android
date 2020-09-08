package constants.android.timeouts;

public enum AuthorizationTimeouts {
    TIMEOUT_USER_LOGGED_OUT(60000);

    private long timeoutMillis;

    AuthorizationTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }
}