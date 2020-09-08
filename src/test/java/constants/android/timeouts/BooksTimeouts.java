package constants.android.timeouts;

public enum BooksTimeouts {
    TIMEOUT_BOOK_CHANGES_STATUS(120000);

    private long timeoutMillis;

    BooksTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }
}