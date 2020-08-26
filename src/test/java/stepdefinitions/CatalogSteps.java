package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.agegate.AgeGateScreen;
import screens.bookDetails.BookDetailsScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.CatalogScreen;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogSteps {
    private final BottomMenuForm bottomMenuForm;
    private final CatalogScreen catalogScreen;
    private final AgeGateScreen ageGateScreen;
    private final BookDetailsScreen bookDetailsScreen;
    private ScenarioContext context;

    @Inject
    public CatalogSteps(ScenarioContext context) {
        this.context = context;
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        catalogScreen = AqualityServices.getScreenFactory().getScreen(CatalogScreen.class);
        ageGateScreen = AqualityServices.getScreenFactory().getScreen(AgeGateScreen.class);
        bookDetailsScreen = AqualityServices.getScreenFactory().getScreen(BookDetailsScreen.class);
    }

    @Then("Books feed is loaded")
    public void booksFeedIsLoaded() {
        Assert.assertTrue(catalogScreen.state().waitForDisplayed(), "Books feed is not loaded");
    }

    @When("I get names of books on screen and save them as {string}")
    public void getNamesOfBooksAndSaveThem(String booksNamesListKey) {
        context.add(booksNamesListKey, catalogScreen.getListOfBooksNames());
    }

    @Then("List of books on screen is not equal to list of books saved as {string}")
    public void checkListOfBooksIsNotEqualToSavedListOfBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertNotEquals(catalogScreen.getListOfBooksNames(), expectedList,
                "Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @And("I switch to {string} from side menu")
    public void openLibraryFromSideMenu(String libraryName) {
        catalogScreen.openLibrary(libraryName);
    }

    @And("I open Catalog")
    public void openCatalogWithAgeCheck() {
        bottomMenuForm.open(BottomMenu.CATALOG);
        if (ageGateScreen.state().isDisplayed()) {
            ageGateScreen.approveAge();
        }
    }

    @And("I open Books")
    public void openBooks() {
        bottomMenuForm.open(BottomMenu.BOOKS);
    }

    @And("I get first book from shelf and save it as {string}")
    public void getBookFromShelfAndSaveItAsBookInfo(String bookInfoKey) {
        String bookName = catalogScreen.getBookName(1);
        context.add(bookInfoKey, bookName);
        catalogScreen.clickBook(1);
        bookDetailsScreen.downloadBook();
    }
}
