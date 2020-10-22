package hooks.logout.components.impl;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import constants.context.ContextLibrariesKeys;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import hooks.logout.components.AbstractLogoutHooks;
import screens.alert.AlertScreen;
import screens.bottommenu.BottomMenu;
import screens.notifications.NotificationModal;

@StepsType(platform = PlatformName.IOS)
public class IosLogoutHooks extends AbstractLogoutHooks {
    private final NotificationModal notificationModal;
    private final AlertScreen alertScreen;

    public IosLogoutHooks(ScenarioContext context) {
        super(context);
        notificationModal = AqualityServices.getScreenFactory().getScreen(NotificationModal.class);
        alertScreen = AqualityServices.getScreenFactory().getScreen(AlertScreen.class);
    }

    @Override
    public void closeApplication() {
        alertScreen.closeModalIfPresent();
        navigateBackIfBottomMenuIsNotVisibleUntilItIs();
        if (!accountScreen.state().isDisplayed()) {
            bottomMenuForm.open(BottomMenu.SETTINGS);
            bottomMenuForm.open(BottomMenu.SETTINGS);
            settingsScreen.openAccounts();
            accountsScreen.openAccount(context.get(ContextLibrariesKeys.LOG_OUT.getKey()));
        }
        if (accountScreen.isLogoutRequired()) {
            notificationModal.closeSyncNotificationIfDisplayed();
            accountScreen.logOut();
        }
        if (!accountScreen.isLogoutSuccessful()) {
            notificationModal.closeSyncNotificationIfDisplayed();
            accountScreen.logOut();
        }
    }
}
