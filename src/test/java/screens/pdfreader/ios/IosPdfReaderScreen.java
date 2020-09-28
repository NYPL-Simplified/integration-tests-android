package screens.pdfreader.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import framework.utilities.swipe.SwipeElementUtils;
import org.openqa.selenium.By;
import screens.pdfreader.PdfReaderScreen;

@ScreenType(platform = PlatformName.IOS)
public class IosPdfReaderScreen extends PdfReaderScreen {
    private final ILabel lblBookName = getElementFactory().getLabel(By.id(""), "Book Name");
    private final ILabel lblPageNumber = getElementFactory().getLabel(By.xpath(""), "Book Page number");
    private final ILabel lblPage = getElementFactory().getLabel(By.xpath(""), "Book Page");

    public IosPdfReaderScreen() {
        super(By.xpath(""));
    }

    @Override
    public String getBookName() {
        return lblBookName.getText();
    }

    @Override
    public int getPageNumber() {
        return Integer.parseInt(lblPageNumber.getText());
    }

    @Override
    public void swipeFromRightToLeft() {
        SwipeElementUtils.swipeFromRightToLeft(lblPage);
    }

    @Override
    public void swipeFromLeftToRight() {
        SwipeElementUtils.swipeFromLeftToRight(lblPage);
    }
}
