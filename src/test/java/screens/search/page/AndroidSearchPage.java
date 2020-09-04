package screens.search.page;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSearchPage extends SearchPage {
    private final ILabel lblFirstFoundBook = getElementFactory().getLabel(
            By.xpath("//*[@rresource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\"]"), "First found book");

    public AndroidSearchPage() {
        super(By.xpath("//*[@resource-id=\"org.nypl.simplified.simplye:id/feedWithoutGroupsList\"]"));
    }

    @Override
    public void selectFirstFoundBook() {
        lblFirstFoundBook.click();
    }
}
