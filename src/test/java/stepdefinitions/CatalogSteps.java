package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.application.timeouts.CategoriesTimeouts;
import constants.context.ContextLibrariesKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import constants.localization.application.facetedSearch.FacetAvailabilityKeys;
import constants.localization.application.facetedSearch.FacetSortByKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.AndroidBookDetailsScreenInformationBlockModel;
import models.android.CatalogBookModel;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import screens.agegate.AgeGateScreen;
import screens.bookDetails.BookDetailsScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.form.MainCatalogToolbarForm;
import screens.catalog.screen.books.CatalogBooksScreen;
import screens.catalog.screen.catalog.CatalogScreen;
import screens.facetedSearch.FacetedSearchScreen;
import screens.subcategory.SubcategoryScreen;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CatalogSteps {
    private final BottomMenuForm bottomMenuForm;
    private final CatalogScreen catalogScreen;
    private final SubcategoryScreen subcategoryScreen;
    private final AgeGateScreen ageGateScreen;
    private final BookDetailsScreen bookDetailsScreen;
    private final MainCatalogToolbarForm mainCatalogToolbarForm;
    private final CatalogBooksScreen catalogBooksScreen;
    private final FacetedSearchScreen facetedSearchScreen;
    private final ScenarioContext context;

    @Inject
    public CatalogSteps(ScenarioContext context) {
        this.context = context;
        mainCatalogToolbarForm = AqualityServices.getScreenFactory().getScreen(MainCatalogToolbarForm.class);
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        catalogScreen = AqualityServices.getScreenFactory().getScreen(CatalogScreen.class);
        ageGateScreen = AqualityServices.getScreenFactory().getScreen(AgeGateScreen.class);
        bookDetailsScreen = AqualityServices.getScreenFactory().getScreen(BookDetailsScreen.class);
        subcategoryScreen = AqualityServices.getScreenFactory().getScreen(SubcategoryScreen.class);
        catalogBooksScreen = AqualityServices.getScreenFactory().getScreen(CatalogBooksScreen.class);
        facetedSearchScreen = AqualityServices.getScreenFactory().getScreen(FacetedSearchScreen.class);
    }

    @Then("Books feed is loaded")
    public void booksFeedIsLoaded() {
        Assert.assertTrue(catalogScreen.state().waitForDisplayed(Duration.ofMillis(
                CategoriesTimeouts.TIMEOUT_WAIT_UNTIL_CATEGORY_PAGE_LOAD.getTimeoutMillis())),
                "Books feed is not loaded");
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
        mainCatalogToolbarForm.chooseAnotherLibrary();
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

    @And("I Download first book from shelf and save it as {string}")
    public void getBookFromShelfAndSaveItAsBookInfo(String bookInfoKey) {
        CatalogBookModel bookModel = new CatalogBookModel();
        bookModel.setImageTitle(catalogScreen.getBookName(1));
        context.add(bookInfoKey, bookModel);
        catalogScreen.clickBook(1);
        bookDetailsScreen.downloadBook();
    }

    @And("Current library is {string} in Catalog")
    public void checkCurrentLibraryIsCorrect(String expectedLibraryName) {
        Assert.assertEquals(mainCatalogToolbarForm.getCatalogName(), expectedLibraryName,
                "Current library name is not correct");
    }

    @And("I open {string} category")
    @When("I open {string} subcategory")
    public void openCategory(String categoryName) {
        catalogScreen.openCategory(categoryName);
    }

    @Then("Current category name is {string}")
    @And("Subcategory name is {string}")
    public void checkCurrentCategoryName(String expectedCategoryName) {
        Assert.assertTrue(AqualityServices.getConditionalWait()
                        .waitFor(() -> mainCatalogToolbarForm.getCategoryName().equals(expectedCategoryName),
                                "Wait while category become correct."),
                String.format("Current category name is not correct. Expected '%1$s' but found '%2$s'",
                        mainCatalogToolbarForm.getCategoryName(), expectedCategoryName));
    }

    @Then("Subcategory screen is present")
    public void checkSubcategoryScreenIsPresent() {
        Assert.assertTrue(subcategoryScreen.state().waitForDisplayed(), "Subcategory screen is not present");
    }

    @And("Following subcategories are present:")
    public void checkFollowingSubcategoriesArePresent(List<String> expectedValuesList) {
        Assert.assertTrue(expectedValuesList.stream().allMatch(catalogScreen::isSubcategoryPresent),
                "Not all categories are present");
    }

    @When("I open category by chain:")
    @And("Open category by chain:")
    public void openCategoryByChain(List<String> categoriesChain) {
        IntStream.range(0, categoriesChain.size()).forEach(index -> {
            openCategory(categoriesChain.get(index));
            if (index != categoriesChain.size() - 1) {
                Assert.assertTrue(catalogScreen.isCategoryPageLoad(), "Check that category page has been loaded");
            }
        });
    }

    @When("I open the book details for the subsequent {} and save it as {string}")
    @And("Open the book details for the subsequent {} and save it as {string}")
    public void openBookDetailsExecuteBookActionAndSaveItToContext(
            BookActionButtonKeys actionButtonKey, String bookInfoKey) {
        catalogBooksScreen.openBookDetailsWithAction(actionButtonKey);
        bookDetailsScreen.clickActionButton(actionButtonKey);
        context.add(bookInfoKey, bookDetailsScreen.getBookInfo());
    }

    @And("{} book and save it as {string}")
    public void executeBookActionAndSaveItToContextAndLibraryCancel(
            BookActionButtonKeys actionButtonKey, String bookInfoKey) {
        context.add(bookInfoKey, catalogBooksScreen.scrollToTheBookAndClickAddButton(actionButtonKey));
    }

    @And("{} book of {string} type and save it as {string}")
    public void performActionOnBookOfTypeAndSaveIt(BookActionButtonKeys actionButtonKey, String bookType, String bookInfoKey) {
        context.add(bookInfoKey, catalogBooksScreen.scrollToTheBookAndClickAddButton(actionButtonKey, bookType));
    }

    @When("I click on the book {string} button {} on catalog books screen")
    public void clickOnTheBookAddButtonOnCatalogBooksScreen(String bookInfoKey, BookActionButtonKeys key) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        catalogBooksScreen.clickTheBookByTitleBtnWithKey(catalogBookModel.getTitle(), key);
    }

    @And("Save current library for {} books after test")
    public void saveLibraryForCancel(ContextLibrariesKeys contextLibrariesKeys) {
        String libraryName = mainCatalogToolbarForm.getCatalogName();
        List<String> listOfLibraries = context.containsKey(contextLibrariesKeys.getKey())
                ? context.get(contextLibrariesKeys.getKey())
                : new ArrayList<>();

        listOfLibraries.add(libraryName);
        context.add(contextLibrariesKeys.getKey(), listOfLibraries);
    }

    @And("Save current {string} library for {} books after test")
    public void saveLibraryForCancel(String libraryName, ContextLibrariesKeys contextLibrariesKeys) {
        List<String> listOfLibraries = context.containsKey(contextLibrariesKeys.getKey())
                ? context.get(contextLibrariesKeys.getKey())
                : new ArrayList<>();

        listOfLibraries.add(libraryName);
        context.add(contextLibrariesKeys.getKey(), listOfLibraries);
    }

    @And("Count of books in first lane is up to {int}")
    public void checkCountOfBooksInFirstLaneIsUpTo(int countOfBooks) {
        Assert.assertTrue(countOfBooks >= catalogScreen.getListOfAllBooksNamesInFirstLane().size(),
                "Count of books is bigger then " + countOfBooks);
    }

    @And("Count of books in subcategory {string} lane is up to {int}")
    public void checkCountOfBooksInSubcategoryLaneIsUpTo(String lineName, int countOfBooks) {
        int foundCountOfBooks = catalogScreen
                .getListOfAllBooksNamesInSubcategoryLane(lineName)
                .size();
        Assert.assertTrue(countOfBooks >= foundCountOfBooks,
                String.format("Expected count of books bigger or equal to %1$s but found %2$s", countOfBooks,
                        foundCountOfBooks));
    }

    @Then("Book {string} is opened")
    public void checkBookInfoIsOpened(String bookInfoKey) {
        Assert.assertEquals(bookDetailsScreen.getBookInfo(),
                Optional.ofNullable(context.get(bookInfoKey)).orElse(bookInfoKey), "Expected book is not opened");
    }

    @When("I open first book in subcategory list and save it as {string}")
    public void openFirstBookInSubcategoryListAndSaveIt(String bookInfoKey) {
        context.add(bookInfoKey, subcategoryScreen.getFirstBookInfo());
        subcategoryScreen.openFirstBook();
    }

    @When("I switch to {string} catalog tab")
    public void switchToCatalogTab(String catalogTab) {
        catalogScreen.switchToCatalogTab(catalogTab);
    }

    @Then("All present books are audiobooks")
    public void checkAllPresentBooksAreAudiobooks() {
        Assert.assertTrue(catalogScreen.getListOfBooksNames().stream().allMatch(x -> x.toLowerCase().contains("audiobook")),
                "Not all present books are audiobooks");
    }

    @And("I sort books by {}")
    public void sortBooksBy(FacetSortByKeys sortingCategory) {
        facetedSearchScreen.sortBy();
        facetedSearchScreen.changeSortByTo(sortingCategory);
    }

    @When("I save list of books as {string}")
    public void saveListOfBooks(String booksInfoKey) {
        context.add(booksInfoKey, subcategoryScreen.getBooksInfo());
    }

    @Then("All books can be downloaded")
    public void checkAllBooksCanBeDownloaded() {
        Assert.assertTrue(subcategoryScreen.getAllButtonsNames()
                        .stream()
                        .allMatch(x -> x.equals(BookActionButtonKeys.DOWNLOAD.i18n())),
                "Not all present books can be downloaded");
    }

    @Then("All books can be loaned or downloaded")
    public void checkAllBooksCanBeLoanedOrDownloaded() {
        Assert.assertTrue(subcategoryScreen.getAllButtonsNames()
                        .stream()
                        .allMatch(x -> x.equals(BookActionButtonKeys.GET.i18n())
                                || x.equals(BookActionButtonKeys.DOWNLOAD.i18n())),
                "Not all present books can be loaned or downloaded");
    }

    @Then("List of books on subcategory screen is not equal to list of books saved as {string}")
    public void checkListOfBooksOnSubcategoryScreenIsNotEqualToListOfSavedBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertNotEquals(subcategoryScreen.getBooksInfo(), expectedList,
                "Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Then("Books are sorted by Author ascending")
    public void checkBooksAreSortedByAuthorAscending() {
        List<String> list = subcategoryScreen.getAuthorsInfo();
        List<String> listOfSurnames = getSurnames(list);
        Assert.assertEquals(listOfSurnames, listOfSurnames.stream().sorted().collect(Collectors.toList()),
                "Lists of authors is not sorted properly" + list.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Then("Books are sorted by Title ascending")
    public void booksAreSortedByTitleAscending() {
        List<String> list = subcategoryScreen.getTitlesInfo();
        Assert.assertEquals(list, list.stream().sorted().collect(Collectors.toList()),
                "Lists of authors is not sorted properly" + list.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    private List<String> getSurnames(List<String> list) {
        List<String> listOfSurnames = new ArrayList<>();
        for (String authorName : list) {
            String[] separatedName = authorName.split("\\s");
            if (separatedName.length > 1) {
                if (authorName.contains(",")) {
                    listOfSurnames.add(separatedName[0]);
                } else if (authorName.contains("."))  {
                    listOfSurnames.add(separatedName[separatedName.length - 1]);
                } else {
                    listOfSurnames.add(separatedName[1]);
                }
            } else {
                listOfSurnames.add(separatedName[0]);
            }
        }
        return listOfSurnames;
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
    public void checkDescriptionHasText(final String description) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookDetailsScreen.isDescriptionPresent(), "Description does not present");
        softAssert.assertEquals(StringUtils.trim(bookDetailsScreen.getDescriptionText()), StringUtils.trim(description),
                "Description has not correct text");
        softAssert.assertAll();
    }

    @When("I open related books")
    public void openRelatedBooks() {
        bookDetailsScreen.clickRelatedBooks();
    }

    @When("I go back to the previous catalog screen")
    public void goBackToThePreviousCatalogScreen() {
        mainCatalogToolbarForm.goBack();
    }


    @And("Search page is opened")
    public void checkSearchPageIsOpened() {
        Assert.assertTrue(catalogBooksScreen.state().waitForDisplayed(), "Search page is not present");
    }

    @When("I open first found book from the search result")
    @And("Open first found book from the search result")
    public void selectFirstFoundBook() {
        catalogBooksScreen.selectFirstFoundBook();
    }

    @And("Count of books in search result is up to {int}")
    public void checkCountOfBooksInSearchResultIsUpTo(int countOfBooks) {
        Assert.assertTrue(countOfBooks >= catalogBooksScreen.getFoundBooksCount(),
                "Count of books is bigger then " + countOfBooks);
    }

    @Then("Book saved as {string} should contain {} button at catalog books screen")
    public void checkThatSavedBookContainButtonAtCatalogBooksScreen(
            final String bookInfoKey, final BookActionButtonKeys key) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        Assert.assertTrue(catalogBooksScreen.isBookAddButtonTextEqualTo(
                catalogBookModel.getTitle(), key),
                String.format("Book with title '%1$s' add button does not contain text '%2$s'",
                        catalogBookModel.getTitle(), key.i18n()));
    }

    @Then("I check that opened book contains {} button at book details screen")
    public void checkThatSavedBookContainButtonAtBookDetailsScreen(final BookActionButtonKeys key) {
        Assert.assertTrue(bookDetailsScreen.isBookAddButtonTextEqualTo(key),
                String.format("Opened book add button does not contain text %1$s", key.i18n()));
    }

    @And("I delete book from book details screen")
    public void deleteBookFromBookDetailsScreen() {
        bookDetailsScreen.deleteBook();
    }

    @When("I open book {string} details by clicking on cover")
    public void openBookDetailsByClickingOnCover(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        subcategoryScreen.openBook(bookInfo);
    }

    @When("I press on the book details screen at the action button {}")
    @And("Press on the book details screen at the action button {}")
    public void pressOnTheBookDetailsScreenAtTheActionButton(BookActionButtonKeys actionButton) {
        bookDetailsScreen.clickActionButton(actionButton);
    }

    @Then("I check that the action button text equal to the {}")
    public void checkThatTheActionButtonTextEqualToTheExpected(BookActionButtonKeys actionButton) {
        Assert.assertTrue(bookDetailsScreen.isBookAddButtonTextEqualTo(actionButton),
                "I check that the action button text equal to the " + actionButton.i18n());
    }

    @When("I change books visibility to show {}")
    @And("Change books visibility to show {}")
    public void checkThatTheActionButtonTextEqualToTheExpected(FacetAvailabilityKeys facetAvailabilityKeys) {
        facetedSearchScreen.openAvailabilityMenu();
        facetedSearchScreen.changeAvailabilityTo(facetAvailabilityKeys);
    }
}