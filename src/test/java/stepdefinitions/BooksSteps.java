package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.CatalogBookModel;
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
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        booksScreen.state().waitForDisplayed();
        Assert.assertTrue(booksScreen.isBookPresent(bookInfo), "Book '" + bookInfo + "' is not present in Books List");
    }

    @Then("Book {string} is not present in Books List")
    public void checkBookInfoIsNotPresentInBooksList(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        Assert.assertFalse(booksScreen.isBookPresent(bookInfo), "Book '" + bookInfo + "' is present in Books List");
    }

    @And("Count of books is equal to {int}")
    public void checkCountOfBooksIsEqualTo(int expectedCountOfBooks) {
        Assert.assertEquals(booksScreen.getCountOfBooks(), expectedCountOfBooks, "Count of books is not correct");
    }

    @When("I refresh list of books")
    public void refreshListOfBooks() {
        booksScreen.refreshList();
    }

    @And("I Read book {string}")
    public void readBook(String bookInfoKey) {
        booksScreen.readBook(context.get(bookInfoKey));
    }
}
