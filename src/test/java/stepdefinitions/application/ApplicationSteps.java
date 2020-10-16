package stepdefinitions.application;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import com.google.inject.Inject;
import constants.context.ContextLibrariesKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import stepdefinitions.BaseSteps;
import stepdefinitions.application.components.AbstractApplicationSteps;
import stepdefinitions.application.components.IApplicationSteps;

public class ApplicationSteps extends BaseSteps implements IApplicationSteps {
    public static final String IOS_APP_BUNDLE_ID = "org.nypl.labs.SimplyE";
    public static final String GET_CURRENT_PACKAGE_COMMAND = "getCurrentPackage";
    private AbstractApplicationSteps applicationSteps;
    private ScenarioContext context;

    @Inject
    public ApplicationSteps(ScenarioContext context) {
        this.context = context;
        this.applicationSteps = stepsFactory.getSteps(AbstractApplicationSteps.class);
    }

    @When("I open application")
    @Given("Application is opened")
    public void openApplication() {
        applicationSteps.openApplication();
        context.add(ContextLibrariesKeys.APP_BUNDLE_ID.getKey(), getBundleId());
    }

    private String getBundleId() {
        if (AqualityServices.getApplication().getPlatformName() == PlatformName.ANDROID) {
            return (String) AqualityServices.getApplication().getDriver().execute(GET_CURRENT_PACKAGE_COMMAND).getValue();
        }
        return IOS_APP_BUNDLE_ID;
    }

    @And("I return to previous screen")
    public void returnToPreviousPage() {
        applicationSteps.returnToPreviousPage();
    }

    @When("I restart app")
    public void restartApp() {
        applicationSteps.restartApp();
    }
}
