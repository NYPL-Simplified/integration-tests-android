package constants.application.timeouts;

public enum CategoriesTimeouts {
    TIMEOUT_WAIT_UNTIL_CATEGORY_PAGE_LOAD(180000);

    private long timeoutMillis;

    CategoriesTimeouts(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }
}