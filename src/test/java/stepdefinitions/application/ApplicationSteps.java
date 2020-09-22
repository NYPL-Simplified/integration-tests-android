package stepdefinitions.application;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import stepdefinitions.BaseSteps;
import stepdefinitions.application.components.AbstractApplicationSteps;
import stepdefinitions.application.components.IApplicationSteps;

public class ApplicationSteps extends BaseSteps implements IApplicationSteps {
    private AbstractApplicationSteps applicationSteps;

    public ApplicationSteps() {
        this.applicationSteps = stepsFactory.getSteps(AbstractApplicationSteps.class);
    }

    @When("I open application")
    @Given("Application is opened")
    public void openApplication() {
        applicationSteps.openApplication();
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
