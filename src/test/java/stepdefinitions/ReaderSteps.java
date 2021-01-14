package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
import constants.application.ReaderType;
import constants.localization.application.reader.ColorKeys;
import constants.localization.application.reader.FontNameKeys;
import constants.localization.application.reader.ReaderSettingKeys;
import framework.utilities.RegExUtil;
import framework.utilities.ScenarioContext;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import framework.utilities.swipe.directions.EntireScreenDragDirection;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.CatalogBookModel;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.audioplayer.AudioPlayerScreen;
import screens.epubreader.EpubReaderScreen;
import screens.epubtableofcontents.EpubTableOfContentsScreen;
import screens.fontchoicesscreen.FontChoicesScreen;
import screens.pdfreader.PdfReaderScreen;
import screens.pdfsearch.PdfSearchScreen;
import screens.pdftableofcontents.PdfTableOfContentsScreen;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.IntStream;

public class ReaderSteps {
    private final EpubReaderScreen epubReaderScreen;
    private final PdfReaderScreen pdfReaderScreen;
    private final ScenarioContext context;
    private final EpubTableOfContentsScreen epubTableOfContentsScreen;
    private final PdfTableOfContentsScreen pdfTableOfContentsScreen;
    private final PdfSearchScreen pdfSearchScreen;
    private final FontChoicesScreen fontChoicesScreen;
    private final AudioPlayerScreen audioPlayerScreen;

    @Inject
    public ReaderSteps(ScenarioContext context) {
        epubReaderScreen = AqualityServices.getScreenFactory().getScreen(EpubReaderScreen.class);
        pdfReaderScreen = AqualityServices.getScreenFactory().getScreen(PdfReaderScreen.class);
        epubTableOfContentsScreen = AqualityServices.getScreenFactory().getScreen(EpubTableOfContentsScreen.class);
        pdfTableOfContentsScreen = AqualityServices.getScreenFactory().getScreen(PdfTableOfContentsScreen.class);
        fontChoicesScreen = AqualityServices.getScreenFactory().getScreen(FontChoicesScreen.class);
        pdfSearchScreen = AqualityServices.getScreenFactory().getScreen(PdfSearchScreen.class);
        audioPlayerScreen = AqualityServices.getScreenFactory().getScreen(AudioPlayerScreen.class);
        this.context = context;
    }

    @Then("Book {string} is present on screen")
    public void checkBookInfoIsPresentOnScreen(String bookInfoKey) {
        assertBookName(context.get(bookInfoKey));
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
        String expectedBookInfo = context.get(pageNumberInfo);
        AqualityServices.getConditionalWait().waitFor(() -> !expectedBookInfo.equals(epubReaderScreen.getPageNumberInfo()));
        String actualBookInfo = epubReaderScreen.getPageNumberInfo();
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
        Assert.assertTrue(epubTableOfContentsScreen.state().waitForDisplayed(), "Table of Contents is not opened");
    }

    @Then("Font choices screen is present")
    public void checkFontChoicesScreenIsPresent() {
        Assert.assertTrue(fontChoicesScreen.state().waitForDisplayed(), "Font choices screen is not opened");
    }

