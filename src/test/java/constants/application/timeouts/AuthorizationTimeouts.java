package constants.application.timeouts;

public enum AuthorizationTimeouts {
    TIMEOUT_USER_LOGGED_OUT(120000);

    private long timeoutMillis;

    AuthorizationTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }
}