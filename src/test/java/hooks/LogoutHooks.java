package hooks;

import aquality.appium.mobile.application.AqualityServices;
import com.google.inject.Inject;
import constants.android.catalog.AndroidBookActionButtonKeys;
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

import java.util.List;
import java.util.stream.IntStream;

public class LogoutHooks {

    private final AccountScreen accountScreen;
    private final AccountsScreen accountsScreen;
    private final BottomMenuForm bottomMenuForm;
    private final SettingsScreen settingsScreen;
    private final CatalogScreen catalogScreen;
    private final HoldsScreen holdsScreen;
    private final BooksScreen booksScreen;
    private final BookDetailsScreen bookDetailsScreen;
    private final MainCatalogToolbarForm mainCatalogToolbarForm;

    private ScenarioContext context;

    @Inject
    public LogoutHooks(ScenarioContext context) {
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

    @After(value = "@logout", order = 1)
    public void closeApplication() {
        if (!accountScreen.state().isDisplayed()) {
            bottomMenuForm.open(BottomMenu.SETTINGS);
            settingsScreen.openAccounts();
            accountsScreen.openFirstLibrary();
        }
        accountScreen.logOut();
    }

    @After(value = "@cancelGet", order = 2)
    public void cancelHold() {
        List<String> librariesForCancel = context.get("librariesForCancel");
        for (String library : librariesForCancel) {
            bottomMenuForm.open(BottomMenu.CATALOG);
            mainCatalogToolbarForm.chooseAnotherLibrary();
            catalogScreen.openLibrary(library);
            bottomMenuForm.open(BottomMenu.HOLDS);
            holdsScreen.cancelReservations();
        }
    }

    @After(value = "@cancelGet", order = 2)
    public void cancelGet() {
        List<String> librariesForCancelGet = context.get("librariesForCancelGet");
        librariesForCancelGet.forEach(library -> {
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
        });
    }
}
