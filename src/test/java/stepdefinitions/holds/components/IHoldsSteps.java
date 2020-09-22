package stepdefinitions.holds.components;

import constants.localization.application.catalog.BookActionButtonKeys;

public interface IHoldsSteps {

    void openHolds();

    void checkHoldsFeedIsLoaded();

    void checkNoBooksArePresentInHoldsList();

    void checkBookBookInfoIsPresentInHoldsList(String bookInfoKey);

    void clickOnTheBookAddButtonOnTheHoldsScreen(String bookInfoKey, BookActionButtonKeys key);

    void checkThatSavedBookContainButtonAtTheHoldScreen(final String bookInfoKey, final BookActionButtonKeys key);
}
