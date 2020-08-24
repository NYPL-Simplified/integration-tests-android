package stepdefinitions;

import aquality.appium.mobile.application.AqualityServices;
import io.cucumber.java.en.And;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;

public class MenuSteps {
    private final BottomMenuForm bottomMenuForm;

    public MenuSteps() {
        bottomMenuForm = AqualityServices.getScreenFactory().getScreen(BottomMenuForm.class);
    }

    @And("I open Settings from Menu pane")
    public void openSettings() {
        bottomMenuForm.open(BottomMenu.SETTINGS);
    }
}
