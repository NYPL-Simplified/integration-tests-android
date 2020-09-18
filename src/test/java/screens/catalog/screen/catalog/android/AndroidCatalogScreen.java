package screens.catalog.screen.catalog.android;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import framework.utilities.swipe.SwipeElementUtils;
import org.openqa.selenium.By;
import screens.catalog.screen.catalog.CatalogScreen;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidCatalogScreen extends CatalogScreen {
    private static final String CATEGORY_LOCATOR =
            "//*[@resource-id=\"org.nypl.simplified.simplye:id/feedLaneTitle\" and @text=\"%1$s\"]";
    private static final String LANE_BY_NAME_LOCATOR_PART = CATEGORY_LOCATOR +
            "/following-sibling::*[@resource-id=\"org.nypl.simplified.simplye:id/feedLaneCoversScroll\"]";
    private static final String BOOK_COVER_IN_THE_LANE_LOCATOR = "/android.widget.LinearLayout";
    private static final String CONTENT_VALUE_ATTRIBUTE = "content-desc";
    private static final String LIBRARY_BUTTON_LOCATOR_PATTERN =
            "//android.widget.TextView[@resource-id=\"org.nypl.simplified.simplye:id/accountTitle\" and @text=\"%s\"]";
    private static final String BOOKS_LOCATOR = "//androidx.recyclerview.widget.RecyclerView[1]"
            + "//android.widget.LinearLayout[@content-desc]";

    private static final String FEED_LANE_TITLES_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/feedLaneTitle\"]";

    private final ILabel firstLaneName = getElementFactory().getLabel(
            By.xpath(FEED_LANE_TITLES_LOC), "First lane name");

    public AndroidCatalogScreen() {
        super(By.id("feedWithGroups"));
    }

    @Override
    public List<String> getListOfBooksNames() {
        List<String> listOfNames = getValuesFromListOfLabels(String.format(LANE_BY_NAME_LOCATOR_PART,
                firstLaneName.getText()) + BOOK_COVER_IN_THE_LANE_LOCATOR);
        AqualityServices.getLogger().info("Found list of books - " + listOfNames.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return listOfNames;
    }

    @Override
    public boolean isCategoryPageLoad() {
        return AqualityServices.getConditionalWait().waitFor(() ->
                getElementFactory().findElements(By.xpath(FEED_LANE_TITLES_LOC), ElementType.LABEL).size() > 0);
    }

    @Override
    public void openLibrary(String libraryName) {
        getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN, libraryName)),
                "Menu").click();
    }

    @Override
    public String getBookName(int index) {
        return getBookWithGivenIndex(index).getAttribute(CONTENT_VALUE_ATTRIBUTE);
    }

    private IButton getBookWithGivenIndex(int index) {
        return getElementFactory().getButton(By.xpath(String.format("(%s)[%d]", BOOKS_LOCATOR, index)),
                "Book no" + index);
    }

    @Override
    public void clickBook(int index) {
        getBookWithGivenIndex(index).click();
    }

    @Override
    public void openCategory(String categoryName) {
        IButton categoryButton = getCategoryButton(categoryName);
        categoryButton.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        categoryButton.click();
    }

    private IButton getCategoryButton(String categoryName) {
        return getElementFactory().getButton(By.xpath(String.format(CATEGORY_LOCATOR, categoryName)), categoryName);
    }

    @Override
    public boolean isSubcategoryPresent(String subcategoryName) {
        IButton subcategoryButton = getCategoryButton(subcategoryName);
        subcategoryButton.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        return subcategoryButton.state().isDisplayed();
    }

    @Override
    public void switchToCatalogTab(String catalogTab) {
        getElementFactory().getButton(By.xpath("//android.widget.RadioButton[@text=\"" + catalogTab + "\"]"), catalogTab).click();
    }

    @Override
    public Set<String> getListOfAllBooksNamesInFirstLane() {
        return getListOfAllBooksNamesInSubcategoryLane(firstLaneName.getText());
    }

    @Override
    public Set<String> getListOfAllBooksNamesInSubcategoryLane(String lineName) {
        List<String> currentBooksNames = getListOfVisibleBooksNamesInSubcategoryLane(lineName);
        Set<String> bookNames = new HashSet<>();
        ILabel subcategoryLine = getElementFactory().getLabel(
                By.xpath(String.format(LANE_BY_NAME_LOCATOR_PART, lineName)),
                String.format("Subcategory %1$s line", lineName));
        do {
            bookNames.addAll(currentBooksNames);
            SwipeElementUtils.swipeFromRightToLeft(subcategoryLine);
            currentBooksNames = getListOfVisibleBooksNamesInSubcategoryLane(lineName);
        } while (!bookNames.containsAll(currentBooksNames));
        return bookNames;
    }

    private List<String> getListOfVisibleBooksNamesInSubcategoryLane(String lineName) {
        return getValuesFromListOfLabels(String.format(LANE_BY_NAME_LOCATOR_PART, lineName)
                + BOOK_COVER_IN_THE_LANE_LOCATOR);
    }

    private List<String> getValuesFromListOfLabels(String xpath) {
        return getElementFactory().findElements(By.xpath(xpath), ElementType.LABEL)
                .stream()
                .map(x -> x.getAttribute(CONTENT_VALUE_ATTRIBUTE))
                .collect(Collectors.toList());
    }
}
