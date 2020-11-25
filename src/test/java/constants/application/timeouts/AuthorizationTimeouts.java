package constants.application.timeouts;

public enum AuthorizationTimeouts {
    DEBUG_MENU_IS_OPENED(40000, 1000),
    USER_LOGGED_OUT(120000, 1000);

    private long timeoutMillis;

    private long pollingMillis;

    AuthorizationTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    AuthorizationTimeouts(long timeoutMillis, long pollingMillis) {
        this.timeoutMillis = timeoutMillis;
        this.pollingMillis = pollingMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public long getPollingMillis() {
        return pollingMillis;
    }
}