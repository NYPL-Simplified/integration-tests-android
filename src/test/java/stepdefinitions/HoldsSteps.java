package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.android.catalog.AndroidBookActionButtonKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.android.AndroidCatalogBookModel;
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

    @When("I open Holds")
    @And("Open Holds")
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
        AndroidCatalogBookModel bookInfo = context.get(bookInfoKey);
        Assert.assertTrue(holdsScreen.isBookPresent(bookInfo.getImageTitle()), "Book '" + bookInfo + "' is not present in Books List");
    }

    @When("I click on the book {string} button {} on the holds screen")
    public void clickOnTheBookAddButtonOnTheHoldsScreen(String bookInfoKey, AndroidBookActionButtonKeys key) {
        AndroidCatalogBookModel androidCatalogBookModel = context.get(bookInfoKey);
        holdsScreen.clickTheBookByTitleBtnWithKey(androidCatalogBookModel.getTitle(), key);
    }

    @Then("Book saved as {string} should contain {} button at the hold screen")
    public void checkThatSavedBookContainButtonAtTheHoldScreen(
            final String bookInfoKey, final AndroidBookActionButtonKeys key) {
        AndroidCatalogBookModel androidCatalogBookModel = context.get(bookInfoKey);
        Assert.assertTrue(holdsScreen.isBookAddButtonTextEqualTo(
                androidCatalogBookModel.getTitle(), key),
                String.format("Book with title '%1$s' add button does not contain text '%2$s'",
                        androidCatalogBookModel.getTitle(), key.getKey()));
    }
}
