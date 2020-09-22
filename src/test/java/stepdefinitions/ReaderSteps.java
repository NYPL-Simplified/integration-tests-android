package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
import constants.localization.application.reader.ReaderSettingKeys;
import framework.utilities.RegExUtil;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.CatalogBookModel;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.fontchoicesscreen.FontChoicesScreen;
import screens.reader.ReaderScreen;
import screens.tableofcontents.TableOfContentsScreen;

import java.util.Set;
import java.util.regex.Matcher;

public class ReaderSteps {
    private final ReaderScreen readerScreen;
    private final ScenarioContext context;
    private final TableOfContentsScreen tableOfContentsScreen;
    private final FontChoicesScreen fontChoicesScreen;
    private static final String WHITE_COLOR = "#ffffff !important";
    private static final String BLACK_COLOR = "#000000 !important";
    private static final String SEPIA_COLOR = "#f2e4cb !important";
    private static final String SERIF_FONT_NAME = "serif !important";
    private static final String SANS_SERIF_FONT_NAME = "sans-serif !important";
    private static final String ALTERNATIVE_SANS_FONT_NAME = "OpenDyslexic3 !important";

    @Inject
    public ReaderSteps(ScenarioContext context) {
        readerScreen = AqualityServices.getScreenFactory().getScreen(ReaderScreen.class);
        tableOfContentsScreen = AqualityServices.getScreenFactory().getScreen(TableOfContentsScreen.class);
        fontChoicesScreen = AqualityServices.getScreenFactory().getScreen(FontChoicesScreen.class);
        this.context = context;
    }

