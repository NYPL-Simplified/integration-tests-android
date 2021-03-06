package screens.catalog.screen.catalog.ios;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.ElementState;
import aquality.selenium.core.elements.ElementsCount;
import constants.application.ReaderType;
import constants.application.attributes.IosAttributes;
import constants.application.timeouts.AuthorizationTimeouts;
import constants.application.timeouts.CategoriesTimeouts;
import framework.utilities.swipe.SwipeElementUtils;
import org.openqa.selenium.By;
import screens.catalog.screen.catalog.CatalogScreen;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosCatalogScreen extends CatalogScreen {
    private static final String CATEGORY_LOCATOR = "//XCUIElementTypeButton[@name=\"%1$s\"]/following-sibling::XCUIElementTypeButton[contains(@name, \"More\")]";
    private static final String LANE_BY_NAME_LOCATOR_PART = "(//XCUIElementTypeOther[.//XCUIElementTypeButton[@name=\"%1$s\"]]"
            + "/following-sibling::XCUIElementTypeCell)[1]";
    private static final String BOOK_COVER_IN_LANE_LOCATOR = "/XCUIElementTypeButton";
    private static final String FEED_LANE_TITLES_LOCATOR =
            "//XCUIElementTypeOther[./following-sibling::XCUIElementTypeCell[1]]//XCUIElementTypeButton[1]";
    private static final String LIBRARY_BUTTON_LOCATOR_PATTERN =
            "//XCUIElementTypeButton[@name=\"%1$s\"]";

    private static final String BOOKS_LOCATOR = "//XCUIElementTypeTable//XCUIElementTypeCell//XCUIElementTypeButton[@name]";
    private static final String CATEGORY_XPATH_PATTERN = "//XCUIElementTypeOther//XCUIElementTypeButton//XCUIElementTypeStaticText[not(contains(@name, 'More'))]";
    private static final int COUNT_OF_CATEGORIES_TO_WAIT_FOR = 5;

    private final ILabel firstLaneName =
            getElementFactory().getLabel(By.xpath(FEED_LANE_TITLES_LOCATOR), "First lane name", ElementState.EXISTS_IN_ANY_STATE);
    private final ILabel categoryScreen = getElementFactory().getLabel(By.xpath("//XCUIElementTypeTable"), "Category Screen");
    private final ILabel buttonMore = getElementFactory().getLabel(By.xpath("//XCUIElementTypeButton[contains(@name, 'More')][1]"), "Button More...");

    public IosCatalogScreen() {
        super(By.xpath(FEED_LANE_TITLES_LOCATOR));
    }

    @Override
    public List<String> getListOfBooksNames() {
        state().waitForDisplayed();
        int countOfItems = getElements(BOOKS_LOCATOR).size();
        AqualityServices.getConditionalWait().waitFor(() -> getElements(BOOKS_LOCATOR).size() <= countOfItems);
        List<String> listOfNames = getValuesFromListOfLabels(BOOKS_LOCATOR);
        AqualityServices.getLogger().info("Found list of books - " + listOfNames.stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        return listOfNames;
    }

    @Override
    public boolean isCategoryPageLoad() {
        return AqualityServices.getConditionalWait().waitFor(() ->
                        getElementFactory().findElements(By.xpath(FEED_LANE_TITLES_LOCATOR), ElementType.LABEL).size() > 0,
                Duration.ofMillis(CategoriesTimeouts.TIMEOUT_WAIT_UNTIL_CATEGORY_PAGE_LOAD.getTimeoutMillis()));
    }

    @Override
    public void openLibrary(String libraryName) {
        getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN, libraryName)),
                "Menu").click();
    }

    @Override
    public String getBookName(int index) {
        return getBookWithGivenIndex(index).getAttribute(IosAttributes.NAME);
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
        categoryButton.state().waitForDisplayed();
        categoryButton.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        categoryButton.state().waitForDisplayed();
        categoryButton.click();
    }

    private IButton getCategoryButton(String categoryName) {
        return getElementFactory().getButton(By.xpath(String.format(CATEGORY_LOCATOR, categoryName)), categoryName);
    }

    @Override
    public void switchToCatalogTab(String catalogTab) {
        getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN, catalogTab)), catalogTab).click();
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
        subcategoryLine.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        do {
            bookNames.addAll(currentBooksNames);
            SwipeElementUtils.swipeFromRightToLeft(subcategoryLine);
            currentBooksNames = getListOfVisibleBooksNamesInSubcategoryLane(lineName);
        } while (!bookNames.containsAll(currentBooksNames));
        return bookNames;
    }

    @Override
    public void openFirstBookFromLane(ReaderType readerType, String laneName) {
    }

    @Override
    public boolean isAnyBookPresentInLane(ReaderType readerType, String laneName) {
        return false;
    }

    @Override
    public void openFirstBookOfType(ReaderType readerType) {
    }

    @Override
    public void swipeScreenUp() {
    }

    @Override
    public boolean isErrorButtonPresent() {
        return false;
    }

    @Override
    public String getErrorDetails() {
        return null;
    }

    @Override
    public Set<String> getAllCategoriesNames() {
        AqualityServices.getConditionalWait().waitFor(() -> getElements(CATEGORY_XPATH_PATTERN).size() > COUNT_OF_CATEGORIES_TO_WAIT_FOR);
        List<String> currentBooksNames = geListOfCategoriesNames();
        Set<String> categoriesNames = new HashSet<>();
        do {
            categoriesNames.addAll(currentBooksNames);
            List<String> finalCurrentBooksNames = currentBooksNames;
            SwipeElementUtils.swipeElementUp(categoryScreen);
            SwipeElementUtils.swipeElementUp(categoryScreen);
            AqualityServices.getConditionalWait().waitFor(() -> !finalCurrentBooksNames.containsAll(geListOfCategoriesNames())
                    , Duration.ofMillis(AuthorizationTimeouts.DEBUG_MENU_IS_OPENED.getTimeoutMillis()));
            currentBooksNames = geListOfCategoriesNames();
        } while (!categoriesNames.containsAll(currentBooksNames));
        return categoriesNames;
    }

    @Override
    public void openFirstCategory() {
        buttonMore.click();
    }

    private List<String> geListOfCategoriesNames() {
        return getValuesFromListOfLabels(CATEGORY_XPATH_PATTERN);
    }

    private List<String> getListOfVisibleBooksNamesInSubcategoryLane(String lineName) {
        return getValuesFromListOfLabels(String.format(LANE_BY_NAME_LOCATOR_PART, lineName) + BOOK_COVER_IN_LANE_LOCATOR);
    }

    private List<String> getValuesFromListOfLabels(String xpath) {
        return getElements(xpath)
                .stream()
                .map(x -> x.getAttribute(IosAttributes.NAME))
                .collect(Collectors.toList());
    }

    private List<IElement> getElements(String xpath) {
        return getElementFactory().findElements(By.xpath(xpath), ElementType.LABEL, ElementsCount.MORE_THEN_ZERO, ElementState.EXISTS_IN_ANY_STATE);
    }
}
