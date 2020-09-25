package screens.audioplayer.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.audioplayer.AudioPlayerScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosAudioPlayerScreen extends AudioPlayerScreen {

    public IosAudioPlayerScreen() {
        super(By.xpath(""));
    }

    @Override
    public void checkThatChaptersVisible() {

    }

    @Override
    public void waitAndCheckAllLoadersDisappeared() {

    }

    @Override
    public void openMenu() {

    }

    @Override
    public void selectChapterNumber(int chapterNumber) {

    }

    @Override
    public String getCurrentChapterInfo() {
        return null;
    }

    @Override
    public int getCountOfChapters() {
        return 0;
    }
}
