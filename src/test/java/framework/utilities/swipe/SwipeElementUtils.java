package framework.utilities.swipe;

import aquality.appium.mobile.actions.ITouchActions;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;

public final class SwipeElementUtils {
    private SwipeElementUtils() {

    }

    public static void swipeElementLeft(IElement element) {
        Point upperLeft = element.getElement().getLocation();
        Dimension dimensions = element.getElement().getSize();
        element.getTouchActions().swipe(new Point(upperLeft.x, upperLeft.y + dimensions.height / 2));
    }

    public static void swipeElementDown(IElement element) {
        Point upperLeft = element.getElement().getLocation();
        Dimension dimensions = element.getElement().getSize();
        element.getTouchActions().swipe(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y + dimensions.height));
    }

    public static void swipeElementUp(IElement element) {
        Point upperLeft = element.getElement().getLocation();
        Point center = element.getElement().getCenter();
        element.getTouchActions().swipe(new Point(center.x, upperLeft.y));
    }

    public static void swipeThroughEntireElementUp(IElement element) {
        Point upperLeft = element.getElement().getLocation();
        Point center = element.getElement().getCenter();
        Dimension dimensions = element.getElement().getSize();
        ITouchActions touchActions = AqualityServices.getTouchActions();
        touchActions.swipe(new Point(center.x, upperLeft.y + dimensions.height - 1), new Point(center.x, upperLeft.y - 1));
    }

    public static void swipeFromLeftToRight(IElement element) {
        Rectangle rectangle = element.getElement().getRect();
        element.getTouchActions().swipe(new Point(rectangle.x + rectangle.width - 1, element.getElement().getCenter().y));
    }

    public static void swipeFromRightToLeft(IElement element) {
        element.getTouchActions().swipe(new Point(0, element.getElement().getCenter().y));
    }
}
