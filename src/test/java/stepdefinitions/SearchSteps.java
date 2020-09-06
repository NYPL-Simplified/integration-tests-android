package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.catalog.form.MainCatalogToolbarForm;
import screens.search.modal.SearchModal;
import screens.search.screen.SearchPage;

public class SearchSteps {
    private final SearchPage searchPage;
    private final MainCatalogToolbarForm mainCatalogToolbarForm;
    private final SearchModal searchModal;

    public SearchSteps() {
        searchPage = AqualityServices.getScreenFactory().getScreen(SearchPage.class);
        mainCatalogToolbarForm = AqualityServices.getScreenFactory().getScreen(MainCatalogToolbarForm.class);
        searchModal = AqualityServices.getScreenFactory().getScreen(SearchModal.class);
    }

    @When("I open search modal")
    public void openSearchModal() {
        mainCatalogToolbarForm.openSearchModal();
    }

    @Then("Search modal is opened")
    public void checkSearchModalIsOpened() {
        Assert.assertTrue(searchModal.state().waitForDisplayed(), "Search modal is not loaded");
    }

    @When("I set text to the search textBox {string}")
    public void setTextToTheSearch(String searchedText) {
        searchModal.setSearchedText(searchedText);
    }

    @And("I click apply search button")
    public void clickApplySearch() {
        searchModal.applySearch();
    }

    @Then("Search modal is closed")
    public void checkSearchModalIsClosed() {
        Assert.assertTrue(searchModal.state().waitForNotDisplayed(), "Search modal is not disappear");
    }

    @And("Search page is opened")
    public void checkSearchPageIsOpened() {
        Assert.assertTrue(searchPage.state().waitForDisplayed(), "Search page is not present");
    }

    @When("I open first found book from the search result")
    public void selectFirstFoundBook() {
        searchPage.selectFirstFoundBook();
    }

    @And("Count of books in search result is up to {int}")
    public void checkCountOfBooksInSearchResultIsUpTo(int countOfBooks) {
        Assert.assertTrue(countOfBooks >= searchPage.getFoundBooksCount(),
                "Count of books is bigger then " + countOfBooks);
    }
}
