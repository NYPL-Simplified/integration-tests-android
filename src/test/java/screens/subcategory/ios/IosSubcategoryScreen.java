package screens.subcategory.ios;

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

@ScreenType(platform = PlatformName.IOS)
public class IosSubcategoryScreen extends SubcategoryScreen {
    private static final String BOOKS_LOCATOR = null;
    public static final String BOOK_BUTTON_XPATH = null;
    public static final String BOOK_COVER_LOCATOR_PATTERN = null;
    private final String SORTING_BUTTON_XPATH_PATTERN = null;
    private final ILabel lblSubcategoryName = getElementFactory().getLabel(null, "Category name");
    private final IButton btnSortBy = getElementFactory().getButton(null, "Sort By");
    private final IButton btnSortByAvailability =
            getElementFactory().getButton(null, "Sort By Availability");
    private final ILabel lblFirstBookInfo =
            getElementFactory().getLabel(null, "First book info");
    private final String AUTHOR_INFO_XPATH = null;
    private final String BOOK_NAME_XPATH = null;

    public IosSubcategoryScreen() {
        super(By.xpath(""));
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
        getElementFactory().getButton(null, sortingCategory).click();
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
