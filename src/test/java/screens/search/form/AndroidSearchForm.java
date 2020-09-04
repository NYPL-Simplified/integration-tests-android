package screens.search.form;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSearchForm extends SearchForm {
    public AndroidSearchForm() {
        super(By.xpath("//*[@resource-id=\"org.nypl.simplified.simplye:id/catalogMenuActionSearch\"]"));
    }

    @Override
    public void openSearchModal() {
        getElementFactory().getButton(getLocator(), "Search button");
    }
}
