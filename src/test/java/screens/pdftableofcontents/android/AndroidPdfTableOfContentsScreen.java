package screens.pdftableofcontents.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import framework.utilities.swipe.SwipeElementUtils;
import framework.utilities.swipe.directions.EntireElementSwipeDirection;
import org.apache.commons.lang3.NotImplementedException;
import org.openqa.selenium.By;
import screens.pdftableofcontents.PdfTableOfContentsScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidPdfTableOfContentsScreen extends PdfTableOfContentsScreen {
    private static final String CHAPTER_NAME_BUTTON_XPATH_PATTERN = "//android.widget.TextView[contains(@resource-id,\"reader_toc_element_title\") and @text=\"%s\"]";
    private static final String PAGE_NUMBER_LOCATOR_PATTERN = "//android.widget.TextView[contains(@resource-id,\"reader_toc_element_title\") and @text=\"%s\"]//following-sibling::android.widget.TextView";
    private static final String CHAPTER_XPATH_LOCATOR = "//android.widget.TextView[contains(@resource-id,\"reader_toc_element_title\")]";
    private final ILabel lblTable = getElementFactory().getLabel(By.id("recyclerView"), "Table");

    public AndroidPdfTableOfContentsScreen() {
        super(By.id("pdf_reader_fragment_holder"));
    }

    @Override
    public void switchToChaptersListView() {
        // not implemented on Android
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

    @Override
    public boolean isGalleryPagesLoaded() {
        throw new NotImplementedException();
    }

    @Override
    public int getCountOfBookPages() {
        throw new NotImplementedException();
    }

    @Override
    public void scrollGallery(EntireElementSwipeDirection entireElementSwipeDirection) {
        throw new NotImplementedException();
    }

    @Override
    public void openGalleryPage(int pageNumber) {
        throw new NotImplementedException();
    }

    private List<ILabel> getChapters() {
        return getElementFactory().findElements(By.xpath(CHAPTER_XPATH_LOCATOR), ElementType.LABEL);
    }
}
