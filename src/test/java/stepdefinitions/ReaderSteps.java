package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import screens.reader.ReaderScreen;

public class ReaderSteps {
    private final ReaderScreen readerScreen;

    public ReaderSteps() {
        readerScreen = AqualityServices.getScreenFactory().getScreen(ReaderScreen.class);
    }

    @Then("Book cover is present on screen")
    public void bookCoverIsPresentOnScreen() {
        Assert.assertTrue(readerScreen.isBookCoverPresent(), "Book cover is not present on screen");
    }
}
