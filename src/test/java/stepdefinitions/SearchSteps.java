package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.catalog.form.MainCatalogToolbarForm;
import screens.search.modal.SearchModal;
import screens.subcategory.SubcategoryScreen;

public class SearchSteps {
    private final MainCatalogToolbarForm mainCatalogToolbarForm;
    private final SearchModal searchModal;
    private final SubcategoryScreen subcategoryScreen;

    public SearchSteps() {
        mainCatalogToolbarForm = AqualityServices.getScreenFactory().getScreen(MainCatalogToolbarForm.class);
        searchModal = AqualityServices.getScreenFactory().getScreen(SearchModal.class);
        subcategoryScreen = AqualityServices.getScreenFactory().getScreen(SubcategoryScreen.class);
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

    @When("I search for {string}")
    public void searchFor(String searchedText) {
        Assert.assertTrue(searchModal.state().waitForDisplayed(), "Search modal is not present. Error (if present) - "+ subcategoryScreen.getErrorMessage());
        searchModal.setSearchedText(searchedText);
        searchModal.applySearch();
        Assert.assertTrue(searchModal.state().waitForNotDisplayed(), "Search modal is not disappear");
        Assert.assertTrue(subcategoryScreen.state().waitForDisplayed(), String.format("Search results page for value '%s' is not present. Error (if present) - %s", searchedText, subcategoryScreen.getErrorMessage()));
    }
}
