package framework.utilities.keyboard;

import aquality.appium.mobile.application.AqualityServices;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.interactions.Actions;

public final class KeyboardUtils {
    private KeyboardUtils() {
    }

    public static boolean isKeyboardVisible() {
        return ((HasOnScreenKeyboard) AqualityServices.getApplication().getDriver()).isKeyboardShown();
    }

    public static void hideKeyboard() {
        AqualityServices.getApplication().getDriver().hideKeyboard();
    }

    public static void pressKey(AndroidKey androidKey) {
        ((PressesKey) AqualityServices.getApplication().getDriver()).pressKey(new KeyEvent(androidKey));
    }

}
