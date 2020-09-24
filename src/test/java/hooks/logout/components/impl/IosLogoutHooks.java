package hooks.logout.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import hooks.logout.components.AbstractLogoutHooks;
import io.cucumber.java.After;
import screens.bottommenu.BottomMenu;

@StepsType(platform = PlatformName.IOS)
public class IosLogoutHooks extends AbstractLogoutHooks {

    public IosLogoutHooks(ScenarioContext context) {
        super(context);
    }

    @Override
    public void closeApplication() {
        if (bottomMenuForm.isBottomMenuBtnVisible(BottomMenu.SETTINGS)) {
            applicationSteps.returnToPreviousPage();
        }
        if (!accountScreen.state().isDisplayed()) {
            bottomMenuForm.open(BottomMenu.SETTINGS);
            settingsScreen.openAccounts();
        }
        accountScreen.logOut();
        if (!accountScreen.isLogoutSuccessful()) {
            accountScreen.logOut();
        }
    }
}
