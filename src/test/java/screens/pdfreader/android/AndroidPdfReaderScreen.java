package screens.pdfreader.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.pdfreader.PdfReaderScreen;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidPdfReaderScreen extends PdfReaderScreen {
    private final ILabel lblBookName = getElementFactory().getLabel(By.id("title_textView"), "Book Name");
    private final ILabel lblPageNumber =
            getElementFactory().getLabel(By.xpath("//*[contains(@resource-id,\"pdfView\")]//android.widget.TextView"), "Book Page number");

    public AndroidPdfReaderScreen() {
        super(By.id("pdf_reader_hud_container"));
    }

    @Override
    public String getBookName() {
        return lblBookName.getText();
    }

    @Override
    public int getPageNumber() {
        return Integer.parseInt(lblPageNumber.getText());
    }
}
