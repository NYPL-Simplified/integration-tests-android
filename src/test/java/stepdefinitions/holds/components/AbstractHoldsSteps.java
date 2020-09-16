package stepdefinitions.holds.components;

import aquality.appium.mobile.application.AqualityServices;
import constants.application.catalog.AndroidBookActionButtonKeys;
import framework.utilities.ScenarioContext;
import models.android.AndroidCatalogBookModel;
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

    public void clickOnTheBookAddButtonOnTheHoldsScreen(String bookInfoKey, AndroidBookActionButtonKeys key) {
        AndroidCatalogBookModel androidCatalogBookModel = context.get(bookInfoKey);
        holdsScreen.clickTheBookByTitleBtnWithKey(androidCatalogBookModel.getTitle(), key);
    }

    public void checkThatSavedBookContainButtonAtTheHoldScreen(
            final String bookInfoKey, final AndroidBookActionButtonKeys key) {
        AndroidCatalogBookModel androidCatalogBookModel = context.get(bookInfoKey);
        Assert.assertTrue(holdsScreen.isBookAddButtonTextEqualTo(
                androidCatalogBookModel.getTitle(), key),
                String.format("Book with title '%1$s' add button does not contain text '%2$s'",
                        androidCatalogBookModel.getTitle(), key.getKey()));
    }
}
