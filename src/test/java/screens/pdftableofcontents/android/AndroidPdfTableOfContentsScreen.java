package screens.pdftableofcontents.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import framework.utilities.swipe.SwipeElementUtils;
import org.openqa.selenium.By;
import screens.pdftableofcontents.PdfTableOfContentsScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidPdfTableOfContentsScreen extends PdfTableOfContentsScreen {
    public static final String CHAPTER_NAME_BUTTON_XPATH_PATTERN = "//android.widget.TextView[contains(@resource-id,\"reader_toc_element_title\") and @text=\"%s\"]";
    public static final String PAGE_NUMBER_LOCATOR_PATTERN = "//android.widget.TextView[contains(@resource-id,\"reader_toc_element_title\") and @text=\"%s\"]//following-sibling::android.widget.TextView";
    private final ILabel lblTable = getElementFactory().getLabel(By.id("recyclerView"), "Table");

    private List<ILabel> getChapters() {
        return getElementFactory().findElements(By.xpath("//android.widget.TextView[contains(@resource-id,\"reader_toc_element_title\")]"), ElementType.LABEL);
    }

    public AndroidPdfTableOfContentsScreen() {
        super(By.id("pdf_reader_fragment_holder"));
    }

    public Set<String> getListOfBookChapters() {
        List<String> listOfChapters = getChapters().stream().map(IElement::getText).collect(Collectors.toList());
        Set<String> bookNames = new HashSet<>();
        do {
            bookNames.addAll(listOfChapters);
            SwipeElementUtils.swipeThroughEntireElementUp(lblTable);
            listOfChapters = getChapters().stream().map(IElement::getText).collect(Collectors.toList());
        } while (!bookNames.containsAll(listOfChapters));
        return bookNames;
    }

    @Override
    public void openChapter(String chapter) {
        IButton button = getElementFactory().getButton(By.xpath(String.format(CHAPTER_NAME_BUTTON_XPATH_PATTERN, chapter)), chapter);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        button.click();
    }

    @Override
    public int getChapterPageNumber(String chapter) {
        IButton button = getElementFactory().getButton(By.xpath(String.format(PAGE_NUMBER_LOCATOR_PATTERN, chapter)), chapter);
        button.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return Integer.parseInt(button.getText());
    }
}
