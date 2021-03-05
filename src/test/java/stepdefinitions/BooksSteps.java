package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.CatalogBookModel;
import org.testng.Assert;
import screens.bookDetails.BookDetailsScreen;
import screens.books.BooksScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;

public class BooksSteps {
    private final BooksScreen booksScreen;
    private final BottomMenuForm bottomMenuForm;
    private ScenarioContext context;
    private BookDetailsScreen detailsScreen;

    @Inject
    public BooksSteps(ScenarioContext context) {
        this.context = context;
        booksScreen = AqualityServices.getScreenFactory().getScreen(BooksScreen.class);
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        detailsScreen = AqualityServices.getScreenFactory().getScreen(BookDetailsScreen.class);
    }

    @And("I click {} for a opened book")
    public void reserveBookFromBookDetail(BookActionButtonKeys actionButtonKey) {
        detailsScreen.clickActionButton(actionButtonKey);
    }

    @Then("No books are present in Books list")
    public void checkNoBooksArePresentInBooksList() {
        Assert.assertTrue(booksScreen.isNoBooksMessagePresent(), "Books are present in Books list");
    }

    @Then("Book {string} is present in Books List")
    public void checkBookInfoIsPresentInBooksList(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        booksScreen.state().waitForDisplayed();
        AqualityServices.getConditionalWait().waitFor(() -> booksScreen.isNoBooksMessagePresent() || booksScreen.getCountOfBooks() > 0);
        Assert.assertTrue(booksScreen.isBookPresent(bookInfo), String.format("Book '%s' is not present in Books List", bookInfo));
        bottomMenuForm.open(BottomMenu.BOOKS);
    }

    @Then("Book {string} is not present in Books List")
    public void checkBookInfoIsNotPresentInBooksList(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        Assert.assertFalse(booksScreen.isBookPresent(bookInfo), String.format("Book '%s' is present in Books List", bookInfo));
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
