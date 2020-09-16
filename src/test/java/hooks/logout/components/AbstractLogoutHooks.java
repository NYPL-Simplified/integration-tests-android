package hooks.logout.components;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.application.catalog.AndroidBookActionButtonKeys;
import framework.utilities.ScenarioContext;
import io.cucumber.java.After;
import screens.account.AccountScreen;
import screens.accounts.AccountsScreen;
import screens.bookDetails.BookDetailsScreen;
import screens.books.BooksScreen;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;
import screens.catalog.form.MainCatalogToolbarForm;
import screens.catalog.screen.catalog.CatalogScreen;
import screens.holds.HoldsScreen;
import screens.settings.SettingsScreen;
import stepdefinitions.BaseSteps;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class AbstractLogoutHooks extends BaseSteps {

    protected final AccountScreen accountScreen;
    protected final AccountsScreen accountsScreen;
    protected final BottomMenuForm bottomMenuForm;
    protected final SettingsScreen settingsScreen;
    protected final CatalogScreen catalogScreen;
    protected final HoldsScreen holdsScreen;
    protected final BooksScreen booksScreen;
    protected final BookDetailsScreen bookDetailsScreen;
    protected final MainCatalogToolbarForm mainCatalogToolbarForm;

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

        this.context = context;
    }

    public abstract void closeApplication();

    public void cancelHold() {
        List<String> librariesForCancel = context.get("librariesForCancel");
        Optional.ofNullable(librariesForCancel).ifPresent(libraries ->
                libraries.forEach(library -> {
                    bottomMenuForm.open(BottomMenu.CATALOG);
                    mainCatalogToolbarForm.chooseAnotherLibrary();
                    catalogScreen.openLibrary(library);
                    bottomMenuForm.open(BottomMenu.HOLDS);
                    holdsScreen.cancelReservations();
                }));
    }

    public void cancelGet() {
        List<String> librariesForCancelGet = context.get("librariesForCancelGet");

        Optional.ofNullable(librariesForCancelGet).ifPresent(libraries ->
                libraries.forEach(library -> {
                    bottomMenuForm.open(BottomMenu.CATALOG);
                    mainCatalogToolbarForm.chooseAnotherLibrary();
                    catalogScreen.openLibrary(library);
                    bottomMenuForm.open(BottomMenu.BOOKS);

                    IntStream.range(0, booksScreen.getCountOfBooksWithAction(AndroidBookActionButtonKeys.READ))
                            .forEach(index -> {
                                booksScreen.openBookPage(index, AndroidBookActionButtonKeys.READ);
                                bookDetailsScreen.clickActionButton(AndroidBookActionButtonKeys.RETURN);
                                bookDetailsScreen.isBookAddButtonTextEqualTo(AndroidBookActionButtonKeys.GET);
                                bottomMenuForm.open(BottomMenu.BOOKS);
                            });
                }));
    }
}
