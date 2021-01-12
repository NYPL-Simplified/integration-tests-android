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
import framework.utilities.DateUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.audioplayer.AudioPlayerScreen;

import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@ScreenType(platform = PlatformName.IOS)
public class IosAudioPlayerScreen extends AudioPlayerScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeImage[@name=\"cover_art\"]";
    private static final String CHAPTERS_TIMERS = ".//XCUIElementTypeStaticText[@name]";
    private static final String CHAPTERS_LOCATOR = "//XCUIElementTypeTable//XCUIElementTypeCell";
    private static final String LOADED_CHAPTERS_LOCATOR = "//XCUIElementTypeTable//XCUIElementTypeCell//XCUIElementTypeOther[@visible=\"false\"]";
    public static final int COUNT_OF_CHAPTERS_TO_WAIT_FOR = 3;

    private final IButton btnMenu =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@name=\"Table of Contents\"]"), "Menu");
    private final IButton btnPlay =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@label=\"Play\"]"), "Play");
    private final IButton btnPause =
            getElementFactory().getButton(By.xpath("//XCUIElementTypeButton[@label=\"Pause\"]"), "Pause");
    private final ILabel lblCurrentChapter =
            getElementFactory().getLabel(By.xpath("(//XCUIElementTypeStaticText[@name=\"progress_rightLabel\"])[1]"), "Current chapter");
    private final ILabel lblCurrentTime =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[@name=\"progress_leftLabel\"]"), "Current time", ElementState.EXISTS_IN_ANY_STATE);
    private final ILabel lblDownloadingStatus =
            getElementFactory().getLabel(By.xpath("//XCUIElementTypeStaticText[@value=\"Downloading\"]"), "Downloading");


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
        lblDownloadingStatus.state().waitForNotExist();
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
    public Date getCurrentPlayTime() throws ParseException {
        return DateUtils.parseTime(lblCurrentTime.getAttribute(IosAttributes.VALUE));
    }

    @Override
    public String getLoadingStatus() {
        return "";
    }

    @Override
    public void skipAhead() {

    }

    @Override
    public void skipBehind() {

    }

    @Override
    public void moveChapterToMiddle() {

    }

    @Override
    public Date getChapterLength() throws ParseException {
        return null;
    }

    @Override
    public void waitForBookLoading() {

    }
}
