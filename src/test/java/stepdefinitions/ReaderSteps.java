package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
import constants.localization.application.reader.ColorKeys;
import constants.localization.application.reader.ReaderSettingKeys;
import framework.utilities.RegExUtil;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.CatalogBookModel;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.epubreader.EpubReaderScreen;
import screens.fontchoicesscreen.FontChoicesScreen;
import screens.pdfreader.PdfReaderScreen;
import screens.tableofcontents.TableOfContentsScreen;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.IntStream;

public class ReaderSteps {
    private final EpubReaderScreen epubReaderScreen;
    private final PdfReaderScreen pdfReaderScreen;
    private final ScenarioContext context;
    private final TableOfContentsScreen tableOfContentsScreen;
    private final FontChoicesScreen fontChoicesScreen;
    private static final String SERIF_FONT_NAME = "serif !important";
    private static final String SANS_SERIF_FONT_NAME = "sans-serif !important";
    private static final String ALTERNATIVE_SANS_FONT_NAME = "OpenDyslexic3 !important";

    @Inject
    public ReaderSteps(ScenarioContext context) {
        epubReaderScreen = AqualityServices.getScreenFactory().getScreen(EpubReaderScreen.class);
        pdfReaderScreen = AqualityServices.getScreenFactory().getScreen(PdfReaderScreen.class);
        tableOfContentsScreen = AqualityServices.getScreenFactory().getScreen(TableOfContentsScreen.class);
        fontChoicesScreen = AqualityServices.getScreenFactory().getScreen(FontChoicesScreen.class);
        this.context = context;
    }

    @Then("Book {string} is present on screen")
    public void checkBookInfoIsPresentOnScreen(String bookInfoKey) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        Assert.assertEquals(catalogBookModel.getTitle(), epubReaderScreen.getBookName(), "Book name is not correct");
    }

    @Then("Book page number is {int}")
    public void checkBookPageNumberIs(int pageNumber) {
        Assert.assertEquals(pageNumber, getPageNumber(epubReaderScreen.getPageNumberInfo()), "Book page number is not correct");
    }

    @When("I swipe from left to right book corner")
    public void swipeFromLeftToRightBookCorner() {
        epubReaderScreen.swipeFromLeftToRight();
    }

    @When("I swipe from right to left book corner")
    public void swipeFromRightToLeftBookCorner() {
        epubReaderScreen.swipeFromRightToLeft();
    }

    @When("I click on left book corner")
    public void clickOnLeftBookCorner() {
        epubReaderScreen.clickLeftCorner();
    }

    @When("I click on right book corner")
    public void clickOnRightBookCorner() {
        epubReaderScreen.clickRightCorner();
    }

    @When("I save page info as {string}")
    public void savePageInfoAsPageInfo(String pageNumberInfo) {
        context.add(pageNumberInfo, epubReaderScreen.getPageNumberInfo());
    }

    @Then("Book page number is bigger then previous {string}")
    public void checkBookPageNumberIsBiggerThenPreviousPageInfo(String pageNumberInfo) {
        String actualBookInfo = epubReaderScreen.getPageNumberInfo();
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
        String actualBookInfo = epubReaderScreen.getPageNumberInfo();
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
        Set<String> chapters = epubReaderScreen.getListOfChapters();
        for (String chapter :
                chapters) {
            epubReaderScreen.openChapter(chapter);
            softAssert.assertEquals(getChapterName(epubReaderScreen.getPageNumberInfo()), chapter, "Chapter name is not correct");
        }
        softAssert.assertAll();
    }

    @When("I open font choices for book")
    public void openFontChoicesForBook() {
        epubReaderScreen.openFontSettings();
    }

    @And("I open Table of Contents")
    public void openTableOfContents() {
        epubReaderScreen.openTableOfContents();
    }

    @Then("Table of Contents is opened")
    public void checkTableOfContentsIsOpened() {
        Assert.assertTrue(tableOfContentsScreen.state().waitForDisplayed(), "Table of Contents is not opened");
    }

    @Then("Font choices screen is present")
    public void checkFontChoicesScreenIsPresent() {
        Assert.assertTrue(fontChoicesScreen.state().waitForDisplayed(), "Font choices screen is not opened");
    }

    @When("I save font size as {string}")
    public void saveFontSize(String fontSizeKey) {
        context.add(fontSizeKey, epubReaderScreen.getFontSize());
    }

    @Then("Font size {string} is increased")
    public void checkFontSizeIsIncreased(String fontSizeKey) {
        double actualFontSize = epubReaderScreen.getFontSize();
        double expectedFontSize = context.get(fontSizeKey);
        Assert.assertTrue(actualFontSize > expectedFontSize,
                "Font size is not increased actual - " + actualFontSize + ", expected - " + expectedFontSize);
    }

    @Then("Font size {string} is decreased")
    public void checkFontSizeIsDecreased(String fontSizeKey) {
        double actualFontSize = epubReaderScreen.getFontSize();
        double expectedFontSize = context.get(fontSizeKey);
        Assert.assertTrue(actualFontSize < expectedFontSize,
                "Font size is not decreased actual - " + actualFontSize + ", expected - " + expectedFontSize);
    }

    @When("I {} of text")
    @When("I change font style to {}")
    @When("I change contrast to {}")
    public void changeSettingsForFont(ReaderSettingKeys readerSettingKey) {
        changeSetting(readerSettingKey);
    }

    @Then("Book text displays {} on {}")
    public void checkBookTextDisplaysWhiteTextOnBlack(ColorKeys text, ColorKeys background) {
        assertFontAndBackground(text, background);
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
        epubReaderScreen.openFontSettings();
        fontChoicesScreen.setSetting(settingName);
        AqualityServices.getApplication().getDriver().navigate().back();
    }

    private void assertFontAndBackground(ColorKeys fontColor, ColorKeys backgroundColor) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(epubReaderScreen.getFontColor(), fontColor.i18n(), "Font color is not correct");
        softAssert.assertEquals(epubReaderScreen.getBackgroundColor(), backgroundColor.i18n(), "Background color is not correct");
        softAssert.assertAll();
    }

    private void assertFontName(String fontName) {
        Assert.assertEquals(epubReaderScreen.getFontName(), fontName, "Book font is not correct");
    }

    @And("Page info {string} is correct")
    public void checkPageInfoPageInfoIsCorrect(String pageNumberInfo) {
        String pageInfo = context.get(pageNumberInfo);
        Assert.assertEquals(epubReaderScreen.getPageNumberInfo(), pageInfo, "Page info is not correct");
    }

    @When("I scroll page forward from {int} to {int} times")
    public void scrollPageForward(int minValue, int maxValue) {
        int randomScrollsCount = RandomUtils.nextInt(minValue, maxValue);
        IntStream.range(0, randomScrollsCount).forEachOrdered(i -> epubReaderScreen.clickRightCorner());
    }

    @Then("Pdf book {string} is present on screen")
    public void checkPdfBookBookInfoIsPresentOnScreen(String bookInfoKey) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        Assert.assertEquals(pdfReaderScreen.getBookName(), catalogBookModel.getTitle(), "Book name is not correct");
    }

    @Then("Pdf book page number is {int}")
    public void checkPdfBookPageNumberIs(int pageNumber) {
        Assert.assertEquals(pageNumber, pdfReaderScreen.getPageNumber(), "Book page number is not correct");
    }
}
