package hooks.logout.components.impl;

import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import hooks.logout.components.AbstractLogoutHooks;
import screens.bottommenu.BottomMenu;

@StepsType(platform = PlatformName.ANDROID)
public class AndroidLogoutHooks extends AbstractLogoutHooks {


    public AndroidLogoutHooks(ScenarioContext context) {
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
        if (accountScreen.isLogoutRequired()) {
            accountScreen.logOut();
        }
        if (!accountScreen.isLogoutSuccessful()) {
            accountScreen.logOut();
        }
    }
}
