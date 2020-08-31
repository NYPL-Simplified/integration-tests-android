package screens.books;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBooksScreen extends BooksScreen {
    private final ILabel lblNoBooks = getElementFactory().getLabel(By.id("feedEmptyText"), "No Books Present");
    private final String BOOK_INFO_LOCATOR_PATTERN = "//android.widget.ImageView[@content-desc=\"%s\"]";

    public AndroidBooksScreen() {
        super(By.xpath("//android.widget.TextView[@content-desc=\"Search in Books…\"]"));
    }

    @Override
    public boolean isNoBooksMessagePresent() {
        return lblNoBooks.state().isDisplayed();
    }

    @Override
    public boolean isBookPresent(String bookInfo) {
        return getElementFactory().getLabel(By.xpath(String.format(BOOK_INFO_LOCATOR_PATTERN, bookInfo)), "No Books Present").state().isDisplayed();
    }
}
