package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import screens.agegate.AgeGateScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.CatalogScreen;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogSteps {
    private final BottomMenuForm bottomMenuForm;
    private final CatalogScreen catalogScreen;
    private final AgeGateScreen ageGateScreen;
    private ScenarioContext context;

    @Inject
    public CatalogSteps(ScenarioContext context) {
        this.context = context;
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        catalogScreen = AqualityServices.getScreenFactory().getScreen(CatalogScreen.class);
        ageGateScreen = AqualityServices.getScreenFactory().getScreen(AgeGateScreen.class);
    }

    @When("I open Catalog")
    public void iOpenCatalog() {
        bottomMenuForm.open(BottomMenu.CATALOG);
    }

    @And("I approve that User is 13 or older")
    public void iApproveThatUserIsOrOlder() {
        ageGateScreen.approveAge();
    }

    @Then("Books feed is loaded")
    public void booksFeedIsLoaded() {
        Assert.assertTrue(catalogScreen.state().waitForDisplayed(), "Books feed is not loaded");
    }

    @When("I get names of books in first lane and save them as {string}")
    public void iGetNamesOfBooksInFirstLaneAndSaveThemAsNameOfBooks(String booksNamesListKey) {
        context.add(booksNamesListKey, catalogScreen.getListOfBooksNames());
    }

    @Then("List of books is not equal to list of books saved as {string}")
    public void listOfBooksIsNotEqualToListOfBooksSavedAsNameOfBooks(String booksNamesListKey) {
        List<String> expectedList = context.get(booksNamesListKey);
        Assert.assertNotEquals(catalogScreen.getListOfBooksNames(), expectedList,
                "Lists of books are equal" + expectedList.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }

    @And("I open {string} from side menu")
    public void iOpenHartfordPublicLibraryFromSideMenu(String libraryName) {
        catalogScreen.openLibrary(libraryName);
    }
}
