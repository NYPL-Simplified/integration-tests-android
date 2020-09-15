package screens.subcategory.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;
import screens.subcategory.SubcategoryScreen;

import java.util.List;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSubcategoryScreen extends SubcategoryScreen {
    private static final String BOOKS_LOCATOR = "//android.widget.ImageView[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleCover\"]";
    public static final String BOOK_BUTTON_XPATH =
            "//android.widget.LinearLayout[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleButtons\"]/android.widget.Button";
    public static final String BOOK_COVER_LOCATOR_PATTERN =
            "//android.widget.ImageView[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleCover\" and @content-desc=\"%s\"]";
    private final String SORTING_BUTTON_XPATH_PATTERN =
            "//android.widget.LinearLayout[@resource-id=\"org.nypl.simplified.simplye:id/feedHeaderFacets\"]/android.widget.Button";
    private final ILabel lblSubcategoryName =
            getElementFactory().getLabel(By.xpath("//android.view.ViewGroup[@resource-id=\"org.nypl.simplified.simplye:id/mainToolbar\"]//android.widget.TextView[1]"), "Category name");
    private final IButton btnSortBy = getElementFactory().getButton(By.xpath(SORTING_BUTTON_XPATH_PATTERN + "[2]"), "Sort By");
    private final IButton btnSortByAvailability =
            getElementFactory().getButton(By.xpath(SORTING_BUTTON_XPATH_PATTERN + "[1]"), "Sort By Availability");
    private final ILabel lblFirstBookInfo =
            getElementFactory().getLabel(By.xpath(BOOKS_LOCATOR), "First book info");
    private final String AUTHOR_INFO_XPATH =
            "//android.widget.TextView[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleAuthor\"]";
    private final String BOOK_NAME_XPATH =
            "//android.widget.TextView[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleTitle\"]";

    public AndroidSubcategoryScreen() {
        super(By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"org.nypl.simplified.simplye:id/feedWithoutGroupsList\"]"));
    }

    @Override
    public String getSubcategoryName() {
        return lblSubcategoryName.getText();
    }

    @Override
    public void sortBy(String sortingCategory) {
        btnSortBy.click();
        selectCheckedListValue(sortingCategory);
    }

    @Override
    public List<String> getBooksInfo() {
        List<String> listOfNames = getElementFactory().findElements(By.xpath(BOOKS_LOCATOR), ElementType.LABEL)
                .stream()
                .map(x -> x.getAttribute("content-desc"))
                .collect(Collectors.toList());
        AqualityServices.getLogger().info("Found list of books - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return listOfNames;
    }

    @Override
    public void sortByAvailability(String sortingCategory) {
        btnSortByAvailability.click();
        selectCheckedListValue(sortingCategory);
    }

    @Override
    public List<String> getAllButtonsNames() {
        List<String> listOfNames = getValuesFromListOfLabels(BOOK_BUTTON_XPATH);
        AqualityServices.getLogger().info("Found list of buttons names - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return listOfNames;
    }

    @Override
    public List<String> getTitlesInfo() {
        List<String> listOfNames = getValuesFromListOfLabels(BOOK_NAME_XPATH);
        AqualityServices.getLogger().info("Found list of titles - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return listOfNames;
    }

    @Override
    public void openBook(AndroidCatalogBookModel bookInfo) {
        String imageTitle = bookInfo.getImageTitle();
        getElementFactory().getButton(By.xpath(String.format(BOOK_COVER_LOCATOR_PATTERN, imageTitle)), imageTitle).click();
    }

    @Override
    public List<String> getAuthorsInfo() {
        List<String> listOfNames = getValuesFromListOfLabels(AUTHOR_INFO_XPATH);
        AqualityServices.getLogger().info("Found list of authors - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return listOfNames;
    }

    private List<String> getValuesFromListOfLabels(String xpath) {
        return getElementFactory().findElements(By.xpath(xpath), ElementType.LABEL)
                .stream()
                .map(IElement::getText)
                .collect(Collectors.toList());
    }

    private void selectCheckedListValue(String sortingCategory) {
        getElementFactory().getButton(By.xpath("//android.widget.CheckedTextView[@text=\"" + sortingCategory + "\"]"), sortingCategory).click();
    }

    @Override
    public String getFirstBookInfo() {
        return lblFirstBookInfo.getAttribute("content-desc");
    }

    @Override
    public void openFirstBook() {
        lblFirstBookInfo.click();
    }
}
