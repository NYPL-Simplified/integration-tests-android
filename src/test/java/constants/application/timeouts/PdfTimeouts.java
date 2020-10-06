package constants.application.timeouts;

public enum PdfTimeouts {
    PDF_LIST_OF_CHAPTERS_APPEAR_TIMEOUT(120000, 1000);

    private long timeoutMillis;

    private long pollingMillis;

    PdfTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    PdfTimeouts(long timeoutMillis, long pollingMillis) {
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