package screens.audioplayer;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.text.ParseException;
import java.util.Date;

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

    public abstract Date getCurrentPlayTime() throws ParseException;

    public abstract String getLoadingStatus();

    public abstract void skipAhead();

    public abstract void skipBehind();

    public abstract void moveChapterToMiddle();

    public abstract Date getChapterLength() throws ParseException;

    public abstract void waitForBookLoading();

    public abstract void waitForLoadingDisappearing();
}