    @Then("Book {string} is present on screen")
    public void checkBookInfoIsPresentOnScreen(String bookInfoKey) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        Assert.assertEquals(catalogBookModel.getTitle(), readerScreen.getBookName(), "Book name is not correct");
    }

    @Then("Book page number is {int}")
    public void checkBookPageNumberIs(int pageNumber) {
        Assert.assertEquals(pageNumber, getPageNumber(readerScreen.getPageNumberInfo()), "Book page number is not correct");
    }

    @When("I swipe from left to right book corner")
    public void swipeFromLeftToRightBookCorner() {
        readerScreen.swipeFromLeftToRight();
    }

    @When("I swipe from right to left book corner")
    public void swipeFromRightToLeftBookCorner() {
        readerScreen.swipeFromRightToLeft();
    }

    @When("I click on left book corner")
    public void clickOnLeftBookCorner() {
        readerScreen.clickLeftCorner();
    }

    @When("I click on right book corner")
    public void clickOnRightBookCorner() {
        readerScreen.clickRightCorner();
    }

    @When("I save page info as {string}")
    public void savePageInfoAsPageInfo(String pageNumberInfo) {
        context.add(pageNumberInfo, readerScreen.getPageNumberInfo());
    }

    @Then("Book page number is bigger then previous {string}")
    public void checkBookPageNumberIsBiggerThenPreviousPageInfo(String pageNumberInfo) {
        String actualBookInfo = readerScreen.getPageNumberInfo();
        String expectedBookInfo = context.get(pageNumberInfo);
        int expectedPageNumber = getPageNumber(expectedBookInfo) + 1;
        int actualPageNumber = getPageNumber(actualBookInfo);
        Assert.assertTrue(expectedPageNumber == actualPageNumber ||
                (actualPageNumber == 1 && !getChapterName(expectedBookInfo).equals(getChapterName(actualBookInfo))), String.format("Page number is not correct (actual - %d, expected - %d)", actualPageNumber, expectedPageNumber));
    }

    public int getPageNumber(String text) {
        return RegExUtil.getIntFromFirstGroup(text, RegEx.PAGE_NUMBER_REGEX);
    }

    public String getChapterName(String text) {
        Matcher matcher = getMatcher(text);
        return matcher.find() ? matcher.group(3) : "";
    }

    @Then("Book page number is smaller then previous {string}")
    public void bookPageNumberIsSmallerThenPreviousPageInfo(String pageNumberInfo) {
        String actualBookInfo = readerScreen.getPageNumberInfo();
        String expectedBookInfo = context.get(pageNumberInfo);
        int expectedPageNumber = getPageNumber(expectedBookInfo) - 1;
        int actualPageNumber = getPageNumber(actualBookInfo);
        Assert.assertTrue(expectedPageNumber == actualPageNumber ||
                (!getChapterName(expectedBookInfo).equals(getChapterName(actualBookInfo))), String.format("Page number is not correct (actual - %d, expected - %d)", actualPageNumber, expectedPageNumber));
    }

    private Matcher getMatcher(String text) {
        return RegExUtil.getMatcher(text, RegEx.PAGE_NUMBER_REGEX);
    }

    @And("Each chapter can be opened from Table of Contents")
    public void checkEachChapterCanBeOpenedFromTableOfContents() {
        SoftAssert softAssert = new SoftAssert();
        Set<String> chapters = readerScreen.getListOfChapters();
        for (String chapter :
                chapters) {
            readerScreen.openChapter(chapter);
            softAssert.assertEquals(getChapterName(readerScreen.getPageNumberInfo()), chapter, "Chapter name is not correct");
        }
        softAssert.assertAll();
    }

    @When("I open font choices for book")
    public void openFontChoicesForBook() {
        readerScreen.openFontSettings();
    }

    @And("I open Table of Contents")
    public void openTableOfContents() {
        readerScreen.openTableOfContents();
    }

    @Then("Table of Contents is opened")
    public void checkTableOfContentsIsOpened() {
        Assert.assertTrue(tableOfContentsScreen.state().waitForDisplayed(), "Table of Contents is not opened");
    }

    @Then("Font choices screen is present")
    public void checkFontChoicesScreenIsPresent() {
        Assert.assertTrue(fontChoicesScreen.state().waitForDisplayed(), "Font choices screen is not opened");
    }

    @And("I increase text font size")
    public void increaseTextFontSize() {
        changeSetting(ReaderSettingKeys.INCREASE_FONT_SETTINGS);
    }

    @When("I decrease text font size")
    public void decreaseTextFontSize() {
        changeSetting(ReaderSettingKeys.DECREASE_FONT_SETTINGS);
    }

    @When("I save font size as {string}")
    public void saveFontSize(String fontSizeKey) {
        context.add(fontSizeKey, readerScreen.getFontSize());
    }

    @Then("Font size {string} is increased")
    public void checkFontSizeIsIncreased(String fontSizeKey) {
        double actualFontSize = readerScreen.getFontSize();
        double expectedFontSize = context.get(fontSizeKey);
        Assert.assertTrue(actualFontSize > expectedFontSize,
                "Font size is not increased actual - " + actualFontSize + ", expected - " + expectedFontSize);
    }

    @Then("Font size {string} is decreased")
    public void checkFontSizeIsDecreased(String fontSizeKey) {
        double actualFontSize = readerScreen.getFontSize();
        double expectedFontSize = context.get(fontSizeKey);
        Assert.assertTrue(actualFontSize < expectedFontSize,
                "Font size is not decreased actual - " + actualFontSize + ", expected - " + expectedFontSize);
    }

    @When("I change font style to serif")
    public void changeFontStyleToSerif() {
        changeSetting(ReaderSettingKeys.SERIF);
    }

    @When("I change font style to sans-serif arial")
    public void changeFontStyleToSansSerifArial() {
        changeSetting(ReaderSettingKeys.SANS_SERIF_ARIAL);
    }

    @When("I change font style to alternative sans")
    public void changeFontStyleToAlternativeSans() {
        changeSetting(ReaderSettingKeys.ALTERNATIVE_SANS);
    }

    @When("I change contrast to white text on black")
    public void changeContrastToWhiteTextOnBlack() {
        changeSetting(ReaderSettingKeys.WHITE_TEXT_ON_BLACK);
    }

    @When("I change contrast to black text on white")
    public void changeContrastToBlackTextOnWhite() {
        changeSetting(ReaderSettingKeys.BLACK_TEXT_ON_WHITE);
    }

    @When("I change contrast to black text on sepia")
    public void changeContrastToBlackTextOnSepia() {
        changeSetting(ReaderSettingKeys.BLACK_TEXT_ON_SEPIA);
    }

    @Then("Book text displays white-text on black")
    public void checkBookTextDisplaysWhiteTextOnBlack() {
        assertFontAndBackground(WHITE_COLOR, BLACK_COLOR);
    }

    @Then("Book text displays black-text on white")
    public void checkBookTextDisplaysBlackTextOnWhite() {
        assertFontAndBackground(BLACK_COLOR, WHITE_COLOR);
    }

    @Then("Book text displays black-text on sepia")
    public void checkBookTextDisplaysBlackTextOnSepia() {
        assertFontAndBackground(BLACK_COLOR, SEPIA_COLOR);
    }

    @Then("Book text displays in serif font")
    public void bookTextDisplaysInSerifFont() {
        assertFontName(SERIF_FONT_NAME);
    }

    @Then("Book text displays in sans-serif arial font")
    public void bookTextDisplaysInSansSerifArialFont() {
        assertFontName(SANS_SERIF_FONT_NAME);
    }

    @Then("Book text displays in alternative sans font")
    public void bookTextDisplaysInAlternativeSansFont() {
        assertFontName(ALTERNATIVE_SANS_FONT_NAME);
    }

    private void changeSetting(ReaderSettingKeys settingName) {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(settingName);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    private void assertFontAndBackground(String fontColor, String backgroundColor) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(readerScreen.getFontColor(), fontColor, "Font color is not correct");
        softAssert.assertEquals(readerScreen.getBackgroundColor(), backgroundColor, "Background color is not correct");
        softAssert.assertAll();
    }

    private void assertFontName(String fontName) {
        Assert.assertEquals(readerScreen.getFontName(), fontName, "Book font is not correct");
    }
}
