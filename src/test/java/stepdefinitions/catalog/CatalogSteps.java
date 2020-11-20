package stepdefinitions.catalog;

import com.google.inject.Inject;
import constants.application.ReaderType;
import constants.context.ContextLibrariesKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import constants.localization.application.catalog.CategoriesNamesKeys;
import constants.localization.application.facetedSearch.FacetAvailabilityKeys;
import constants.localization.application.facetedSearch.FacetSortByKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.BookDetailsScreenInformationBlockModel;
import models.android.CatalogBookModel;
import stepdefinitions.BaseSteps;
import stepdefinitions.catalog.components.AbstractCatalogSteps;
import stepdefinitions.catalog.components.ICatalogSteps;

import java.util.List;

public class CatalogSteps extends BaseSteps implements ICatalogSteps {
    private AbstractCatalogSteps catalogSteps;

    @Inject
    public CatalogSteps(ScenarioContext context) {
        catalogSteps = stepsFactory.getSteps(AbstractCatalogSteps.class, context);
    }

    @Then("Books feed is loaded")
    public void booksFeedIsLoaded() {
        catalogSteps.booksFeedIsLoaded();
    }

    @When("I get names of books on screen and save them as {string}")
    public void getNamesOfBooksAndSaveThem(String booksNamesListKey) {
        catalogSteps.getNamesOfBooksAndSaveThem(booksNamesListKey);
    }

    @Then("List of books on screen is not equal to list of books saved as {string}")
    public void checkListOfBooksIsNotEqualToSavedListOfBooks(String booksNamesListKey) {
        catalogSteps.checkListOfBooksIsNotEqualToSavedListOfBooks(booksNamesListKey);
    }

    @And("I switch to {string} from side menu")
    public void openLibraryFromSideMenu(String libraryName) {
        catalogSteps.openLibraryFromSideMenu(libraryName);
    }

    @And("I open Catalog")
    @Given("Catalog is opened")
    public void openCatalogWithAgeCheck() {
        catalogSteps.openCatalogWithAgeCheck();
    }

    @And("I open Books")
    public void openBooks() {
        catalogSteps.openBooks();
    }

    @And("I Download first book from shelf and save it as {string}")
    public void getBookFromShelfAndSaveItAsBookInfo(String bookInfoKey) {
        catalogSteps.getBookFromShelfAndSaveItAsBookInfo(bookInfoKey);
    }

    @And("Current library is {string} in Catalog")
    public void checkCurrentLibraryIsCorrect(String expectedLibraryName) {
        catalogSteps.checkCurrentLibraryIsCorrect(expectedLibraryName);
    }

    @And("I open {string} category")
    @When("I open {string} subcategory")
    public void openCategory(String categoryName) {
        catalogSteps.openCategory(categoryName);
    }

    @Then("Current category name is {string}")
    @And("Subcategory name is {string}")
    public void checkCurrentCategoryName(String expectedCategoryName) {
        catalogSteps.checkCurrentCategoryName(expectedCategoryName);
    }

    @Then("Current category name by localization is {}")
    @And("Subcategory name by localization  is {}")
    public void checkCurrentCategoryNameByLocalization(CategoriesNamesKeys categoriesNamesKeys) {
        catalogSteps.checkCurrentCategoryNameByLocalization(categoriesNamesKeys);
    }

    @Then("Subcategory screen is present")
    public void checkSubcategoryScreenIsPresent() {
        catalogSteps.checkSubcategoryScreenIsPresent();
    }

    @And("Following subcategories are present:")
    public void checkFollowingSubcategoriesArePresent(List<String> expectedValuesList) {
        catalogSteps.checkFollowingSubcategoriesArePresent(expectedValuesList);
    }

    @When("I open category by chain:")
    @And("Open category by chain:")
    public void openCategoryByChain(List<String> categoriesChain) {
        catalogSteps.openCategoryByChain(categoriesChain);
    }

    @When("I open the book details for the subsequent {} and save it as {string}")
    @And("Open the book details for the subsequent {} and save it as {string}")
    public void openBookDetailsExecuteBookActionAndSaveItToContext(
            BookActionButtonKeys actionButtonKey, String bookInfoKey) {
        catalogSteps.openBookDetailsExecuteBookActionAndSaveItToContext(actionButtonKey, bookInfoKey);
    }

