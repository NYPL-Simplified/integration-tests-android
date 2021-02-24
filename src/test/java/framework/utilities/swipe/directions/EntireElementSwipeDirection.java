package framework.utilities.swipe.directions;

import aquality.appium.mobile.elements.interfaces.IElement;
import framework.utilities.swipe.Direction;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public enum EntireElementSwipeDirection {
    UP {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y))
                    .setTo(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y + dimensions.height));
        }
    },
    DOWN {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y + dimensions.height))
                    .setTo(new Point(upperLeft.x + dimensions.width / 2, upperLeft.y));
        }
    },
    LEFT {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            int y = upperLeft.y + dimensions.height / 2;
            return new Direction()
                    .setFrom(new Point(upperLeft.x + dimensions.width / 4, y))
                    .setTo(new Point(upperLeft.x + dimensions.width - 1, y));
        }
    },
    RIGHT {
        @Override
        public Direction getSwipeDirection(IElement element) {
            Point upperLeft = element.getElement().getLocation();
            Dimension dimensions = element.getElement().getSize();
            return new Direction()
                    .setFrom(new Point(upperLeft.x + dimensions.width - dimensions.width / 4, upperLeft.y + dimensions.height / 4))
                    .setTo(new Point(upperLeft.x, upperLeft.y + dimensions.height / 4));
        }
    };

    public abstract Direction getSwipeDirection(IElement element);
}
