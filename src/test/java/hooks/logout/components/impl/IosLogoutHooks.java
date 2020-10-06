package hooks.logout.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import hooks.logout.components.AbstractLogoutHooks;
import screens.bottommenu.BottomMenu;

@StepsType(platform = PlatformName.IOS)
public class IosLogoutHooks extends AbstractLogoutHooks {

    public IosLogoutHooks(ScenarioContext context) {
        super(context);
    }

    @Override
    public void closeApplication() {
        navigateBackIfBottomMenuIsNotVisibleUntilItIs();
        if (!accountScreen.state().isDisplayed()) {
            bottomMenuForm.open(BottomMenu.SETTINGS);
            settingsScreen.openAccounts();
            accountsScreen.openFirstLibrary();
        }
        accountScreen.logOut();
        if (!accountScreen.isLogoutSuccessful()) {
            accountScreen.logOut();
        }
    }
}
