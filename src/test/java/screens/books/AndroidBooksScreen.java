package screens.books;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

import java.util.List;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBooksScreen extends BooksScreen {
    private final ILabel lblNoBooks = getElementFactory().getLabel(By.id("feedEmptyText"), "No Books Present");
    private final IButton btnMenu =
            getElementFactory().getButton(By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"), "Menu");
    private final IButton btnRefresh = getElementFactory().getButton(By.id("title"), "Refresh");
    private final String BOOK_INFO_LOCATOR_PATTERN = "//android.widget.ImageView[@content-desc=\"%s\"]";
    private final List<IElement> booksList = getElementFactory().findElements(By.xpath("//android.widget.ImageView[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleCover\"]"), ElementType.LABEL);

    public AndroidBooksScreen() {
        super(By.xpath("//android.widget.TextView[@content-desc=\"Search in Books…\"]"));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookPresent(String bookInfo) {
        return getElementFactory().getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo)), "No Books Present").state().waitForDisplayed();
    }

    @Override
    public int getCountOfBooks() {
        return booksList.size();
    }

    @Override
    public void refreshList() {
        btnMenu.click();
        btnRefresh.click();
    }
}
