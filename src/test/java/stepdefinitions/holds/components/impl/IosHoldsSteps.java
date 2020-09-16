package stepdefinitions.holds.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import models.android.AndroidCatalogBookModel;
import org.testng.Assert;
import stepdefinitions.holds.components.AbstractHoldsSteps;

@StepsType(platform = PlatformName.IOS)
public class IosHoldsSteps extends AbstractHoldsSteps {

    public IosHoldsSteps(ScenarioContext context) {
        super(context);
    }


    @Override
    public void checkBookBookInfoIsPresentInHoldsList(String bookInfoKey) {
        AndroidCatalogBookModel bookInfo = context.get(bookInfoKey);
        Assert.assertTrue(holdsScreen.isBookPresent(bookInfo.getTitle()), "Book '" + bookInfo + "' is not present in Books List");
    }
}
