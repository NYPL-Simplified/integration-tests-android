package framework.utilities.keyboard;

import aquality.appium.mobile.application.AqualityServices;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.interactions.Actions;

public final class KeyboardUtils {
    private KeyboardUtils() {}

    @SuppressWarnings("unchecked")
    public static boolean isKeyboardVisible() {
        return ((AndroidDriver<AndroidElement>)AqualityServices.getApplication().getDriver()).isKeyboardShown();
    }

    @SuppressWarnings("unchecked")
    public static void hideKeyboard() {
        ((AndroidDriver<AndroidElement>)AqualityServices.getApplication().getDriver()).hideKeyboard();
    }

    @SuppressWarnings("unchecked")
    public static void pressKey(AndroidKey androidKey) {
        ((AndroidDriver<AndroidElement>)AqualityServices.getApplication().getDriver()).pressKey(new KeyEvent(androidKey));
    }

}
