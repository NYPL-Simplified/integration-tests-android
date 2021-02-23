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

import java.util.List;

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
        List<String> listOfLibraries = context.get(ContextLibrariesKeys.LOG_OUT.getKey());
        for (String library : listOfLibraries) {
            if (!accountScreen.state().isDisplayed()) {
                bottomMenuForm.open(BottomMenu.SETTINGS);
                alertScreen.closeNotNowModalIfPresent();
                bottomMenuForm.open(BottomMenu.SETTINGS);
                settingsScreen.openAccounts();
                accountsScreen.openAccount(library);
            }
            if (accountScreen.isLogoutRequired()) {
                logOut();
            }
            if (!accountScreen.isLogoutSuccessful()) {
                logOut();
            }
        }
    }

    private void logOut() {
        notificationModal.closeSyncNotificationIfDisplayed();
        alertScreen.closeNotNowModalIfPresent();
        accountScreen.logOut();
    }
}
