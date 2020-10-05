package framework.utilities.swipe;

import lombok.Data;
import lombok.experimental.Accessors;
import org.openqa.selenium.Point;

@Data
@Accessors(chain = true)
public class Direction {
    private Point from;
    private Point to;

}
