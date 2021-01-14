package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
import constants.application.timeouts.BooksTimeouts;
import framework.utilities.RegExUtil;
import framework.utilities.ScenarioContext;
import framework.utilities.SmartRandomUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.audioplayer.AudioPlayerScreen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class AudioPlayerSteps {
    private final AudioPlayerScreen audioPlayerScreen;
    private final ScenarioContext context;

    @Inject
    public AudioPlayerSteps(ScenarioContext context) {
        audioPlayerScreen = AqualityServices.getScreenFactory().getScreen(AudioPlayerScreen.class);
        this.context = context;
    }

    @And("Remember current book chapter in {string}")
    public void saveCurrentBookChapterInContextByKey(String key) {
        audioPlayerScreen.waitForLoadingDisappearing();
        context.add(key, getChapterNumber());
    }

    @And("Open the menu-based position in the audiobook")
    @When("I open the menu-based position in the audiobook")
    public void openTheMenuBasedPositionInTheAudiobook() {
        audioPlayerScreen.openMenu();
    }

    @Then("I check that chapters are visible")
    public void checkThatChaptersAreVisible() {
        audioPlayerScreen.checkThatChaptersVisible();
    }

    @And("Wait and check that all loaders are disappeared")
    public void waitAndCheckAllLoadersDisappeared() {
        audioPlayerScreen.waitAndCheckAllLoadersDisappeared();
    }

    @When("I select the chapter not equal to remembered {string} and remember selected chapter as {string}")
    public void selectChapterIsNotEqualToSavedInTheContextByKeyAndSaveSelectedChapter(String keySavedChapter, String keySelectedChapter) {
        int savedChapterNumber = context.get(keySavedChapter);
        int totalChapterCount = audioPlayerScreen.getCountOfChapters();
        int chapterToSelect =
                SmartRandomUtils.getRandomWithExclusion(0, totalChapterCount, savedChapterNumber - 1, totalChapterCount);

        audioPlayerScreen.selectChapterNumber(chapterToSelect);
        context.add(keySelectedChapter, chapterToSelect + 1);
    }

    @When("I select {int} chapter and remember selected chapter as {string}")
    public void selectSecondChapterAndSaveSelectedChapter(int chapterToSelect, String keySelectedChapter) {
        audioPlayerScreen.selectChapterNumber(chapterToSelect);
        audioPlayerScreen.waitForLoadingDisappearing();
        context.add(keySelectedChapter, chapterToSelect);
    }

    @Then("I check that current chapter equal to remembered {string}")
    public void selectChapterIsNotEqualToSavedInTheContextByKeyAndSaveSelectedChapter(String keyCurrentChapter) {
        int expectedChapterName = context.get(keyCurrentChapter);
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> getChapterNumber() == expectedChapterName),
                String.format("Current chapter number is not correct. Expected - %d; actual - %d", expectedChapterName, getChapterNumber()));
        AqualityServices.getConditionalWait().waitFor(() -> false, Duration.ofMillis(BooksTimeouts.SYSTEM_CHANGES_STATUS.getTimeoutMillis()));
    }

    @And("I click play button on player screen")
    public void clickPlayButtonOnPlayerScreen() {
        audioPlayerScreen.playBook();
    }

    @When("I click pause button on player screen")
    public void clickPauseButtonOnPlayerScreen() {
        audioPlayerScreen.pauseBook();
    }

    @Then("Pause button is present")
    public void checkPauseButtonIsPresent() {
        Assert.assertTrue(audioPlayerScreen.isPauseButtonPresent(), "Pause button is not present");
    }

    @Then("Play button is present")
    public void checkPlayButtonIsPresent() {
        Assert.assertTrue(audioPlayerScreen.isPlayButtonPresent(), "Play button is not present");
    }

    @And("Book is playing")
    public void checkBookIsPlaying() throws ParseException {
        audioPlayerScreen.waitForBookLoading();
        Date firstTiming = audioPlayerScreen.getCurrentPlayTime();
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> {
                    try {
                        return !firstTiming.equals(audioPlayerScreen.getCurrentPlayTime());
                    } catch (ParseException e) {
                        AqualityServices.getLogger().info("Date parsing error." + e.getMessage());
                        return false;
                    }
                }),
                "Book is not playing. Error (if present) - " + audioPlayerScreen.getLoadingStatus());
    }

    @And("Book is not playing")
    public void checkBookIsNotPlaying() throws ParseException {
        Date firstTiming = audioPlayerScreen.getCurrentPlayTime();
        Assert.assertEquals(audioPlayerScreen.getCurrentPlayTime(), firstTiming, "Book is still playing");
    }

    @When("I save book play time as {string}")
    public void iSaveBookPlayTimeAsTimeAhead(String dateKey) throws ParseException {
        context.add(dateKey, audioPlayerScreen.getCurrentPlayTime());
    }

    private int getChapterNumber() {
        return Integer.parseInt(RegExUtil.getStringFromFirstGroup(audioPlayerScreen.getCurrentChapterInfo(), RegEx.AUDIO_BOOK_CURRENT_CHAPTER_REGEX));
    }

    @And("I skip ahead 15 seconds")
    public void skipAhead() {
        audioPlayerScreen.skipAhead();
    }

    @And("I skip behind 15 seconds")
    public void skipBehind() {
        audioPlayerScreen.skipBehind();
    }

    @Then("Playback {string} moves forward by {int} seconds increment")
    public void checkPlaybackTimeAheadMovesForwardBySecondsIncrement(String timeKey, int secondsDiff) throws ParseException {
        Date savedDate = context.get(timeKey);
        int diffInSec = (int) (audioPlayerScreen.getCurrentPlayTime().getTime() - savedDate.getTime()) / 1000;
        AqualityServices.getLogger().info("diff between  times -" + diffInSec);
        Assert.assertTrue(diffInSec >= secondsDiff && diffInSec < secondsDiff + 5,
                "Date is not moved forward by " + secondsDiff + " seconds");
    }

    @Then("Playback {string} moves behind by {int} seconds increment")
    public void checkPlaybackTimeAheadMovesBehindBySecondsIncrement(String timeKey, int secondsDiff) throws ParseException {
        Date savedDate = context.get(timeKey);
        int diffInSec = (int) (savedDate.getTime() - audioPlayerScreen.getCurrentPlayTime().getTime()) / 1000;
        AqualityServices.getLogger().info("diff between  times -" + diffInSec);
        Assert.assertTrue(diffInSec > secondsDiff - 5 && diffInSec <= secondsDiff,
                "Date is not moved behind by " + secondsDiff + " seconds");
    }

    @And("I move to middle part of chapter")
    public void moveToMiddlePartOfChapter() {
        audioPlayerScreen.moveChapterToMiddle();
    }

    @Then("Play time is close to middle part of chapter")
    public void checkPlayTimeIsCloseToMiddlePartOfChapter() throws ParseException {
        Date zeroDate = new SimpleDateFormat("HH:mm:ss").parse("00:00:00");
        Date fullChapterLength = audioPlayerScreen.getChapterLength();
        int middleOfChapterImMillis = (int) ((fullChapterLength.getTime() - zeroDate.getTime()) / 1000 / 2);
        AqualityServices.getLogger().info("middle im mills " + middleOfChapterImMillis);
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> {
            int currentTimeDifference = 0;
            try {
                currentTimeDifference = (int) (audioPlayerScreen.getCurrentPlayTime().getTime() - zeroDate.getTime()) / 1000;
                AqualityServices.getLogger().info("current time in mills " + currentTimeDifference);
            } catch (ParseException e) {
            }
            return middleOfChapterImMillis - 10 < currentTimeDifference && currentTimeDifference < middleOfChapterImMillis + 10;
        }), "Middle of chapter wasn't opened");
    }
}
