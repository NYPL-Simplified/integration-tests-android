package hooks.logout;

import com.google.inject.Inject;
import framework.utilities.ScenarioContext;
import hooks.logout.components.AbstractLogoutHooks;
import hooks.logout.components.ILogoutHooks;
import io.cucumber.java.After;
import stepdefinitions.BaseSteps;
import stepdefinitions.application.components.AbstractApplicationSteps;

public class LogoutHooks extends BaseSteps implements ILogoutHooks {

    private final AbstractLogoutHooks abstractLogoutHooks;

    @Inject
    public LogoutHooks(ScenarioContext context) {
        abstractLogoutHooks = stepsFactory.getSteps(AbstractLogoutHooks.class, context);
    }

    @Override
    @After(value = "@logout", order = 1)
    public void closeApplication() {
        abstractLogoutHooks.closeApplication();
    }

    @Override
    @After(value = "@cancelHold", order = 2)
    public void cancelHold() {
        abstractLogoutHooks.cancelHold();
    }

    @Override
    @After(value = "@cancelGet", order = 2)
    public void cancelGet() {
        abstractLogoutHooks.cancelGet();
    }
}
