package screens.audioplayer.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.application.timeouts.AudioBookTimeouts;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.audioplayer.AudioPlayerScreen;

import java.time.Duration;
import java.util.List;

@ScreenType(platform = PlatformName.IOS)
public class IosAudioPlayerScreen extends AudioPlayerScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeImage[@name=\"cover_art\"]";
    private static final String CHAPTERS_TIMERS = ".//XCUIElementTypeStaticText[@name]";

    private final IButton menuBtn = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeButton[@name=\"Table of Contents\"]"), "Menu");

    private final ILabel currentChapter = getElementFactory().getLabel(
            By.xpath("(//XCUIElementTypeStaticText[@name=\"progress_rightLabel\"])[1]"), "Current chapter");

    private final List<ILabel> chapters = getElementFactory().findElements(
            By.xpath("//XCUIElementTypeTable//XCUIElementTypeCell"), ElementType.LABEL);


    public IosAudioPlayerScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void checkThatChaptersVisible() {
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> chapters.size() > 0),
                "Checking that count of chapters greater than zero");
    }

    @Override
    public void waitAndCheckAllLoadersDisappeared() {
        checkThatChaptersVisible();
        SoftAssert softAssert = new SoftAssert();
        chapters.forEach(chapter ->
                softAssert.assertTrue(
                        AqualityServices.getConditionalWait().waitFor(() ->
                                        chapter.findChildElement(By.xpath(CHAPTERS_TIMERS), ElementType.LABEL).state().isDisplayed(),
                                Duration.ofMillis(AudioBookTimeouts.TIMEOUT_AUDIO_BOOK_LOADER_DISAPPEAR.getTimeoutMillis())),
                        "Loader did not disappear from the chapter block")
        );
        softAssert.assertAll("Checking that all loaders are disappeared");
    }

    @Override
    public void openMenu() {
        menuBtn.click();
    }

    @Override
    public void selectChapterNumber(int chapterNumber) {
        ILabel chapter = chapters.get(chapterNumber);
        chapter.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        chapter.click();
    }

    @Override
    public String getCurrentChapterInfo() {
        return currentChapter.getText();
    }

    @Override
    public int getCountOfChapters() {
        return chapters.size();
    }

}
