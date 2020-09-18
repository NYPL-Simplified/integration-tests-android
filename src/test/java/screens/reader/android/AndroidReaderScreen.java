package screens.reader.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import framework.utilities.swipe.SwipeElementUtils;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import screens.reader.ReaderScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidReaderScreen extends ReaderScreen {
    private final ILabel lblBookName =
            getElementFactory().getLabel(By.xpath("//android.widget.TextView[contains(@resource-id,\"reader_title_text\")]"), "Book Cover");
    private final ILabel lblPageNumber =
            getElementFactory().getLabel(By.xpath("//android.widget.TextView[contains(@resource-id,\"reader_position_text\")]"), "Page Number");
    private final ILabel lblPage =
            getElementFactory().getLabel(By.xpath("//android.webkit.WebView"), "Page View");
    private final ILabel lblTable =
            getElementFactory().getLabel(By.id("reader_toc_list"), "Table");
    private final IButton btnChapters =
            getElementFactory().getButton(By.xpath("//android.widget.ImageView[@content-desc=\"Show table of contents\"]"), "Chapters");

    private List<ILabel> getChapters() {
        return getElementFactory().findElements(By.xpath("//android.widget.TextView[contains(@resource-id,\"reader_toc_element_text\")]"), ElementType.LABEL);
    }

    public AndroidReaderScreen() {
        super(By.id("//android.view.View[@resource-id=\"reflowable-book-frame\"]"));
    }

    @Override
    public String getBookName() {
        String text = lblBookName.getText();
        AqualityServices.getLogger().info("Book name - " + text);
        return text;
    }

    @Override
    public void swipeFromLeftToRight() {
        SwipeElementUtils.swipeFromLeftToRight(lblPage);
    }

    @Override
    public void swipeFromRightToLeft() {
        SwipeElementUtils.swipeFromRightToLeft(lblPage);
    }

    @Override
    public void clickLeftCorner() {
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        action.tap(PointOption.point(0, lblPage.getElement().getCenter().y)).perform();
    }

    @Override
    public void clickRightCorner() {
        TouchAction action = new TouchAction(AqualityServices.getApplication().getDriver());
        Point upperLeftCorner = lblPage.getElement().getLocation();
        Point center = lblPage.getElement().getCenter();
        Dimension dimensions = lblPage.getElement().getSize();
        action.tap(PointOption.point(upperLeftCorner.x + dimensions.width - 1, center.y)).perform();
    }

    @Override
    public String getPageNumberInfo() {
        return lblPageNumber.getText();
    }

    @Override
    public Set<String> getListOfChapters() {
        btnChapters.click();
        lblTable.state().waitForExist();
        List<String> listOfChapters = getChapters().stream().map(IElement::getText).collect(Collectors.toList());
        Set<String> bookNames = new HashSet<>();
        do {
            bookNames.addAll(listOfChapters);
            SwipeElementUtils.swipeThroughEntireElementUp(lblTable);
            listOfChapters = getChapters().stream().map(IElement::getText).collect(Collectors.toList());
        } while (!bookNames.containsAll(listOfChapters));
        AqualityServices.getApplication().getDriver().navigate().back();
        AqualityServices.getLogger().info("Found chapters - " + listOfChapters.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return bookNames;
    }

    @Override
    public void openChapter(String chapter) {
        btnChapters.click();
        IButton button = getElementFactory().getButton(By.xpath("//android.widget.TextView[contains(@resource-id,\"reader_toc_element_text\") and @text=\"" + chapter + "\"]"), chapter);
        if (!button.state().isDisplayed()) {
            button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        }
        button.click();
    }
}
