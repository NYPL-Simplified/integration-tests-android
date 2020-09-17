package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.RegEx;
import framework.utilities.RegExUtil;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.AndroidCatalogBookModel;
import org.testng.Assert;
import screens.reader.ReaderScreen;

import java.util.regex.Matcher;

public class ReaderSteps {
    private final ReaderScreen readerScreen;
    private final ScenarioContext context;

    @Inject
    public ReaderSteps(ScenarioContext context) {
        readerScreen = AqualityServices.getScreenFactory().getScreen(ReaderScreen.class);
        this.context = context;
    }

    @Then("Book {string} is present on screen")
    public void checkBookInfoIsPresentOnScreen(String bookInfoKey) {
        AndroidCatalogBookModel androidCatalogBookModel = context.get(bookInfoKey);
        Assert.assertEquals(androidCatalogBookModel.getTitle(), readerScreen.getBookName(), "Book name is not correct");
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
        int expectedPageNumber = getPageNumber(expectedBookInfo);
        int actualPageNumber = getPageNumber(actualBookInfo);
        Assert.assertTrue(expectedPageNumber + 1 == actualPageNumber ||
                (actualPageNumber == 1 && !getChapterName(expectedBookInfo).equals(getChapterName(actualBookInfo))));
    }

    public int getPageNumber(String text) {
        Matcher matcher = getMatcher(text);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    public String getChapterName(String text) {
        Matcher matcher = getMatcher(text);
        return matcher.find() ? matcher.group(2) : "";
    }

    @Then("Book page number is smaller then previous {string}")
    public void bookPageNumberIsSmallerThenPreviousPageInfo(String pageNumberInfo) {
        String actualBookInfo = readerScreen.getPageNumberInfo();
        String expectedBookInfo = context.get(pageNumberInfo);
        int expectedPageNumber = getPageNumber(expectedBookInfo);
        int actualPageNumber = getPageNumber(actualBookInfo);
        Assert.assertTrue(expectedPageNumber - 1 == actualPageNumber ||
                (actualPageNumber == 1 && !getChapterName(expectedBookInfo).equals(getChapterName(actualBookInfo))));
    }

    private Matcher getMatcher(String text) {
        return RegExUtil.getMatcher(text, RegEx.PAGE_NUMBER_REGEX);
    }
}
