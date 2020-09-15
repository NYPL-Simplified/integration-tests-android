package factories.steps;

import stepdefinitions.BaseSteps;

public interface IStepsFactory {

    <T extends BaseSteps> T getSteps(Class<T> clazz);
}
