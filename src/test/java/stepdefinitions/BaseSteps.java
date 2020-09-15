package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.screens.screenfactory.IScreenFactory;
import factories.steps.IStepsFactory;
import factories.steps.StepsFactory;

public abstract class BaseSteps {
    protected IStepsFactory stepsFactory = StepsFactory.getStepsFactory();
    protected IScreenFactory screenFactory = AqualityServices.getScreenFactory();
}
