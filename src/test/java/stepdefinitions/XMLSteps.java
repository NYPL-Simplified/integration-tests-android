package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import org.testng.Assert;
import screens.search.modal.SearchModal;
import screens.subcategory.SubcategoryScreen;
import framework.utilities.feedXMLUtil.xml.XMLUtil;

public class XMLSteps {
    private final SearchModal searchModal;
    private final SubcategoryScreen subcategoryScreen;

    public XMLSteps() {
        searchModal = AqualityServices.getScreenFactory().getScreen(SearchModal.class);
        subcategoryScreen = AqualityServices.getScreenFactory().getScreen(SubcategoryScreen.class);
    }

    @And("I search {string} book of distributor {string} and bookType {string}")
    public void searchFor(String availabilityType, String distributor, String bookType) {
        String bookName = XMLUtil.getInstance().getRandomBook(availabilityType.toLowerCase(), bookType.toLowerCase(), distributor.toLowerCase());
        AqualityServices.getLogger().info("randomBookName: " + bookName);
        Assert.assertTrue(searchModal.state().waitForDisplayed(), "Search modal is not present. Error (if present) - " + subcategoryScreen.getErrorMessage());
        searchModal.setSearchedText(bookName);
        searchModal.applySearch();
        Assert.assertTrue(searchModal.state().waitForNotDisplayed(), "Search modal is not disappear");
        Assert.assertTrue(subcategoryScreen.state().waitForDisplayed(), String.format("Search results page for value '%s' is not present. Error (if present) - %s", bookName, subcategoryScreen.getErrorMessage()));
    }
}
