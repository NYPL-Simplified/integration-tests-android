package stepdefinitions.holds.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import models.android.CatalogBookModel;
import org.testng.Assert;
import stepdefinitions.holds.components.AbstractHoldsSteps;

@StepsType(platform = PlatformName.ANDROID)
public class AndroidHoldsSteps extends AbstractHoldsSteps {
    public AndroidHoldsSteps(ScenarioContext context) {
        super(context);
    }

    @Override
    public void checkBookBookInfoIsPresentInHoldsList(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        Assert.assertTrue(holdsScreen.isBookPresent(bookInfo.getImageTitle()),
                String.format("Book '%s' is not present in Books List", bookInfo));
    }

    @Override
    public void checkBookBookInfoIsNotPresentInHoldsList(String bookInfoKey) {
        CatalogBookModel bookInfo = context.get(bookInfoKey);
        Assert.assertFalse(holdsScreen.isBookPresent(bookInfo.getImageTitle()),
                String.format("Book '%s' is not present in Books List", bookInfo));
    }
}
