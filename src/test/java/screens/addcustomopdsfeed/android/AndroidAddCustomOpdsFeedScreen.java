package screens.addcustomopdsfeed.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.AuthorizationTimeouts;
import org.openqa.selenium.By;
import screens.addcustomopdsfeed.AddCustomOpdsFeedScreen;

import java.time.Duration;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAddCustomOpdsFeedScreen extends AddCustomOpdsFeedScreen {
    private static final By PAGE_LOCATOR = By.id("settingsCustomOPDSURL");
    private final ITextBox txbBookName = getElementFactory().getTextBox(PAGE_LOCATOR, "Feed url");
    private final IButton btnSubmit = getElementFactory().getButton(By.id("settingsCustomOPDSCreate"), "Submit");
    private final ILabel lblStatus = getElementFactory().getLabel(By.id("settingsCustomOPDSProgressText"), "Status");

    public AndroidAddCustomOpdsFeedScreen() {
        super(PAGE_LOCATOR);
    }

    @Override
    public void enterOpds(String opds) {
        txbBookName.clearAndType(opds);
        btnSubmit.click();
        AqualityServices.getConditionalWait().waitFor(this::isFeedAdded, Duration.ofMillis(AuthorizationTimeouts.DEBUG_MENU_IS_OPENED.getTimeoutMillis()));
    }

    @Override
    public boolean isFeedAdded() {
        return lblStatus.getText().contains("Creating the account succeeded.");
    }
}
