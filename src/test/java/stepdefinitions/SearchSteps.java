package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.search.form.SearchForm;
import screens.search.modal.SearchModal;
import screens.search.page.SearchPage;

public class SearchSteps {
    private final SearchPage searchPage;
    private final SearchForm searchForm;
    private final SearchModal searchModal;

    public SearchSteps() {
        searchPage = AqualityServices.getScreenFactory().getScreen(SearchPage.class);
        searchForm = AqualityServices.getScreenFactory().getScreen(SearchForm.class);
        searchModal = AqualityServices.getScreenFactory().getScreen(SearchModal.class);
    }

    @When("I open search modal")
    public void openSearchModal() {
        searchForm.openSearchModal();
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
}
