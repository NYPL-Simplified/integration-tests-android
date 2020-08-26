package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import screens.books.BooksScreen;

public class BooksSteps {
    private final BooksScreen booksScreen;
    private ScenarioContext context;

    @Inject
    public BooksSteps(ScenarioContext context) {
        this.context = context;
        booksScreen = AqualityServices.getScreenFactory().getScreen(BooksScreen.class);
    }

    @Then("No books are present in Books list")
    public void checkNoBooksArePresentInBooksList() {
        Assert.assertTrue(booksScreen.isNoBooksMessagePresent(), "Books are present in Books list");
    }

    @Then("Book {string} is present in Books List")
    public void checkBookInfoIsPresentInBooksList(String bookInfoKey) {
        String bookInfo = context.get(bookInfoKey);
        Assert.assertTrue(booksScreen.isBookPresent(bookInfo), "Book '" + bookInfo + "' is not present in Books List");
    }
}
