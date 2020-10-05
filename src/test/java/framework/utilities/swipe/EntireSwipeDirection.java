package framework.utilities.swipe;

import aquality.appium.mobile.elements.interfaces.IElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public enum EntireSwipeDirection {
    UP {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y))
                    .setTo(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y + dimensions.height - 1));
        }
    },
    DOWN {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y + dimensions.height - 1))
                    .setTo(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y));
        }
    },
    LEFT {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x, upperLeft.y + dimensions.height / 2))
                    .setTo(new Point(upperLeft.x + dimensions.width - 1, upperLeft.y + dimensions.height / 2));
        }
    },
    RIGHT {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + dimensions.width - 1, upperLeft.y + dimensions.height / 2))
                    .setTo(new Point(upperLeft.x, upperLeft.y + dimensions.height / 2));
        }
    };

    public abstract Direction getSwipeDirection(IElement element);
}
