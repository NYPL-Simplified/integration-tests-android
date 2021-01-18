package stepdefinitions.holds.components;

import constants.localization.application.catalog.BookActionButtonKeys;

public interface IHoldsSteps {

    void openHolds();

    void checkHoldsFeedIsLoaded();

    void checkNoBooksArePresentInHoldsList();

    void checkBookBookInfoIsPresentInHoldsList(String bookInfoKey);

    void clickOnBookAddButtonOnHoldsScreen(String bookInfoKey, BookActionButtonKeys key);

    void checkThatSavedBookContainButtonAtHoldScreen(final String bookInfoKey, final BookActionButtonKeys key);
}
