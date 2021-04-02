package framework.utilities.feedXMLUtil.xml;

import java.io.Serializable;
import java.util.Objects;

public class BookModel implements Serializable {
    private String distributorName;
    private String bookType;
    private String bookName;
    private int countAvailableCopies;

    public BookModel(String distributorName, String bookType, String bookName, int countAvailableCopies) {
        this.distributorName = distributorName;
        this.bookType = bookType;
        this.bookName = bookName;
        this.countAvailableCopies = countAvailableCopies;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public String getBookType() {
        return bookType;
    }

    public String getBookName() {
        return bookName;
    }

    public int getCountAvailableCopies() {
        return countAvailableCopies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookModel bookModel = (BookModel) o;
        return Objects.equals(bookName, bookModel.bookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookName);
    }
}
