package stepdefinitions.catalog.components.impl;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import org.testng.Assert;
import stepdefinitions.catalog.components.AbstractCatalogSteps;

@StepsType(platform = PlatformName.ANDROID)
public class AndroidCatalogSteps extends AbstractCatalogSteps {
    public AndroidCatalogSteps(ScenarioContext context) {
        super(context);
    }

    @Override
    public void checkCurrentCategoryName(String expectedCategoryName) {
        Assert.assertTrue(AqualityServices.getConditionalWait().waitFor(() -> mainCatalogToolbarForm.getCategoryName().equals(expectedCategoryName), "Wait while category become correct."),
                String.format("Current category name is not correct. Expected '%1$s' but found '%2$s'", expectedCategoryName, mainCatalogToolbarForm.getCategoryName()));
    }
}
