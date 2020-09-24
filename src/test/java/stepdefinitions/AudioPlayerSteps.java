package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
import framework.utilities.RegExUtil;
import framework.utilities.ScenarioContext;
import framework.utilities.SmartRandomUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.audioplayer.AudioPlayerScreen;

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
        Integer chapterNumber = Integer.parseInt(RegExUtil.getStringFromFirstGroup(audioPlayerScreen.getCurrentChapterInfo(),
                RegEx.AUDIO_BOOK_CURRENT_CHAPTER_REGEX));
        context.add(key, chapterNumber);
    }

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
        int chapterToSelect = SmartRandomUtils.getRandomWithExclusion(0, totalChapterCount, savedChapterNumber - 1);

        audioPlayerScreen.selectChapterNumber(chapterToSelect);
        context.add(keySelectedChapter, chapterToSelect + 1);
    }

    @Then("I check that current chapter equal to remembered {string}")
    public void selectChapterIsNotEqualToSavedInTheContextByKeyAndSaveSelectedChapter(String keyCurrentChapter) {
        Integer currentChapter = Integer.parseInt(RegExUtil.getStringFromFirstGroup(audioPlayerScreen.getCurrentChapterInfo(),
                RegEx.AUDIO_BOOK_CURRENT_CHAPTER_REGEX));

        Assert.assertEquals(currentChapter, context.get(keyCurrentChapter),
                "Current chapter does not equal to expected");
    }





}
