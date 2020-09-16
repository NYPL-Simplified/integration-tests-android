package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.AndroidCatalogBookModel;
import org.testng.Assert;
import screens.reader.ReaderScreen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void bookPageNumberIs(int pageNumber) {
        Assert.assertEquals(pageNumber, readerScreen.getPageNumber(), "Book page number is not correct");
    }

    @When("I scroll for book start")
    public void iScrollForBookStart() {
        readerScreen.scrollForBookStart();
    }

    @When("I swipe from left to right book corner")
    public void iSwipeFromLeftToRightBookCorner() {
        readerScreen.swipeFromLeftToRight();
    }

    @When("I swipe from right to left book corner")
    public void iSwipeFromRightToLeftBookCorner() {
        readerScreen.swipeFromRightToLeft();
    }

    @When("I click on left book corner")
    public void iClickOnLeftBookCorner() {
        readerScreen.clickLeftCorner();
    }

    @When("I click on right book corner")
    public void iClickOnRightBookCorner() {
        readerScreen.clickRightCorner();
    }

    @When("I save page info as {string}")
    public void iSavePageInfoAsPageInfo(String pageNumberInfo) {
        context.add(pageNumberInfo, readerScreen.getPageNumberInfo());
    }

    @Then("Book page number is bigger then previous {string}")
    public void bookPageNumberIsBiggerThenPreviousPageInfo(String pageNumberInfo) {
        String actualBookInfo = readerScreen.getPageNumberInfo();
        String expectedBookInfo = context.get(pageNumberInfo);
        int expectedPageNumber = getPageNumber(expectedBookInfo);
        int actualPageNumber = getPageNumber(actualBookInfo);
        Assert.assertTrue(expectedPageNumber + 1 == actualPageNumber ||
                ((actualPageNumber == 1 || expectedPageNumber == 1) && !getChapterName(expectedBookInfo).equals(getChapterName(actualBookInfo))));
    }

    public int getPageNumber(String text) {
        Pattern ptrn = Pattern.compile("Page (\\d+) of \\d+ \\(.*\\)");
        Matcher matcher = ptrn.matcher(text);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    public String getChapterName(String text) {
        Pattern ptrn = Pattern.compile("Page (\\d+) of \\d+ (\\(.*\\))");
        Matcher matcher = ptrn.matcher(text);
        return matcher.find() ? matcher.group(2) : "";
    }

    @Then("Book page number is smaller then previous {string}")
    public void bookPageNumberIsSmallerThenPreviousPageInfo(String pageNumberInfo) {
        String actualBookInfo = readerScreen.getPageNumberInfo();
        String expectedBookInfo = context.get(pageNumberInfo);
        int expectedPageNumber = getPageNumber(expectedBookInfo);
        int actualPageNumber = getPageNumber(actualBookInfo);
        Assert.assertTrue(expectedPageNumber - 1 == actualPageNumber ||
                ((actualPageNumber == 1 || expectedPageNumber == 1) && !getChapterName(expectedBookInfo).equals(getChapterName(actualBookInfo))));

    }
}
