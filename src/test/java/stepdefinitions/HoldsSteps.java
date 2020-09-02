package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.holds.HoldsScreen;

public class HoldsSteps {
    private final BottomMenuForm bottomMenuForm;
    private final HoldsScreen holdsScreen;
    private final ScenarioContext context;

    @Inject
    public HoldsSteps(ScenarioContext context) {
        this.context = context;
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        holdsScreen = AqualityServices.getScreenFactory().getScreen(HoldsScreen.class);
    }

    @And("I open Holds")
    public void openHolds() {
        bottomMenuForm.open(BottomMenu.HOLDS);
    }

    @Then("Holds feed is loaded")
    public void checkHoldsFeedIsLoaded() {
        Assert.assertTrue(holdsScreen.state().waitForDisplayed(), "Holds feed is not loaded");
    }

    @And("No books are present in Holds list")
    public void checkNoBooksArePresentInHoldsList() {
        Assert.assertTrue(holdsScreen.isNoBooksMessagePresent(), "Books are present in Holds list");
    }

    @And("Book {string} is present in Holds List")
    public void checkBookBookInfoIsPresentInHoldsList(String bookInfoKey) {
        String bookInfo = context.get(bookInfoKey);
        Assert.assertTrue(holdsScreen.isBookPresent(bookInfo), "Book '" + bookInfo + "' is not present in Books List");
    }
}
