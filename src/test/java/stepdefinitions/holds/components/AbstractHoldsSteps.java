package stepdefinitions.holds.components;

import aquality.appium.mobile.application.AqualityServices;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.ScenarioContext;
import models.android.CatalogBookModel;
import org.testng.Assert;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.holds.HoldsScreen;
import stepdefinitions.BaseSteps;

public abstract class AbstractHoldsSteps extends BaseSteps {
    protected final BottomMenuForm bottomMenuForm;
    protected final HoldsScreen holdsScreen;
    protected final ScenarioContext context;

    public AbstractHoldsSteps(ScenarioContext context) {
        this.context = context;
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        holdsScreen = AqualityServices.getScreenFactory().getScreen(HoldsScreen.class);
    }

    public void openHolds() {
        bottomMenuForm.open(BottomMenu.HOLDS);
    }

    public void checkHoldsFeedIsLoaded() {
        Assert.assertTrue(holdsScreen.state().waitForDisplayed(), "Holds feed is not loaded");
    }

    public void checkNoBooksArePresentInHoldsList() {
        Assert.assertTrue(holdsScreen.isNoBooksMessagePresent(), "Books are present in Holds list");
    }

    public abstract void checkBookBookInfoIsPresentInHoldsList(String bookInfoKey);

    public void clickOnTheBookAddButtonOnTheHoldsScreen(String bookInfoKey, BookActionButtonKeys key) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        holdsScreen.clickTheBookByTitleBtnWithKey(catalogBookModel.getTitle(), key);
    }

    public void checkThatSavedBookContainButtonAtTheHoldScreen(
            final String bookInfoKey, final BookActionButtonKeys key) {
        CatalogBookModel catalogBookModel = context.get(bookInfoKey);
        Assert.assertTrue(holdsScreen.isBookAddButtonTextEqualTo(
                catalogBookModel.getTitle(), key),
                String.format("Book with title '%1$s' add button does not contain text '%2$s'",
                        catalogBookModel.getTitle(), key.i18n()));
    }
}
