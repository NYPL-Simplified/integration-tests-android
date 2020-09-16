package screens.reader.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import screens.reader.ReaderScreen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ScreenType(platform = PlatformName.IOS)
public class IosReaderScreen extends ReaderScreen {
    private final ILabel lblBookName =
            getElementFactory().getLabel(By.xpath(""), "Book Cover");
    private final ILabel lblPageNumber =
            getElementFactory().getLabel(By.xpath(""), "Page Number");
    private final ILabel lblBookTextPage =
            getElementFactory().getLabel(By.xpath(""), "Page Number");
    private final ILabel lblPage =
            getElementFactory().getLabel(By.xpath(""), "Page Number");

    public IosReaderScreen() {
        super(By.xpath(""));
    }

    @Override
    public String getBookName() {
        String text = lblBookName.getText();
        AqualityServices.getLogger().info("Book name - " + text);
        return text;
    }

    @Override
    public int getPageNumber() {
        Pattern ptrn = Pattern.compile("Page (\\d+) of \\d+ \\(.*\\)");
        Matcher matcher = ptrn.matcher(lblPageNumber.getText());
        return Integer.parseInt(matcher.group(1));
    }

    @Override
    public void scrollForBookStart() {
        lblBookTextPage.getTouchActions().scrollToElement(SwipeDirection.RIGHT);
    }

    @Override
    public void swipeFromLeftToRight() {
        Rectangle rectangle = lblPage.getElement().getRect();
        lblPage.getTouchActions().swipe(new Point(rectangle.x + rectangle.width, lblPage.getElement().getCenter().y));
    }

    @Override
    public void swipeFromRightToLeft() {
        lblPage.getTouchActions().swipe(new Point(0, lblPage.getElement().getCenter().y));
    }

    @Override
    public void clickLeftCorner() {
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.press(PointOption.point(0, lblPage.getElement().getCenter().y)).perform();
    }

    @Override
    public void clickRightCorner() {
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        Rectangle rectangle = lblPage.getElement().getRect();
        action.press(PointOption.point(rectangle.x + rectangle.width, lblPage.getElement().getCenter().y)).perform();
    }

    @Override
    public String getPageNumberInfo() {
        return null;
    }
}
