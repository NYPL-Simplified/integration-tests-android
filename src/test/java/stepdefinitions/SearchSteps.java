package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.catalog.form.MainCatalogToolbarForm;
import screens.search.modal.SearchModal;

public class SearchSteps {
    private final MainCatalogToolbarForm mainCatalogToolbarForm;
    private final SearchModal searchModal;

    public SearchSteps() {
        mainCatalogToolbarForm = AqualityServices.getScreenFactory().getScreen(MainCatalogToolbarForm.class);
        searchModal = AqualityServices.getScreenFactory().getScreen(SearchModal.class);
    }

    @When("I open search modal")
    public void openSearchModal() {
        mainCatalogToolbarForm.openSearchModal();
        searchModal.state().waitForDisplayed();
    }

    @Then("Search modal is opened")
    public void checkSearchModalIsOpened() {
        Assert.assertTrue(searchModal.state().waitForDisplayed(), "Search modal is not loaded");
    }

    @When("I set text to the search textBox {string}")
    public void setTextToTheSearch(String searchedText) {
        searchModal.state().waitForDisplayed();
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
}
