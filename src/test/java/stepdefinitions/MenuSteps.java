package stepdefinitions;

import aquality.appium.mobile.screens.screenfactory.IScreenFactory;
import io.cucumber.java.en.And;
import screens.bottommenu.BottomMenu;
import screens.bottommenu.BottomMenuForm;

public class MenuSteps {
    private final BottomMenuForm bottomMenuForm;

    public MenuSteps(IScreenFactory screenFactory) {
        bottomMenuForm = screenFactory.getScreen(BottomMenuForm.class);
    }

    @And("I open Settings from Menu pane")
    public void openSettings() {
        bottomMenuForm.open(BottomMenu.SETTINGS);
    }
}
