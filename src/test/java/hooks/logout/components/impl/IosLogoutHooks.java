package hooks.logout.components.impl;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import factories.steps.StepsType;
import framework.utilities.ScenarioContext;
import hooks.logout.components.AbstractLogoutHooks;
import screens.bottommenu.BottomMenu;
import screens.notifications.NotificationModal;

@StepsType(platform = PlatformName.IOS)
public class IosLogoutHooks extends AbstractLogoutHooks {
    private final NotificationModal notificationModal;

    public IosLogoutHooks(ScenarioContext context) {
        super(context);
        notificationModal = AqualityServices.getScreenFactory().getScreen(NotificationModal.class);
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
            notificationModal.closeSyncNotificationIfDisplayed();
            accountScreen.logOut();
        }
        if (!accountScreen.isLogoutSuccessful()) {
            notificationModal.closeSyncNotificationIfDisplayed();
            accountScreen.logOut();
        }
    }
}
