package stepdefinitions.catalog.components;

import aquality.appium.mobile.application.AqualityServices;
import constants.application.timeouts.CategoriesTimeouts;
import constants.context.ContextLibrariesKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import constants.localization.application.catalog.BookActionButtonNames;
import constants.localization.application.catalog.CategoriesNamesKeys;
import constants.localization.application.facetedSearch.FacetAvailabilityKeys;
import constants.localization.application.facetedSearch.FacetSortByKeys;
import framework.utilities.ScenarioContext;
import models.android.BookDetailsScreenInformationBlockModel;
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
import screens.notifications.NotificationModal;
import screens.subcategory.SubcategoryScreen;
import stepdefinitions.BaseSteps;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractCatalogSteps extends BaseSteps implements ICatalogSteps {
    protected final BottomMenuForm bottomMenuForm;
    protected final CatalogScreen catalogScreen;
    protected final SubcategoryScreen subcategoryScreen;
    protected final AgeGateScreen ageGateScreen;
    protected final BookDetailsScreen bookDetailsScreen;
    protected final MainCatalogToolbarForm mainCatalogToolbarForm;
    protected final CatalogBooksScreen catalogBooksScreen;
    protected final FacetedSearchScreen facetedSearchScreen;
    protected final ScenarioContext context;
    protected final NotificationModal notificationModal;

    public AbstractCatalogSteps(ScenarioContext context) {
        this.context = context;
        mainCatalogToolbarForm = AqualityServices.getScreenFactory().getScreen(MainCatalogToolbarForm.class);
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        catalogScreen = AqualityServices.getScreenFactory().getScreen(CatalogScreen.class);
        ageGateScreen = AqualityServices.getScreenFactory().getScreen(AgeGateScreen.class);
        bookDetailsScreen = AqualityServices.getScreenFactory().getScreen(BookDetailsScreen.class);
        subcategoryScreen = AqualityServices.getScreenFactory().getScreen(SubcategoryScreen.class);
        catalogBooksScreen = AqualityServices.getScreenFactory().getScreen(CatalogBooksScreen.class);
        facetedSearchScreen = AqualityServices.getScreenFactory().getScreen(FacetedSearchScreen.class);
        notificationModal = AqualityServices.getScreenFactory().getScreen(NotificationModal.class);
    }

    @Override
    public void booksFeedIsLoaded() {
        Assert.assertTrue(catalogScreen.state().waitForDisplayed(Duration.ofMillis(
                CategoriesTimeouts.TIMEOUT_WAIT_UNTIL_CATEGORY_PAGE_LOAD.getTimeoutMillis())),
                "Books feed is not loaded");
    }

    @Override
    public void getNamesOfBooksAndSaveThem(String booksNamesListKey) {
        context.add(booksNamesListKey, catalogScreen.getListOfBooksNames());
    }

    @Override
    public void checkListOfBooksIsNotEqualToSavedListOfBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertNotEquals(catalogScreen.getListOfBooksNames(), expectedList,
                "Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Override
    public void openLibraryFromSideMenu(String libraryName) {
        mainCatalogToolbarForm.chooseAnotherLibrary();
        catalogScreen.openLibrary(libraryName);
    }

    @Override
    public void openCatalogWithAgeCheck() {
        bottomMenuForm.open(BottomMenu.CATALOG);
        if (ageGateScreen.state().isDisplayed()) {
            ageGateScreen.approveAge();
        }
    }

    @Override
    public void openBooks() {
        bottomMenuForm.open(BottomMenu.BOOKS);
    }

    @Override
    public void getBookFromShelfAndSaveItAsBookInfo(String bookInfoKey) {
        CatalogBookModel bookModel = new CatalogBookModel();
        bookModel.setImageTitle(catalogScreen.getBookName(1));
        context.add(bookInfoKey, bookModel);
        catalogScreen.clickBook(1);
        bookDetailsScreen.downloadBook();
    }

    @Override
    public void checkCurrentLibraryIsCorrect(String expectedLibraryName) {
        Assert.assertEquals(mainCatalogToolbarForm.getCatalogName(), expectedLibraryName,
                "Current library name is not correct");
    }

    @Override
    public void openCategory(String categoryName) {
        catalogScreen.openCategory(categoryName);
    }

    public abstract void checkCurrentCategoryName(String expectedCategoryName);

    @Override
    public void checkCurrentCategoryNameByLocalization(CategoriesNamesKeys categoriesNamesKeys) {
        checkCurrentCategoryName(categoriesNamesKeys.i18n());
    }

    @Override
    public void checkSubcategoryScreenIsPresent() {
        Assert.assertTrue(subcategoryScreen.state().waitForDisplayed(), "Subcategory screen is not present");
    }

    @Override
    public void checkFollowingSubcategoriesArePresent(List<String> expectedValuesList) {
        Assert.assertTrue(expectedValuesList.stream().allMatch(catalogScreen::isSubcategoryPresent),
                "Not all categories are present");
    }

    @Override
    public void openCategoryByChain(List<String> categoriesChain) {
        IntStream.range(0, categoriesChain.size()).forEach(index -> {
            openCategory(categoriesChain.get(index));
            if (index != categoriesChain.size() - 1) {
                Assert.assertTrue(catalogScreen.isCategoryPageLoad(), "Check that category page has been loaded");
            }
        });
    }

    @Override
    public void openBookDetailsExecuteBookActionAndSaveItToContext(
            BookActionButtonKeys actionButtonKey, String bookInfoKey) {
        catalogBooksScreen.openBookDetailsWithAction(actionButtonKey);
        CatalogBookModel catalogBookModel = bookDetailsScreen.getBookInfo();
        bookDetailsScreen.clickActionButton(actionButtonKey);
        notificationModal.handleBookActionsAndNotificationPopups(actionButtonKey);
        context.add(bookInfoKey, catalogBookModel);
    }

    @Override
    public void executeBookActionAndSaveItToContextAndLibraryCancel(
            BookActionButtonKeys actionButtonKey, String bookInfoKey) {
        context.add(bookInfoKey, catalogBooksScreen.scrollToTheBookAndClickAddButton(actionButtonKey));
        notificationModal.handleBookActionsAndNotificationPopups(actionButtonKey);
    }

    @Override
    public void performActionOnBookOfTypeAndSaveIt(BookActionButtonKeys actionButtonKey, String bookType, String bookInfoKey) {
        context.add(bookInfoKey, catalogBooksScreen.scrollToTheBookByTypeAndClickAddButton(actionButtonKey, bookType));
    }

    @Override
    public void performActionOnBookByNameAndSaveIt(BookActionButtonKeys actionButtonKey, String bookName, String bookInfoKey) {
        context.add(bookInfoKey, catalogBooksScreen.scrollToTheBookByNameAndClickAddButton(actionButtonKey, bookName));
    }

    @Override
    public void clickOnTheBookAddButtonOnCatalogBooksScreen(String bookInfoKey, BookActionButtonKeys key) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        catalogBooksScreen.clickTheBookByTitleBtnWithKey(catalogBookModel.getTitle(), key);
        notificationModal.handleBookActionsAndNotificationPopups(key);
    }

    @Override
    public void saveLibraryForCancel(ContextLibrariesKeys contextLibrariesKeys) {
        String libraryName = mainCatalogToolbarForm.getCatalogName();
        List<String> listOfLibraries = context.containsKey(contextLibrariesKeys.getKey())
                ? context.get(contextLibrariesKeys.getKey())
                : new ArrayList<>();

        listOfLibraries.add(libraryName);
        context.add(contextLibrariesKeys.getKey(), listOfLibraries);
    }

    @Override
    public void saveLibraryForCancel(String libraryName, ContextLibrariesKeys contextLibrariesKeys) {
        List<String> listOfLibraries = context.containsKey(contextLibrariesKeys.getKey())
                ? context.get(contextLibrariesKeys.getKey())
                : new ArrayList<>();

        listOfLibraries.add(libraryName);
        context.add(contextLibrariesKeys.getKey(), listOfLibraries);
    }

    @Override
    public void checkCountOfBooksInFirstLaneIsUpTo(int countOfBooks) {
        Assert.assertTrue(countOfBooks >= catalogScreen.getListOfAllBooksNamesInFirstLane().size(),
                "Count of books is bigger then " + countOfBooks);
    }

    @Override
    public void checkCountOfBooksInSubcategoryLaneIsUpTo(String lineName, int countOfBooks) {
        int foundCountOfBooks = catalogScreen
                .getListOfAllBooksNamesInSubcategoryLane(lineName)
                .size();
        Assert.assertTrue(countOfBooks >= foundCountOfBooks,
                String.format("Expected count of books bigger or equal to %1$s but found %2$s", countOfBooks,
                        foundCountOfBooks));
    }

    @Override
    public void checkBookInfoIsOpened(String bookInfoKey) {
        Assert.assertEquals(bookDetailsScreen.getBookInfo(),
                Optional.ofNullable(context.get(bookInfoKey)).orElse(bookInfoKey), "Expected book is not opened");
    }

    @Override
    public void openFirstBookInSubcategoryListAndSaveIt(String bookInfoKey) {
        context.add(bookInfoKey, subcategoryScreen.getFirstBookInfo());
        subcategoryScreen.openFirstBook();
    }

    @Override
    public void switchToCatalogTab(String catalogTab) {
        catalogScreen.switchToCatalogTab(catalogTab);
    }

    @Override
    public void checkAllPresentBooksAreAudiobooks() {
        Assert.assertTrue(catalogScreen.getListOfBooksNames().stream().allMatch(x -> x.toLowerCase().contains("audiobook")),
                "Not all present books are audiobooks");
    }

    @Override
    public void sortBooksBy(FacetSortByKeys sortingCategory) {
        facetedSearchScreen.sortBy();
        facetedSearchScreen.changeSortByTo(sortingCategory);
    }

    @Override
    public void saveListOfBooks(String booksInfoKey) {
        context.add(booksInfoKey, subcategoryScreen.getBooksInfo());
    }

    @Override
    public void checkAllBooksCanBeDownloaded() {
        Assert.assertTrue(subcategoryScreen.getAllButtonsNames()
                        .stream()
                        .allMatch(x -> x.equals(BookActionButtonNames.DOWNLOAD_BUTTON_NAME)),
                "Not all present books can be downloaded");
    }

    @Override
    public void checkAllBooksCanBeLoanedOrDownloaded() {
        Assert.assertTrue(subcategoryScreen.getAllButtonsNames()
                        .stream()
                        .allMatch(x -> x.equals(BookActionButtonNames.GET_BUTTON_NAME) || x.equals(BookActionButtonNames.DOWNLOAD_BUTTON_NAME)),
                "Not all present books can be loaned or downloaded");
    }

    @Override
    public void checkListOfBooksOnSubcategoryScreenIsNotEqualToListOfSavedBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertNotEquals(subcategoryScreen.getBooksInfo(), expectedList,
                "Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Override
    public void checkBooksAreSortedByAuthorAscending() {
        List<String> list = subcategoryScreen.getAuthorsInfo();
        List<String> listOfSurnames = getSurnames(list);
        Assert.assertEquals(listOfSurnames, listOfSurnames.stream().sorted().collect(Collectors.toList()),
                "Lists of authors is not sorted properly" + list.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @Override
    public void booksAreSortedByTitleAscending() {
        List<String> list = subcategoryScreen.getTitlesInfo();
        Assert.assertEquals(list, list.stream().sorted().collect(Collectors.toList()),
                "Lists of authors is not sorted properly" + list.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    protected List<String> getSurnames(List<String> list) {
        List<String> listOfSurnames = new ArrayList<>();
        for (String authorName : list) {
            String[] separatedName = authorName.split("\\s");
            if (separatedName.length > 1) {
                if (authorName.contains(",")) {
                    listOfSurnames.add(separatedName[0]);
                } else if (authorName.contains(".")) {
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

    @Override
    public void checkFollowingValuesInTheInformationBlockArePresent(
            List<BookDetailsScreenInformationBlockModel> expectedValuesList) {
        Assert.assertTrue(expectedValuesList.stream().allMatch(listElement ->
                        bookDetailsScreen.isValueInTheInformationBlockPresent(listElement.getKey(),
                                listElement.getValue())),
                "Not all information block values are present");
    }

    @Override
    public void checkDescriptionHasText(final String description) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bookDetailsScreen.isDescriptionPresent(), "Description does not present");
        softAssert.assertEquals(StringUtils.trim(bookDetailsScreen.getDescriptionText()), StringUtils.trim(description),
                "Description has not correct text");
        softAssert.assertAll();
    }

    @Override
    public void openRelatedBooks() {
        bookDetailsScreen.clickRelatedBooks();
    }

    @Override
    public void goBackToThePreviousCatalogScreen() {
        mainCatalogToolbarForm.goBack();
    }

    @Override
    public void checkSearchPageIsOpened() {
        Assert.assertTrue(catalogBooksScreen.state().waitForDisplayed(), "Search page is not present");
    }

    @Override
    public CatalogBookModel selectFirstFoundBookAndSaveAs(String bookInfoKey) {
        CatalogBookModel catalogBookModel = catalogBooksScreen.selectFirstFoundBook();
        context.add(bookInfoKey, catalogBookModel);
        return catalogBookModel;
    }

    @Override
    public void checkCountOfBooksInSearchResultIsUpTo(int countOfBooks) {
        int foundBooksCount = catalogBooksScreen.getFoundBooksCount();
        Assert.assertTrue(countOfBooks >= foundBooksCount,
                String.format("Found count of books (%d) is bigger than expected - %d", foundBooksCount, countOfBooks));
    }

    @Override
    public void checkThatSavedBookContainButtonAtCatalogBooksScreen(
            final String bookInfoKey, final BookActionButtonKeys key) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        Assert.assertTrue(catalogBooksScreen.isBookAddButtonTextEqualTo(
                catalogBookModel.getTitle(), key),
                String.format("Book with title '%1$s' add button does not contain text '%2$s'",
                        catalogBookModel.getTitle(), key.i18n()));
    }

    @Override
    public void checkThatSavedBookContainButtonAtBookDetailsScreen(final BookActionButtonKeys key) {
        Assert.assertTrue(bookDetailsScreen.isBookAddButtonTextEqualTo(key),
                String.format("Opened book add button does not contain text %1$s", key.i18n()));
    }

    @Override
    public void deleteBookFromBookDetailsScreen() {
        bookDetailsScreen.deleteBook();
        notificationModal.handleBookActionsAndNotificationPopups(BookActionButtonKeys.DELETE);
    }

    @Override
    public void openBookDetailsByClickingOnCover(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        subcategoryScreen.openBook(bookInfo);
    }

    @Override
    public void pressOnTheBookDetailsScreenAtTheActionButton(BookActionButtonKeys actionButton) {
        bookDetailsScreen.clickActionButton(actionButton);
        notificationModal.handleBookActionsAndNotificationPopups(actionButton);
    }

    @Override
    public void checkThatTheActionButtonTextEqualToTheExpected(BookActionButtonKeys actionButton) {
        Assert.assertTrue(bookDetailsScreen.isBookAddButtonTextEqualTo(actionButton),
                "I check that the action button text equal to the " + actionButton.i18n());
    }

    @Override
    public void checkThatTheActionButtonTextEqualToTheExpected(FacetAvailabilityKeys facetAvailabilityKeys) {
        facetedSearchScreen.openAvailabilityMenu();
        facetedSearchScreen.changeAvailabilityTo(facetAvailabilityKeys);
    }
}