    @And("{} book and save it as {string}")
    public void executeBookActionAndSaveItToContextAndLibraryCancel(
            BookActionButtonKeys actionButtonKey, String bookInfoKey) {
        catalogSteps.executeBookActionAndSaveItToContextAndLibraryCancel(actionButtonKey, bookInfoKey);
    }

    @When("I {} book of {string} type and save it as {string}")
    @And("{} book of {string} type and save it as {string}")
    public void performActionOnBookOfTypeAndSaveIt(BookActionButtonKeys actionButtonKey, String bookType, String bookInfoKey) {
        catalogSteps.performActionOnBookOfTypeAndSaveIt(actionButtonKey, bookType, bookInfoKey);
    }

    @When("I {} book by name {string} and save it as {string}")
    @And("{} book by name {string} type and save it as {string}")
    public void performActionOnBookByNameAndSaveIt(BookActionButtonKeys actionButtonKey, String bookName, String bookInfoKey) {
        catalogSteps.performActionOnBookByNameAndSaveIt(actionButtonKey, bookName, bookInfoKey);
    }

    @When("I open book with name {string} and save it as {string}")
    public void openBookWithNameAndSaveIt(String bookName, String bookInfoKey) {
        catalogSteps.openBookWithGivenName(bookName, bookInfoKey);
    }

    @When("I click on the book {string} button {} on catalog books screen")
    public void clickOnTheBookAddButtonOnCatalogBooksScreen(String bookInfoKey, BookActionButtonKeys key) {
        catalogSteps.clickOnTheBookAddButtonOnCatalogBooksScreen(bookInfoKey, key);
    }

    @And("Save current library for {} books after test")
    public void saveLibraryForCancel(ContextLibrariesKeys contextLibrariesKeys) {
        catalogSteps.saveLibraryForCancel(contextLibrariesKeys);
    }

    @And("Save current {string} library for {} books after test")
    public void saveLibraryForCancel(String libraryName, ContextLibrariesKeys contextLibrariesKeys) {
        catalogSteps.saveLibraryForCancel(libraryName, contextLibrariesKeys);
    }

    @And("Count of books in first lane is up to {int}")
    public void checkCountOfBooksInFirstLaneIsUpTo(int countOfBooks) {
        catalogSteps.checkCountOfBooksInFirstLaneIsUpTo(countOfBooks);
    }

    @And("Count of books in subcategory {string} lane is up to {int}")
    public void checkCountOfBooksInSubcategoryLaneIsUpTo(String lineName, int countOfBooks) {
        catalogSteps.checkCountOfBooksInSubcategoryLaneIsUpTo(lineName, countOfBooks);
    }

    @Then("Book {string} is opened")
    public void checkBookInfoIsOpened(String bookInfoKey) {
        catalogSteps.checkBookInfoIsOpened(bookInfoKey);
    }

    @When("I open first book in subcategory list and save it as {string}")
    public void openFirstBookInSubcategoryListAndSaveIt(String bookInfoKey) {
        catalogSteps.openFirstBookInSubcategoryListAndSaveIt(bookInfoKey);
    }

    @When("I switch to {string} catalog tab")
    public void switchToCatalogTab(String catalogTab) {
        catalogSteps.switchToCatalogTab(catalogTab);
    }

    @Then("All present books are audiobooks")
    public void checkAllPresentBooksAreAudiobooks() {
        catalogSteps.checkAllPresentBooksAreAudiobooks();
    }

    @And("I sort books by {}")
    public void sortBooksBy(FacetSortByKeys sortingCategory) {
        catalogSteps.sortBooksBy(sortingCategory);
    }

    @When("I save list of books as {string}")
    public void saveListOfBooks(String booksInfoKey) {
        catalogSteps.saveListOfBooks(booksInfoKey);
    }

    @Then("All books can be downloaded")
    public void checkAllBooksCanBeDownloaded() {
        catalogSteps.checkAllBooksCanBeDownloaded();
    }

    @Then("All books can be loaned or downloaded")
    public void checkAllBooksCanBeLoanedOrDownloaded() {
        catalogSteps.checkAllBooksCanBeLoanedOrDownloaded();
    }

    @Then("List of books on subcategory screen is not equal to list of books saved as {string}")
    public void checkListOfBooksOnSubcategoryScreenIsNotEqualToListOfSavedBooks(String booksNamesListKey) {
        catalogSteps.checkListOfBooksOnSubcategoryScreenIsNotEqualToListOfSavedBooks(booksNamesListKey);
    }

