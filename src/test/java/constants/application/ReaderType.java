package constants.application;

public enum ReaderType {
    EBOOK("Book"),
    AUDIOBOOK("Audio");

    private String bookType;

    @Override
    public String toString() {
        return bookType;
    }

    ReaderType(String bookType) {
        this.bookType = bookType;
    }
}
