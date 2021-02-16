package framework.utilities;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public final class CoordinatesClickUtils {

    private CoordinatesClickUtils() {
    }

    public static void clickAtCenterOfScreen() {
        Dimension dimension = AqualityServices.getApplication().getDriver().manage().window().getSize();
        TouchAction<?> action = new TouchAction<>(AqualityServices.getApplication().getDriver());
        action.tap(PointOption.point(dimension.width / 2, dimension.height / 2)).perform();
    }

    public static void clickOutOfElement(IElement iElement) {
        int yOffset = 100;
        clickOutOfElement(iElement, yOffset);
    }

    public static void clickOutOfElement(IElement element, int yOffset) {
        Point fontChoicesScreenUpperLeftPoint = element.getElement().getLocation();
        Dimension fontChoicesScreenSize = element.getElement().getSize();

        TouchAction<?> touchAction = new TouchAction<>(AqualityServices.getApplication().getDriver());
        int offset = fontChoicesScreenUpperLeftPoint.y + fontChoicesScreenSize.height + yOffset;
        touchAction.tap(PointOption.point(fontChoicesScreenUpperLeftPoint.x + fontChoicesScreenSize.width / 2, offset)).perform();
    }
}
