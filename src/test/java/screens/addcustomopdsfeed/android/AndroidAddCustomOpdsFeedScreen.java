package screens.addcustomopdsfeed.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.addcustomopdsfeed.AddCustomOpdsFeedScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAddCustomOpdsFeedScreen extends AddCustomOpdsFeedScreen {
    private final ITextBox txbBookName = getElementFactory().getTextBox(By.id("settingsCustomOPDSURL"), "Feed url");
    private final IButton btnSubmit = getElementFactory().getButton(By.id("settingsCustomOPDSCreate"), "Submit");
    private final ILabel lblStatus = getElementFactory().getLabel(By.id("settingsCustomOPDSProgressText"), "Status");

    public AndroidAddCustomOpdsFeedScreen() {
        super(By.id("settingsCustomOPDSURL"));
    }

    @Override
    public void enterOpds(String opds) {
        txbBookName.clearAndType(opds);
        btnSubmit.click();
        AqualityServices.getConditionalWait().waitFor(() -> lblStatus.getText().contains("Creating the account succeeded."));
    }
}
