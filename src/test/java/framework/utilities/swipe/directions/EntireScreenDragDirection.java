package framework.utilities.swipe.directions;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.elements.interfaces.IElement;
import framework.utilities.swipe.Direction;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public enum EntireScreenDragDirection {
    UP {
        @Override
        public Direction getDragDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension elementDimension = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + elementDimension.width / 2, upperLeft.y + elementDimension.height / 2))
                    .setTo(new Point(upperLeft.x + elementDimension.width / 2, elementDimension.height / 2));
        }
    },
    DOWN {
        @Override
        public Direction getDragDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension elementDimension = element.getElement().getSize();
            Dimension screenDimension = getWindowSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + elementDimension.width / 2, upperLeft.y + elementDimension.height / 2))
                    .setTo(new Point(upperLeft.x + elementDimension.width / 2, screenDimension.height - elementDimension.height / 2));
        }
    },
    LEFT {
        @Override
        public Direction getDragDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension elementDimension = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + elementDimension.width / 2, upperLeft.y + elementDimension.height / 2))
                    .setTo(new Point(elementDimension.width / 2, upperLeft.y + elementDimension.height / 2));
        }
    },
    RIGHT {
        @Override
        public Direction getDragDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension elementDimension = element.getElement().getSize();
            Dimension screenDimension = getWindowSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + elementDimension.width / 2, upperLeft.y + elementDimension.height / 2))
                    .setTo(new Point(screenDimension.width - elementDimension.width / 2, upperLeft.y + elementDimension.height / 2));
        }
    };

    private static Dimension getWindowSize() {
        return AqualityServices.getApplication().getDriver().manage().window().getSize();
    }

    public abstract Direction getDragDirection(IElement element);
}
