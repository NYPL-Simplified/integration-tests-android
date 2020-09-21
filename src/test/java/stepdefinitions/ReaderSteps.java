package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
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
        Matcher matcher = getMatcher(text);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
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
    public void iIncreaseTextFontSize() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.INCREASE_FONT_SETTINGS);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @When("I decrease text font size")
    public void iDecreaseTextFontSize() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.DECREASE_FONT_SETTINGS);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @When("I save font size as {string}")
    public void iSaveFontSizeAsFontSize() {
        context.add(readerScreen.getFontSize());
    }

    @Then("Font size {string} is increased")
    public void fontSizeFontSizeIsIncreased(String fontSizeKey) {
        double previousFontSize = context.get(fontSizeKey);
        Assert.assertTrue(readerScreen.getFontSize() > previousFontSize, "Font size is not increased");
    }

    @Then("Font size {string} is decreased")
    public void fontSizeFontSizeIsDecreased(String fontSizeKey) {
        double previousFontSize = context.get(fontSizeKey);
        Assert.assertTrue(readerScreen.getFontSize() < previousFontSize, "Font size is not decreased");
    }

    @When("I change font style to serif")
    public void iChangeFontStyleToSerif() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.SERFIF);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @When("I change font style to sans-serif arial")
    public void iChangeFontStyleToSansSerifArial() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.SANS_SERIF_ARIAL);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @When("I change font style to alternative sans")
    public void iChangeFontStyleToAlternativeSans() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.ALTERNATIVE_SANS);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @When("I change contrast to white text on black")
    public void iChangeContrastToWhiteTextOnBlack() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.WHITE_TEXT_ON_BLACK);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @When("I change contrast to black text on white")
    public void iChangeContrastToBlackTextOnWhite() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.BLACK_TEXT_ON_WHITE);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    @When("I change contrast to black text on sepia")
    public void iChangeContrastToBlackTextOnSepia() {
        readerScreen.openFontSettings();
        fontChoicesScreen.setSetting(SettingScrrenButton.BLACK_TEXT_ON_SEPIA);
        AqualityServices.getApplication().getDriver().navigate().back();
    }
}
