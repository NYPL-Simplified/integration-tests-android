package stepdefinitions.holds;

import com.google.inject.Inject;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import stepdefinitions.holds.components.AbstractHoldsSteps;

public class HoldsSteps extends AbstractHoldsSteps {
    private AbstractHoldsSteps abstractHoldsSteps;

    @Inject
    public HoldsSteps(ScenarioContext context) {
        super(context);
        this.abstractHoldsSteps = stepsFactory.getSteps(AbstractHoldsSteps.class, context);
    }

    @When("I open Holds")
    @And("Open Holds")
    public void openHolds() {
        abstractHoldsSteps.openHolds();
    }

    @Then("Holds feed is loaded")
    public void checkHoldsFeedIsLoaded() {
        abstractHoldsSteps.checkHoldsFeedIsLoaded();
    }

    @And("No books are present in Holds list")
    public void checkNoBooksArePresentInHoldsList() {
        abstractHoldsSteps.checkNoBooksArePresentInHoldsList();
    }

    @And("Book {string} is present in Holds List")
    public void checkBookBookInfoIsPresentInHoldsList(String bookInfoKey) {
        abstractHoldsSteps.checkBookBookInfoIsPresentInHoldsList(bookInfoKey);
    }

    @When("I click on the book {string} button {} on the holds screen")
    public void clickOnTheBookAddButtonOnTheHoldsScreen(String bookInfoKey, BookActionButtonKeys key) {
        abstractHoldsSteps.clickOnTheBookAddButtonOnTheHoldsScreen(bookInfoKey, key);
    }

    @Then("Book saved as {string} should contain {} button at the hold screen")
    public void checkThatSavedBookContainButtonAtTheHoldScreen(
            final String bookInfoKey, final BookActionButtonKeys key) {
        abstractHoldsSteps.checkThatSavedBookContainButtonAtTheHoldScreen(bookInfoKey, key);
    }
}
