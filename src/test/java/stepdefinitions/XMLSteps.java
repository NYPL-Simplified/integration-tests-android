package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import org.testng.Assert;
import screens.search.modal.SearchModal;
import screens.subcategory.SubcategoryScreen;
import framework.utilities.feedXMLUtil.xml.XMLUtil;

import javax.inject.Inject;

public class XMLSteps {
    private final SearchModal searchModal;
    private final SubcategoryScreen subcategoryScreen;
    private ScenarioContext context;

    @Inject
    public XMLSteps(ScenarioContext context) {
        this.context = context;
        searchModal = AqualityServices.getScreenFactory().getScreen(SearchModal.class);
        subcategoryScreen = AqualityServices.getScreenFactory().getScreen(SubcategoryScreen.class);
    }

    @And("I search {string} book of distributor {string} and bookType {string} and save as {string}")
    public void searchFor(String availabilityType, String distributor, String bookType, String bookNameKey) {
        String bookName = XMLUtil.getInstance().getRandomBook(availabilityType.toLowerCase(), bookType.toLowerCase(), distributor.toLowerCase());
        context.add(bookNameKey, bookName);
        AqualityServices.getLogger().info("randomBookName: " + bookName);
        Assert.assertTrue(searchModal.state().waitForDisplayed(), "Search modal is not present. Error (if present) - " + subcategoryScreen.getErrorMessage());
        searchModal.setSearchedText(bookName);
        searchModal.applySearch();
        Assert.assertTrue(searchModal.state().waitForNotDisplayed(), "Search modal is not disappear");
        Assert.assertTrue(subcategoryScreen.state().waitForDisplayed(), String.format("Search results page for value '%s' is not present. Error (if present) - %s", bookName, subcategoryScreen.getErrorMessage()));
    }
}
