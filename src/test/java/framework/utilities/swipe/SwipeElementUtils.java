package framework.utilities.swipe;

import aquality.appium.mobile.actions.ITouchActions;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public final class SwipeElementUtils {
    private SwipeElementUtils() {

    }

    public static void swipeElementLeft(IElement element) {
        Point upperLeft = element.getElement().getLocation();
        Point center = element.getElement().getCenter();
        Dimension dimensions = element.getElement().getSize();

        ITouchActions touchActions = AqualityServices.getTouchActions();
        touchActions.swipe(center, new Point(upperLeft.x, upperLeft.y + dimensions.height / 2));
    }
}