    @When("I close font choices")
    public void closeFontChoices() {
        fontChoicesScreen.closeFontChoices();
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

    @Then("Book text displays in {} font")
    public void bookTextDisplaysInSerifFont(FontNameKeys key) {
        assertFontName(key.i18n());
    }

    @And("Page info {string} is correct")
    public void checkPageInfoPageInfoIsCorrect(String pageNumberInfo) {
        String pageInfo = context.get(pageNumberInfo);

        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> isPageNumberEqual(pageInfo)),
                String.format("Page info is not correct. Expected %1$s but actual %2$s", pageInfo, epubReaderScreen.getPageNumberInfo()));
    }

    @When("I scroll page forward from {int} to {int} times")
    public void scrollPageForward(int minValue, int maxValue) {
        int randomScrollsCount = RandomUtils.nextInt(minValue, maxValue);
        AqualityServices.getLogger().info("Scrolling " + randomScrollsCount + " times");
        IntStream.range(0, randomScrollsCount).forEachOrdered(i -> {
            String pageNumber = epubReaderScreen.getPageNumberInfo();
            epubReaderScreen.clickRightCorner();
            AqualityServices.getConditionalWait().waitFor(() -> !isPageNumberEqual(pageNumber));
        });
    }

    @Then("Pdf book {string} is present on screen")
    public void checkPdfBookBookInfoIsPresentOnScreen(String bookInfoKey) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        assertPdfBookName(catalogBookModel);
    }

    private void assertPdfBookName(CatalogBookModel catalogBookModel) {
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() ->
                        pdfReaderScreen.getBookName().contains(catalogBookModel.getTitle())),
                String.format("Book name is not correct. Expected that name ['%1$s'] would contains in ['%2$s']",
                        catalogBookModel.getTitle(), pdfReaderScreen.getBookName()));
    }

    @Then("Pdf book page number is {int}")
    public void checkPdfBookPageNumberIs(int pageNumber) {
        checkPageNumberIsEqualTo(pageNumber);
    }

    @Then("Pdf book page number is not {int}")
    public void checkPdfBookPageNumberIsNot(int pageNumber) {
        checkPageNumberIsNotEqualTo(pageNumber);
    }

    @When("I go to previous page in pdf book")
    public void goToPreviousPage() {
        pdfReaderScreen.goToPreviousPage();
    }

    @When("I go to next page in pdf book")
    public void goToNextPage() {
        pdfReaderScreen.goToNextPage();
    }

    @And("Each chapter of pdf book can be opened from Table of Contents")
    public void checkEachChapterOfPdfBookCanBeOpenedFromTableOfContents() {
        SoftAssert softAssert = new SoftAssert();
        Set<String> chapters = pdfReaderScreen.getListOfChapters();
        for (String chapter : chapters) {
            int pageNumber = pdfReaderScreen.getChapterPageNumber(chapter);
            pdfReaderScreen.openChapter(chapter);
            softAssert.assertNotEquals(pdfReaderScreen.getPageNumber(), pageNumber, "Chapter name is not correct");
        }
        softAssert.assertAll();
    }

    @And("Pdf page number {string} is correct")
    public void checkPdfPageNumberIsCorrect(String pageNumberKey) {
        checkPageNumberIsEqualTo(context.get(pageNumberKey));
    }

    @And("I save pdf page number as {string}")
    public void savePdfPageNumberAs(String pageNumberKey) {
        context.add(pageNumberKey, pdfReaderScreen.getPageNumber());
    }

    @When("I scroll pdf page forward from {int} to {int} times")
    public void scrollPdfPageForward(int minValue, int maxValue) {
        IntStream.range(0, RandomUtils.nextInt(minValue, maxValue)).forEachOrdered(i -> pdfReaderScreen.goToNextPage());
    }

    @And("Slide page slider {}")
    public void slidePageSlider(EntireScreenDragDirection entireScreenDragDirection) {
        pdfReaderScreen.slidePageSlider(entireScreenDragDirection);
    }

    @Then("Pdf saved page number {string} should not be equal to current")
    public void checkThatSavedPageNumberDoesNotEqualToCurrent(String pageNumberKey) {
        checkPageNumberIsNotEqualTo(context.get(pageNumberKey));
    }

    @When("I open gallery menu")
    public void openChaptersGallery() {
        pdfReaderScreen.openChaptersGallery();
    }

    @Then("Gallery is opened")
    public void isGalleryPagesLoad() {
        pdfTableOfContentsScreen.isGalleryPagesLoaded();
    }

    @And("I save count of books on gallery to {string}")
    public void saveTheBookPagesListToTheBookPagesList(String countOfTheBookPagesKey) {
        context.add(countOfTheBookPagesKey, pdfTableOfContentsScreen.getCountOfTheBookPages());
    }

    @When("I scroll the gallery page {}")
    public void scrollTheGalleryPageDown(EntireElementSwipeDirection entireElementSwipeDirection) {
        pdfTableOfContentsScreen.scrollGallery(entireElementSwipeDirection);
    }

    @Then("Page has scrolled and count of books have changed {string}")
    public void pageHasScrolledAndAppearedNewElementsCompareToBookPagesList(String countOfTheBookPagesKey) {
        int countOfTheBookPages = context.get(countOfTheBookPagesKey);
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() ->
                        countOfTheBookPages != pdfTableOfContentsScreen.getCountOfTheBookPages()),
                String.format("Count of the book pages equal to have gotten before scrolling. "
                                + "Actual count of pages %1$d should not be equal to the count before scrolling %2$d.",
                        pdfTableOfContentsScreen.getCountOfTheBookPages(), countOfTheBookPages));
    }

    @When("I open any page on the gallery screen")
    public void openAnyPageOnTheGalleryScreen() {
        int countOfPages = pdfTableOfContentsScreen.getCountOfTheBookPages();
        pdfTableOfContentsScreen.openGalleryPage(RandomUtils.nextInt(0, countOfPages));
    }

    @When("I click the search in the pdf button")
    public void openSearchPdf() {
        pdfReaderScreen.openSearchPdf();
    }

    @Then("The search in the pdf page opened")
    public void checkThatPdfSearchScreenVisible() {
        Assert.assertTrue(pdfSearchScreen.state().isDisplayed(), "Pdf search screen was not opened");
    }

    @When("I am typing {string} to the search field and apply search")
    public void checkThatPdfSearchScreenVisible(String textToBeFound) {
        pdfSearchScreen.findTextInTheDocument(textToBeFound);
    }

    @Then("Found lines should contain {string} in themselves")
    public void checkThatPdfFoundLinesContainText(String textToBeContained) {
        SoftAssert softAssert = new SoftAssert();
        pdfSearchScreen.getListOfFoundItems()
                .forEach(line -> softAssert.assertTrue(line.contains(textToBeContained), String.format("Line '%1$s' does not contain text '%2$s'", line, textToBeContained)));
        softAssert.assertAll("Checking that all lines contain text");
    }

    @When("I open the first found item")
    public void openFirstFoundItem() {
        pdfSearchScreen.openSearchedItemByName(pdfSearchScreen.getListOfFoundItems().get(0));
    }

    @When("I save page number as {string} of the first item")
    public void savePageNumberOfTheFirstItem(String pageKey) {
        context.add(pageKey, pdfSearchScreen.getSearchedItemPageNumber(pdfSearchScreen.getListOfFoundItems().get(0)));
    }

    @Then("Reader screen for {} type book {string} is present")
    public void readerScreenForEbookTypeIsPresent(ReaderType readerType, String bookInfoKey) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        switch (readerType) {
            case EBOOK:
                if (epubReaderScreen.isBookNamePresent()) {
                    assertBookName(catalogBookModel);
                } else {
                    assertPdfBookName(catalogBookModel);
                }
                break;
            case AUDIOBOOK:
                Assert.assertTrue(audioPlayerScreen.state().waitForDisplayed(), "Audiobook screen is not present");
                break;
        }
    }

    private void assertBookName(CatalogBookModel catalogBookModel) {
        Assert.assertEquals(removeSpaces(epubReaderScreen.getBookName()).toLowerCase(), removeSpaces(catalogBookModel.getTitle().toLowerCase()), "Book name is not correct");
    }

    private String removeSpaces(String text) {
        return text.replace("   ", " ");
    }

    private boolean isPageNumberEqual(String pageNumber) {
        return epubReaderScreen.getPageNumberInfo().equals(pageNumber);
    }

    private void checkPageNumberIsEqualTo(int pageNumber) {
        Assert.assertEquals(pdfReaderScreen.getPageNumber(), pageNumber, "Page number is not correct");
    }

    private void checkPageNumberIsNotEqualTo(int pageNumber) {
        Assert.assertNotEquals(pdfReaderScreen.getPageNumber(), pageNumber, "Page number is not correct");
    }

    private void changeSetting(ReaderSettingKeys settingName) {
        epubReaderScreen.openFontSettings();
        fontChoicesScreen.setSetting(settingName);
        fontChoicesScreen.closeFontChoices();
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
}
