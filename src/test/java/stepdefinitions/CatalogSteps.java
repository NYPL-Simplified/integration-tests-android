package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.AndroidBookDetailsScreenInformationBlockModel;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.agegate.AgeGateScreen;
import screens.bookDetails.BookDetailsScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.CatalogScreen;
import screens.subcategory.SubcategoryScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogSteps {
    public static final String LIBRARIES_FOR_CANCEL_CONTEXT_KEY = "librariesForCancel";
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
        Assert.assertTrue(expectedValuesList.stream().allMatch(catalogScreen::isSubcategoryPresent),
                "Not all categories are present");
    }

    @And("I reserve book in {string}-{string} category and save it as {string}")
    public void reserveBookInCategoryAndSaveIt(String categoryName, String subcategoryName, String bookInfoKey) {
        String libraryName = catalogScreen.getLibraryName();
        List<String> listOfLibraries;
        if (context.containsKey(LIBRARIES_FOR_CANCEL_CONTEXT_KEY)) {
            listOfLibraries = context.get(LIBRARIES_FOR_CANCEL_CONTEXT_KEY);
        } else {
            listOfLibraries = new ArrayList<>();
        }
        listOfLibraries.add(libraryName);
        context.add(LIBRARIES_FOR_CANCEL_CONTEXT_KEY, listOfLibraries);
        catalogScreen.openCategory(categoryName);
        catalogScreen.openCategory(subcategoryName);
        catalogScreen.openBookForReserve();
        bookDetailsScreen.reserveBook();
        context.add(bookInfoKey, bookDetailsScreen.getBookInfo());
    }

    @Then("Count of books in first lane is up to {int}")
    @And("Count of books in first lane is up to {int}")
    public void checkCountOfBooksInFirstLaneIsUpTo(int countOfBooks) {
        Assert.assertTrue(countOfBooks >= catalogScreen.getListOfAllBooksNamesInFirstLane().size(),
                "Count of books is bigger then " + countOfBooks);
    }

    @Then("Book {string} is opened")
    public void checkBookInfoIsOpened(String bookInfoKey) {
        Assert.assertEquals(Optional.of(context.get(bookInfoKey)).orElse(bookInfoKey),
                bookDetailsScreen.getBookInfo(), "Expected book is not opened");
    }

    @When("I open first book in subcategory list and save it as {string}")
    public void openFirstBookInSubcategoryListAndSaveIt(String bookInfoKey) {
        context.add(bookInfoKey, subcategoryScreen.getFirstBookInfo());
        subcategoryScreen.openFirstBook();
    }

    @And("The following values in the information block are present:")
    public void checkFollowingValuesInTheInformationBlockArePresent(
            List<AndroidBookDetailsScreenInformationBlockModel> expectedValuesList) {
        Assert.assertTrue(expectedValuesList.stream().allMatch(listElement ->
                        bookDetailsScreen.isValueInTheInformationBlockPresent(listElement.getKey(),
                                listElement.getValue())),
                "Not all information block values are present");
    }

    @And("Description has text")
    public void checkDescriptionHasText(String description) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookDetailsScreen.isDescriptionPresent(), "Description does not present");
        softAssert.assertEquals(bookDetailsScreen.getDescriptionText(), description, "Description has not "
                + "correct text");
        softAssert.assertAll();
    }

    @When("I open related books")
    public void openRelatedBooks() {
        bookDetailsScreen.clickRelatedBooks();
    }
}
