package hooks.logout.components;

import aquality.appium.mobile.application.AqualityServices;
import constants.context.ContextLibrariesKeys;
import constants.localization.application.catalog.BookActionButtonKeys;
import framework.utilities.ScenarioContext;
import io.appium.java_client.appmanagement.ApplicationState;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;
import screens.alert.AlertScreen;
import screens.bookDetails.BookDetailsScreen;
import screens.books.BooksScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.form.MainCatalogToolbarForm;
import screens.catalog.screen.catalog.CatalogScreen;
import screens.holds.HoldsScreen;
import screens.notifications.NotificationModal;
import screens.settings.SettingsScreen;
import stepdefinitions.BaseSteps;
import stepdefinitions.application.components.AbstractApplicationSteps;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class AbstractLogoutHooks extends BaseSteps implements ILogoutHooks {

    public static final int COUNT_OF_RETRIES = 5;
    protected final AccountScreen accountScreen;
    protected final AccountsScreen accountsScreen;
    protected final BottomMenuForm bottomMenuForm;
    protected final SettingsScreen settingsScreen;
    protected final CatalogScreen catalogScreen;
    protected final HoldsScreen holdsScreen;
    protected final BooksScreen booksScreen;
    protected final BookDetailsScreen bookDetailsScreen;
    protected final MainCatalogToolbarForm mainCatalogToolbarForm;
    protected final NotificationModal notificationModal;
    protected final AbstractApplicationSteps applicationSteps;
    private final AlertScreen alertScreen;

    protected ScenarioContext context;

    public AbstractLogoutHooks(ScenarioContext context) {
        accountScreen = AqualityServices.getScreenFactory().getScreen(AccountScreen.class);
        accountsScreen = AqualityServices.getScreenFactory().getScreen(AccountsScreen.class);
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
        settingsScreen = AqualityServices.getScreenFactory().getScreen(SettingsScreen.class);
        catalogScreen = AqualityServices.getScreenFactory().getScreen(CatalogScreen.class);
        holdsScreen = AqualityServices.getScreenFactory().getScreen(HoldsScreen.class);
        mainCatalogToolbarForm = AqualityServices.getScreenFactory().getScreen(MainCatalogToolbarForm.class);
        booksScreen = AqualityServices.getScreenFactory().getScreen(BooksScreen.class);
        bookDetailsScreen = AqualityServices.getScreenFactory().getScreen(BookDetailsScreen.class);
        notificationModal = AqualityServices.getScreenFactory().getScreen(NotificationModal.class);
        applicationSteps = stepsFactory.getSteps(AbstractApplicationSteps.class);
        alertScreen = AqualityServices.getScreenFactory().getScreen(AlertScreen.class);

        this.context = context;
    }

    public abstract void closeApplication();

    @Override
    public void cancelHold() {
        alertScreen.closeModalIfPresent();
        List<String> librariesForCancel = context.get(ContextLibrariesKeys.CANCEL_HOLD.getKey());
        navigateBackIfBottomMenuIsNotVisibleUntilItIs();
        Optional.ofNullable(librariesForCancel).ifPresent(libraries ->
                libraries.forEach(library -> {
                    openPageForRequiredLibrary(library, BottomMenu.HOLDS);
                    if (holdsScreen.isBookForCancelPresent()) {
                        holdsScreen.cancelReservations();
                    }
                    notificationModal.handleBookActionsAndNotificationPopups(BookActionButtonKeys.RESERVE);
                }));
    }

    @Override
    public void cancelGet() {
        startAppIfCrashed();
        alertScreen.closeModalIfPresent();
        List<String> librariesForCancelGet = context.get(ContextLibrariesKeys.CANCEL_GET.getKey());
        navigateBackIfBottomMenuIsNotVisibleUntilItIs();
        Optional.ofNullable(librariesForCancelGet).ifPresent(libraries ->
                libraries.forEach(library -> {
                    openPageForRequiredLibrary(library, BottomMenu.BOOKS);

                    IntStream.range(0, booksScreen.getCountOfBooksWithAction(BookActionButtonKeys.READ))
                            .forEach(index -> {
                                booksScreen.openBookPage(index, BookActionButtonKeys.READ);
                                bookDetailsScreen.clickActionButton(BookActionButtonKeys.RETURN);
                                notificationModal.handleBookActionsAndNotificationPopups(BookActionButtonKeys.RETURN);
                                bookDetailsScreen.isBookAddButtonTextEqualTo(BookActionButtonKeys.GET);
                                bottomMenuForm.open(BottomMenu.BOOKS);
                            });
                }));
    }

    private void startAppIfCrashed() {
        if (AqualityServices.getApplication().getDriver().queryAppState(context.get(ContextLibrariesKeys.APP_BUNDLE_ID.getKey())) == ApplicationState.NOT_RUNNING) {
            AqualityServices.getLogger().info("App crashed - restarting");
            AqualityServices.getApplication().getDriver().launchApp();
        }
    }

    protected void navigateBackIfBottomMenuIsNotVisibleUntilItIs() {
        IntStream.range(0, COUNT_OF_RETRIES)
                .filter(i -> !bottomMenuForm.state().waitForDisplayed())
                .forEach(i -> applicationSteps.returnToPreviousPage());
    }

    private void openPageForRequiredLibrary(String library, BottomMenu holds) {
        bottomMenuForm.open(BottomMenu.CATALOG);
        bottomMenuForm.open(BottomMenu.CATALOG);
        mainCatalogToolbarForm.chooseAnotherLibrary();
        catalogScreen.openLibrary(library);
        bottomMenuForm.open(holds);
    }
}
