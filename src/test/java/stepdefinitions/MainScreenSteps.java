package stepdefinitions;

import aquality.appium.mobile.screens.screenfactory.IScreenFactory;
import io.cucumber.java.en.And;
import screens.main.MainScreen;

public class MainScreenSteps {
    private final MainScreen mainScreen;

    public MainScreenSteps(IScreenFactory screenFactory) {
        mainScreen = screenFactory.getScreen(MainScreen.class);
    }

    @And("I select Add a Library Later button on Main screen")
    public void selectAddALibraryLaterButton() {
        mainScreen.addALibraryLater();
    }
}
