package constants.application.timeouts;

public enum AudioBookTimeouts {
    TIMEOUT_AUDIO_BOOK_LOADER_DISAPPEAR(120000);

    private long timeoutMillis;

    private long pollingMillis;

    AudioBookTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    AudioBookTimeouts(long timeoutMillis, long pollingMillis) {
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