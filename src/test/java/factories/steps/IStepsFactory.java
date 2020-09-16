package factories.steps;

import stepdefinitions.BaseSteps;

public interface IStepsFactory {

    @SuppressWarnings("unchecked")
    <T extends BaseSteps> T getSteps(Class<T> clazz, Object... args);
}
