package screens.holds;

import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class AndroidHoldsScreen extends HoldsScreen {
    private final ILabel lblNoBooks = getElementFactory().getLabel(By.id("feedEmptyText"), "No Books Present");
    private final IButton btnCancel =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Cancel\"]"), "Cancel");
    private final String BOOK_INFO_LOCATOR_PATTERN = "//android.widget.ImageView[@content-desc=\"%s\"]";

    protected AndroidHoldsScreen() {
        super(By.xpath("//android.widget.TextView[@content-desc=\"Search in Holds…\"]"));
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
    public void cancelReservations() {
        btnCancel.click();
        btnCancel.state().waitForNotDisplayed();
    }
}
