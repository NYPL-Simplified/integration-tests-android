package screens.search.screen;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

import java.util.List;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSearchPage extends SearchPage {
    private static final String BOOKS_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\"]";

    private final ILabel lblFirstFoundBook = getElementFactory().getLabel(
            By.xpath(BOOKS_LOC), "First found book");

    public AndroidSearchPage() {
        super(By.xpath("//*[@resource-id=\"org.nypl.simplified.simplye:id/feedWithoutGroupsList\"]"));
    }

    @Override
    public void selectFirstFoundBook() {
        lblFirstFoundBook.click();
    }

    @Override
    protected List<ILabel> getFoundBooks() {
        return getElementFactory().findElements(By.xpath(BOOKS_LOC), ElementType.LABEL);

    }

    @Override
    public int getFoundBooksCount() {
        return getFoundBooks().size();
    }


}
