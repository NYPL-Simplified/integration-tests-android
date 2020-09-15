package screens.search.modal.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.search.modal.SearchModal;

@ScreenType(platform = PlatformName.IOS)
public class IosSearchModal extends SearchModal {
    private final ITextBox txbSearch = getElementFactory().getTextBox(
            null,
            "Search value input");
    private final IButton btnSearch = getElementFactory().getButton(
            null,
            "Apply search button");

    public IosSearchModal() {
        super(By.xpath(""));
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
