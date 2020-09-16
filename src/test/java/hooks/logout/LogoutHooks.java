package hooks.logout;

import com.google.inject.Inject;
import constants.application.catalog.AndroidBookActionButtonKeys;
import framework.utilities.ScenarioContext;
import hooks.logout.components.AbstractLogoutHooks;
import io.cucumber.java.After;
import screens.bottommenu.BottomMenu;
import stepdefinitions.holds.components.AbstractHoldsSteps;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class LogoutHooks extends AbstractLogoutHooks {

    private final AbstractLogoutHooks abstractLogoutHooks;

    @Inject
    public LogoutHooks(ScenarioContext context) {
        super(context);
        abstractLogoutHooks = stepsFactory.getSteps(AbstractLogoutHooks.class, context);
    }

    @After(value = "@logout", order = 1)
    public void closeApplication() {
        abstractLogoutHooks.closeApplication();
    }

    @After(value = "@cancelHold", order = 2)
    public void cancelHold() {
        abstractLogoutHooks.cancelHold();
    }

    @After(value = "@cancelGet", order = 2)
    public void cancelGet() {
        abstractLogoutHooks.cancelGet();
    }
}
