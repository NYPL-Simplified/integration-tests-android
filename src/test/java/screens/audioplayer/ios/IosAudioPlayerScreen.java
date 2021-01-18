package screens.audioplayer.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.ElementState;
import constants.application.attributes.IosAttributes;
import constants.application.timeouts.AudioBookTimeouts;
import constants.application.timeouts.BooksTimeouts;
import constants.application.timeouts.CategoriesTimeouts;
import constants.localization.application.catalog.TimerKeys;
import framework.utilities.DateUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.audioplayer.AudioPlayerScreen;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ScreenType(platform = PlatformName.IOS)
public class IosAudioPlayerScreen extends AudioPlayerScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeImage[@name=\"cover_art\"]";
    private static final String CHAPTERS_TIMERS = ".//XCUIElementTypeStaticText[@name]";
    private static final String CHAPTERS_LOCATOR = "//XCUIElementTypeTable//XCUIElementTypeCell";
    private static final String LOADED_CHAPTERS_LOCATOR = "//XCUIElementTypeTable//XCUIElementTypeCell//XCUIElementTypeOther[@visible=\"false\"]";
    private static final int COUNT_OF_CHAPTERS_TO_WAIT_FOR = 3;

    private final IButton btnMenu =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Table of Contents\"]"), "Menu");
    private final IButton btnPlay =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@label=\"Play\"]"), "Play");
    private final IButton btnPause =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@label=\"Pause\"]"), "Pause");
    private final IButton btnProgress = getElementFactory().getButton(By.name("progress_background"), "Progress bar");
    private final IButton btnBehind = getElementFactory().getButton(By.name("skip_back"), "Behind");
    private final IButton btnAhead = getElementFactory().getButton(By.name("skip_forward"), "Ahead");
    private final IButton btnPlaybackSpeed =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeToolbar//XCUIElementTypeButton"), "Playback speed");
    private final IButton btnTimer =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeToolbar//XCUIElementTypeButton[3]"), "Timer");
    private final ILabel lblCurrentChapter =
            getElementFactory().getLabel(By.xpath("(//XCUIElementTypeStaticText[@name=\"progress_rightLabel\"])[1]"), "Current chapter");
    private final ILabel lblChapterTime =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[@name=\"progress_rightLabel\" and contains(@value,\":\")]"), "Chapter time", ElementState.EXISTS_IN_ANY_STATE);
    private final ILabel lblCurrentTime =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[@name=\"progress_leftLabel\"]"), "Current time", ElementState.EXISTS_IN_ANY_STATE);
    private final ILabel lblDownloadingStatus =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[@value=\"Downloading\"]"), "Downloading");

    private static Map<Double, String> speedName = new HashMap<Double, String>() {{
        put(2.0, "Two times normal speed. Fastest.");
    }};

    public IosAudioPlayerScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    public List<ILabel> getChapters() {
        return getElementFactory().findElements(By.xpath(CHAPTERS_LOCATOR), ElementType.LABEL);
    }

    @Override
    public void checkThatChaptersVisible() {
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> getChapters().size() > 0),
                "Checking that count of chapters greater than zero");
    }

    @Override
    public void waitAndCheckAllLoadersDisappeared() {
        checkThatChaptersVisible();
        SoftAssert softAssert = new SoftAssert();
        getChapters().forEach(chapter ->
                softAssert.assertTrue(chapter.findChildElement(By.xpath(CHAPTERS_TIMERS), ElementType.LABEL).state()
                                .waitForDisplayed(Duration.ofMillis(AudioBookTimeouts.TIMEOUT_AUDIO_BOOK_LOADER_DISAPPEAR.getTimeoutMillis())),
                        "Loader did not disappear from the chapter block")
        );
        softAssert.assertAll("Checking that all loaders are disappeared");
    }

    @Override
    public void openMenu() {
        btnMenu.click();
    }

    @Override
    public void selectChapterNumber(int chapterNumber) {
        AqualityServices.getConditionalWait().waitFor(() -> getElementFactory().findElements(By.xpath(LOADED_CHAPTERS_LOCATOR), ElementType.LABEL).size() >= chapterNumber, Duration.ofMillis(CategoriesTimeouts.TIMEOUT_WAIT_UNTIL_CATEGORY_PAGE_LOAD.getTimeoutMillis()));
        ILabel chapter = getChapters().get(chapterNumber - 1);
        chapter.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        chapter.click();
    }

    @Override
    public String getCurrentChapterInfo() {
        return lblCurrentChapter.getText();
    }

    @Override
    public int getCountOfChapters() {
        AqualityServices.getConditionalWait().waitFor(() -> getChapters().size() > COUNT_OF_CHAPTERS_TO_WAIT_FOR, Duration.ofMillis(BooksTimeouts.TIMEOUT_BOOK_CHANGES_STATUS.getTimeoutMillis()));
        return getChapters().size();
    }

    @Override
    public void playBook() {
        waitForLoadingDisappearing();
        btnPlay.click();
    }

    @Override
    public void pauseBook() {
        btnPause.click();
    }

    @Override
    public boolean isPauseButtonPresent() {
        return btnPause.state().isDisplayed();
    }

    @Override
    public boolean isPlayButtonPresent() {
        return btnPlay.state().isDisplayed();
    }

    @Override
    public Duration getCurrentPlayTime() {
        return DateUtils.getDuration(lblCurrentTime.getAttribute(IosAttributes.VALUE));
    }

    @Override
    public String getLoadingStatus() {
        return "";
    }

    @Override
    public void skipAhead() {
        btnAhead.click();
    }

    @Override
    public void skipBehind() {
        btnBehind.click();
    }

    @Override
    public void moveChapterToMiddle() {
        btnProgress.click();
    }

    @Override
    public Duration getChapterLength() {
        return DateUtils.getDuration(lblChapterTime.getAttribute(IosAttributes.VALUE));
    }

    @Override
    public void waitForBookLoading() {
        lblCurrentChapter.state().waitForDisplayed();
    }

    @Override
    public void waitForLoadingDisappearing() {
        lblDownloadingStatus.state().waitForDisplayed();
        lblDownloadingStatus.state().waitForNotExist();
    }

    @Override
    public void selectPlaybackSpeed(double playbackSpeed) {
        btnPlaybackSpeed.click();
        String speedOptionName = speedName.get(playbackSpeed);
        getElementFactory().getButton(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"" + speedOptionName + "\"]"), speedOptionName).click();
    }

    @Override
    public boolean isSpeedOptionSelected(double playbackSpeed) {
        String speedOptionName = speedName.get(playbackSpeed);
        return getElementFactory().getButton(By.xpath("//XCUIElementTypeToolbar//XCUIElementTypeButton[@name=" + speedOptionName + "]"), speedOptionName).state().waitForDisplayed();
    }

    @Override
    public void setTimer(TimerKeys timerSetting) {
        btnTimer.click();
        getElementFactory().getButton(By.xpath("//XCUIElementTypeScrollView//XCUIElementTypeButton[@name=\"" + timerSetting.i18n() + "\"]"), timerSetting.i18n()).click();
    }

    @Override
    public boolean isTimerEqualTo(Duration chapterLength) {
        return getElementFactory().getButton(By.xpath("//XCUIElementTypeToolbar//XCUIElementTypeButton[@name=" + (int) chapterLength.toHours() + " hour and " + (int) chapterLength.toMinutes() + " minutes until playback pauses" + "]"), "Timer").state().isDisplayed();
    }

    @Override
    public boolean isTimerSetTo(TimerKeys timerSetting) {
        return false;
    }
}
