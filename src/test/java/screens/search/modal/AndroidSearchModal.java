package screens.search.modal;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSearchModal extends SearchModal {
    private final ITextBox txbSearch = getElementFactory().getTextBox(
            By.xpath("//*[@resource-id=\"org.nypl.simplified.simplye:id/searchDialogText\"]"),
            "Search value input");
    private final IButton btnSearch = getElementFactory().getButton(
            By.xpath("//*[@resource-id=\"org.nypl.simplified.simplye:id/buttonPanel\"]//android.widget.Button"),
            "Apply search button");

    public AndroidSearchModal() {
        super(By.xpath("//*[@resource-id=\"org.nypl.simplified.simplye:id/parentPanel\"]"));
    }

    @Override
    public void setSearchedText(String text) {
        txbSearch.sendKeys(text);
    }

    @Override
    public void applySearch() {
        btnSearch.click();
    }
}
