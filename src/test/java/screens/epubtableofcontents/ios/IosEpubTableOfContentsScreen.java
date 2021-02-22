package screens.epubtableofcontents.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import framework.utilities.swipe.SwipeElementUtils;
import org.openqa.selenium.By;
import screens.epubtableofcontents.EpubTableOfContentsScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosEpubTableOfContentsScreen extends EpubTableOfContentsScreen {
    private static final String CHAPTERS_LIST_LOC = "//XCUIElementTypeTable";
    private static final String CHAPTER_TEXT_LOC = "//XCUIElementTypeCell//XCUIElementTypeStaticText[@name]";

    private final ILabel lblTable = getElementFactory().getLabel(By.xpath(CHAPTERS_LIST_LOC), "Table");

    public IosEpubTableOfContentsScreen() {
        super(By.xpath(CHAPTERS_LIST_LOC));
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

    private List<ILabel> getChapters() {
        return getElementFactory().findElements(By.xpath(CHAPTERS_LIST_LOC + CHAPTER_TEXT_LOC), ElementType.LABEL);
    }
}
