package screens.books;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class BooksScreen extends Screen {
    protected BooksScreen(By locator) {
        super(locator, "Books");
    }

    public abstract boolean isNoBooksMessagePresent();

    public abstract boolean isBookPresent(String bookInfo);

    public abstract int getCountOfBooks();

    public abstract void refreshList();
}
