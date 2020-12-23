package screens.audioplayer;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

public abstract class AudioPlayerScreen extends Screen {
    protected AudioPlayerScreen(By locator) {
        super(locator, "Audio player");
    }

    public abstract void checkThatChaptersVisible();

    public abstract void waitAndCheckAllLoadersDisappeared();

    public abstract void openMenu();

    public abstract void selectChapterNumber(int chapterNumber);

    public abstract String getCurrentChapterInfo();

    public abstract int getCountOfChapters();

    public abstract void playBook();

    public abstract void pauseBook();

    public abstract boolean isPauseButtonPresent();

    public abstract boolean isPlayButtonPresent();
}
