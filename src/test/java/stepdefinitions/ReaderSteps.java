package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.Then;
import models.android.AndroidCatalogBookModel;
import org.testng.Assert;
import screens.reader.ReaderScreen;

public class ReaderSteps {
    private final ReaderScreen readerScreen;
    private final ScenarioContext context;

    @Inject
    public ReaderSteps(ScenarioContext context) {
        readerScreen = AqualityServices.getScreenFactory().getScreen(ReaderScreen.class);
        this.context = context;
    }

    @Then("Book {string} is present on screen")
    public void bookBookInfoIsPresentOnScreen(String bookInfoKey) {
        AndroidCatalogBookModel androidCatalogBookModel = context.get(bookInfoKey);
        Assert.assertEquals(androidCatalogBookModel.getTitle(), readerScreen.getBookName(), "Book name is not correct");
    }
}
