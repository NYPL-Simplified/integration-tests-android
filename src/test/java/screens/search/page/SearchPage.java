package screens.search.page;

import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.util.List;

public abstract class SearchPage extends Screen {
    protected SearchPage(By locator) {
        super(locator, "Search page");
    }

    public abstract void selectFirstFoundBook();

    protected abstract List<ILabel> getFoundBooks();

    public abstract int getFoundBooksCount();
}
