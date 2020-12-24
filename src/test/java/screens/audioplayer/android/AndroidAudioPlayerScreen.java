package screens.audioplayer.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.attributes.AndroidAttributes;
import constants.application.timeouts.AudioBookTimeouts;
import org.openqa.selenium.By;
import org.testng.Assert;
import screens.audioplayer.AudioPlayerScreen;

import java.time.Duration;
import java.util.List;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidAudioPlayerScreen extends AudioPlayerScreen {
    private static final String MAIN_ELEMENT = "player_cover";
    private static final String CHAPTERS_LOC = "//android.widget.RelativeLayout[.//*[contains(@resource-id, \"player_toc_item_view_title\")]]";
    public static final String LOADING_SCREEN_XPATH = "//*[contains(@resource-id,\"player_toc_item_downloading_progress\")]";

    private final IButton btnMenu = getElementFactory().getButton(By.id("player_menu_toc"), "Menu");
    private final ILabel lblCurrentChapter = getElementFactory().getLabel(By.id("player_spine_element"), "Current chapter");
    private final ILabel lblCurrentTiming = getElementFactory().getLabel(By.id("player_time"), "Current time");
    private final ILabel lblLoadingStatus = getElementFactory().getLabel(By.id("player_waiting_buffering"), "Loading status");
    private final IButton btnPlay =
            getElementFactory().getButton(By.xpath("//android.widget.ImageView[@content-desc=\"Play\"]"), "Play");
    private final IButton btnPause =
            getElementFactory().getButton(By.xpath("//android.widget.ImageView[@content-desc=\"Pause\"]"), "Pause");

    public AndroidAudioPlayerScreen() {
        super(By.id(MAIN_ELEMENT));
    }

    public List<ILabel> getChapters() {
        return getElementFactory().findElements(By.xpath(CHAPTERS_LOC), ElementType.LABEL);
    }

    @Override
    public void checkThatChaptersVisible() {
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> getChapters().size() > 0),
                "Checking that count of chapters greater than zero");
    }

    @Override
    public void waitAndCheckAllLoadersDisappeared() {
        checkThatChaptersVisible();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> getElementFactory().findElements(By.xpath(LOADING_SCREEN_XPATH), ElementType.LABEL).size() == 0, Duration.ofMillis(AudioBookTimeouts.TIMEOUT_AUDIO_BOOK_LOADER_DISAPPEAR.getTimeoutMillis())),
                "Book loading wasn't finished");
    }

    @Override
    public void openMenu() {
        btnMenu.click();
    }

    @Override
    public void selectChapterNumber(int chapterNumber) {
        ILabel chapter = getChapters().get(chapterNumber);
        chapter.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        chapter.click();
    }

    @Override
    public String getCurrentChapterInfo() {
        return lblCurrentChapter.getText();
    }

    @Override
    public int getCountOfChapters() {
        return getChapters().size();
    }

    @Override
    public void playBook() {
        btnPlay.click();
    }

    @Override
    public void pauseBook() {
        btnPause.click();
    }

    @Override
    public boolean isPauseButtonPresent() {
        AqualityServices.getApplication().getDriver().getPageSource();
        return btnPause.state().waitForExist();
    }

    @Override
    public boolean isPlayButtonPresent() {
        return btnPlay.state().waitForDisplayed();
    }

    @Override
    public String getCurrentPlayTime() {
        AqualityServices.getLogger().info(lblCurrentTiming.getText());
        AqualityServices.getLogger().info(lblCurrentTiming.getAttribute(AndroidAttributes.CONTENT_DESC));
        return lblCurrentTiming.getText();
    }

    @Override
    public String getLoadingStatus() {
        if (lblLoadingStatus.state().isDisplayed()) {
            return lblLoadingStatus.getText();
        } else {
            return "";
        }
    }
}