    @Then("Books are sorted by Author ascending")
    public void checkBooksAreSortedByAuthorAscending() {
        catalogSteps.checkBooksAreSortedByAuthorAscending();
    }

    @Then("Books are sorted by Title ascending")
    public void booksAreSortedByTitleAscending() {
        catalogSteps.booksAreSortedByTitleAscending();
    }

    @And("The following values in the information block are present:")
    public void checkFollowingValuesInTheInformationBlockArePresent(
            List<BookDetailsScreenInformationBlockModel> expectedValuesList) {
        catalogSteps.checkFollowingValuesInTheInformationBlockArePresent(expectedValuesList);
    }

    @And("Description has text")
    public void checkDescriptionHasText(final String description) {
        catalogSteps.checkDescriptionHasText(description);
    }

    @When("I open related books")
    public void openRelatedBooks() {
        catalogSteps.openRelatedBooks();
    }

    @When("I go back to the previous catalog screen")
    public void goBackToThePreviousCatalogScreen() {
        catalogSteps.goBackToThePreviousCatalogScreen();
    }


    @And("Search page is opened")
    public void checkSearchPageIsOpened() {
        catalogSteps.checkSearchPageIsOpened();
    }


    @When("I open first found book from the search result and save as {string}")
    @And("Open first found book from the search result and save as {string}")
    public CatalogBookModel selectFirstFoundBookAndSaveAs(String bookInfoKey) {
        return catalogSteps.selectFirstFoundBookAndSaveAs(bookInfoKey);
    }

    @And("Count of books in search result is up to {int}")
    public void checkCountOfBooksInSearchResultIsUpTo(int countOfBooks) {
        catalogSteps.checkCountOfBooksInSearchResultIsUpTo(countOfBooks);
    }

    @Then("Book saved as {string} should contain {} button at catalog books screen")
    public void checkThatSavedBookContainButtonAtCatalogBooksScreen(
            final String bookInfoKey, final BookActionButtonKeys key) {
        catalogSteps.checkThatSavedBookContainButtonAtCatalogBooksScreen(bookInfoKey, key);
    }

    @Then("I check that opened book contains {} button at book details screen")
    public void checkThatSavedBookContainButtonAtBookDetailsScreen(final BookActionButtonKeys key) {
        catalogSteps.checkThatSavedBookContainButtonAtBookDetailsScreen(key);
    }

    @And("I delete book from book details screen")
    public void deleteBookFromBookDetailsScreen() {
        catalogSteps.deleteBookFromBookDetailsScreen();
    }

    @When("I open book {string} details by clicking on cover")
    public void openBookDetailsByClickingOnCover(String bookInfoKey) {
        catalogSteps.openBookDetailsByClickingOnCover(bookInfoKey);
    }

    @When("I press on the book details screen at the action button {}")
    @And("Press on the book details screen at the action button {}")
    public void pressOnTheBookDetailsScreenAtTheActionButton(BookActionButtonKeys actionButton) {
        catalogSteps.pressOnTheBookDetailsScreenAtTheActionButton(actionButton);
    }

    @And("Get book on the book details screen")
    public void getBookOnTheBookDetailsScreen() {
        catalogSteps.getBookOnTheBookDetailsScreen();
    }

    @Then("I check that the action button text equal to the {}")
    public void checkThatTheActionButtonTextEqualToTheExpected(BookActionButtonKeys actionButton) {
        catalogSteps.checkThatSavedBookContainButtonAtBookDetailsScreen(actionButton);
    }

    @When("I change books visibility to show {}")
    @And("Change books visibility to show {}")
    public void checkThatTheActionButtonTextEqualToTheExpected(FacetAvailabilityKeys facetAvailabilityKeys) {
        catalogSteps.checkThatTheActionButtonTextEqualToTheExpected(facetAvailabilityKeys);
    }

    @When("I read {} book")
    public void openGivenTypeBookReader(ReaderType readerType) {
        catalogSteps.openTypeBookReader(readerType);
    }

    @And("I open {} book from {string} lane and save book info as {string}")
    public void openBookTypeBookFromLLaneAndSaveBookInfoAs(ReaderType readerType, String laneName, String bookInfoKey) {
        catalogSteps.openBookFromLane(readerType, laneName, bookInfoKey);
    }

    @Then("Book was borrowed successfully")
    @Then("Opened book contains read button at book details screen")
    public void checkBookWasBorrowedSuccessfully() {
        catalogSteps.checkBookWasBorrowedSuccessfully();
    }
}