package screens.bookDetails;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.android.AndroidBookDetailsScreenInformationBlockKeys;
import org.openqa.selenium.By;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidBookDetailsScreen extends BookDetailsScreen {
    private static final String INFORMATION_TAB_LABELS_NAME_PART = "Information tab %1$s value";

    private static final String CONTENT_ATTRIBUTE_NAME = "content-desc";
    private static final String INFORMATION_TAB_VALUE_LOC_PART = "//*[@resource-id=\"org.nypl.simplified.simplye:id/key\" "
            + "and @text=\"%1$s\"]/following-sibling::*[@resource-id=\"org.nypl.simplified.simplye:id/value\"]";

    private final IButton btnDownload =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Download Button\"]"), "Download");
    private final IButton btnRead =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Read Button\"]"), "Read");
    private final IButton btnReserve =
            getElementFactory().getButton(By.xpath("//android.widget.Button[@content-desc=\"Reserve Button\"]"), "Read");
    private final ILabel lblBookInfo = getElementFactory().getLabel(By.id("bookDetailCoverImage"), "Cover Image");
    private final ILabel lblBookDescription = getElementFactory().getLabel(
            By.xpath("//*[@resource-id=\"org.nypl.simplified.simplye:id/bookDetailDescriptionText\"]"),
            "Description");

    public AndroidBookDetailsScreen() {
        super(By.id("bookDetailCover"));
    }

    @Override
    public void downloadBook() {
        btnDownload.click();
        btnRead.state().waitForDisplayed();
    }

    @Override
    public void reserveBook() {
        btnReserve.click();
    }

    @Override
    public String getBookInfo() {
        return lblBookInfo.getAttribute(CONTENT_ATTRIBUTE_NAME);
    }

    @Override
    public boolean isValueInTheInformationBlockPresent(AndroidBookDetailsScreenInformationBlockKeys key, String value) {
        ILabel lblInformationBlockValue = getElementFactory()
                .getLabel(By.xpath(String.format(INFORMATION_TAB_VALUE_LOC_PART, key.getKey())),
                        String.format(INFORMATION_TAB_LABELS_NAME_PART, key.getKey()));
        lblInformationBlockValue.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return lblInformationBlockValue.state()
                .waitForDisplayed();
    }

    @Override
    public boolean isDescriptionPresent() {
        return lblBookDescription.state().waitForDisplayed();
    }

    @Override
    public String getDescriptionText() {
        return lblBookDescription.getText();
    }
}
