package constants.application.timeouts;

public enum BooksTimeouts {
    TIMEOUT_BOOK_CHANGES_STATUS(120000),
    SYSTEM_CHANGES_STATUS(5000),
    TIMEOUT_BOOK_PAGE_LOADED(240000);

    private long timeoutMillis;

    BooksTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }
}