package screens.tableofcontents.android;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import framework.utilities.swipe.SwipeElementUtils;
import org.openqa.selenium.By;
import screens.tableofcontents.TableOfContentsScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidTableOfContentsScreen extends TableOfContentsScreen {
    private final ILabel lblTable =
            getElementFactory().getLabel(By.id("reader_toc_list"), "Table");

    private List<ILabel> getChapters() {
        return getElementFactory().findElements(By.xpath("//android.widget.TextView[contains(@resource-id,\"reader_toc_element_text\")]"), ElementType.LABEL);
    }

    public AndroidTableOfContentsScreen() {
        super(By.id("reader_toc_list"));
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
}
