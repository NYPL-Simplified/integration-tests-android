package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.agegate.AgeGateScreen;
import screens.bookDetails.BookDetailsScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.CatalogScreen;
import screens.subcategory.SubcategoryScreen;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogSteps {
    private final BottomMenuForm bottomMenuForm;
    private final CatalogScreen catalogScreen;
    private final SubcategoryScreen subcategoryScreen;
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
        subcategoryScreen = AqualityServices.getScreenFactory().getScreen(SubcategoryScreen.class);
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
    @Given("Catalog is opened")
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

    @And("I Get first book from shelf and save it as {string}")
    public void getBookFromShelfAndSaveItAsBookInfo(String bookInfoKey) {
        context.add(bookInfoKey, catalogScreen.getBookName(1));
        catalogScreen.clickBook(1);
        bookDetailsScreen.downloadBook();
    }

    @And("Current library is {string} in Catalog")
    public void checkCurrentLibraryIsCorrect(String expectedLibraryName) {
        Assert.assertEquals(expectedLibraryName, catalogScreen.getLibraryName(), "Current library name is not correct");
    }

    @And("I open {string} category")
    @When("I open {string} subcategory")
    public void openCategory(String categoryName) {
        catalogScreen.openCategory(categoryName);
    }

    @Then("Current category name is {string}")
    public void checkCurrentCategoryName(String expectedCategoryName) {
        Assert.assertEquals(expectedCategoryName, catalogScreen.getCategoryName(), "Current category name is not correct");
    }

    @Then("Subcategory screen is present")
    public void checkSubcategoryScreenIsPresent() {
        Assert.assertTrue(subcategoryScreen.state().waitForDisplayed(), "Subcategory screen is not present");
    }

    @And("Subcategory name is {string}")
    public void checkSubcategoryNameIsCorrect(String expectedSubcategoryName) {
        Assert.assertEquals(expectedSubcategoryName, subcategoryScreen.getSubcategoryName(),
                "Current subcategory name is not correct");
    }

    @And("Following subcategories are present:")
    public void checkFollowingSubcategoriesArePresent(List<String> expectedValuesList) {
        Assert.assertTrue(expectedValuesList.stream().allMatch(x -> catalogScreen.isSubcategoryPresent(x)),
                "Not all categories are present");
    }

    @When("I switch to {string} catalog tab")
    public void iSwitchToAudiobooksCatalogTab(String catalogTab) {
        catalogScreen.switchToCatalogTag(catalogTab);
    }

    @Then("All present books are audiobooks")
    public void allPresentBooksAreAudiobooks() {
        Assert.assertTrue(catalogScreen.getListOfBooksNames().stream().allMatch(x -> x.contains("audiobook")),
                "Not all present books are audiobooks");
    }

    @Then("All present books are ebooks")
    public void allPresentBooksAreEbooks() {
        Assert.assertTrue(catalogScreen.getListOfBooksNames().stream().allMatch(x -> x.contains("ebook")),
                "Not all present books are ebooks");
    }

    @And("I sort books by {string}")
    public void iSortBooksByAuthor(String sortingCategory) {
        subcategoryScreen.sortBy(sortingCategory);
    }

    @When("I save list of books as {string}")
    public void iSaveListOfBooksAsRecentlyAddedListOfBooks(String booksInfoKey) {
        context.add(booksInfoKey, subcategoryScreen.getBooksInfo());
    }

    @And("I select book by Availability - {string}")
    public void iSelectBookByAvailabilityAvailableNow(String sortingCategory) {
        subcategoryScreen.sortByAvailability(sortingCategory);
    }

    @Then("All books can be downloaded")
    public void allBooksCanBeDownloaded() {
        Assert.assertTrue(subcategoryScreen.getAllButtonsNames().stream().allMatch(x -> x.equals("Download")),
                "Not all present books can be downloaded");
    }

    @Then("All books can be loaned or downloaded")
    public void allBooksCanBeLoanedOrDownloaded() {
        Assert.assertTrue(subcategoryScreen.getAllButtonsNames().stream().allMatch(x -> x.equals("Get")),
                "Not all present books can be loaned or downloaded");
    }

    @Then("List of books on subcategory screen is equal to list of books saved as {string}")
    public void listOfBooksOnSubcategoryScreenIsEqualToListOfBooksSavedAsRandomListOfBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertEquals(subcategoryScreen.getBooksInfo(), expectedList,
                "Lists of books are not equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Then("List of books on subcategory screen is not equal to list of books saved as {string}")
    public void listOfBooksOnSubcategoryScreenIsNotEqualToListOfBooksSavedAsListOfBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertNotEquals(subcategoryScreen.getBooksInfo(), expectedList,
                "Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Then("Books are sorted by Author ascending")
    public void booksAreSortedByAuthorAscending() {
        List<String> list = subcategoryScreen.getAuthorsInfo();
        Assert.assertEquals(list, list.stream().sorted().collect(Collectors.toList()),
                "Lists of authors is not sorted properly" + list.stream().map(Object::toString).collect(Collectors.joining(", ")));

    }

    @Then("Books are sorted by Title ascending")
    public void booksAreSortedByTitleAscending() {
        List<String> list = subcategoryScreen.getTitlesInfo();
        Assert.assertEquals(list, list.stream().sorted().collect(Collectors.toList()),
                "Lists of authors is not sorted properly" + list.stream().map(Object::toString).collect(Collectors.joining(", ")));

    }
}
