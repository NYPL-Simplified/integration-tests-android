package screens.subcategory.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import io.appium.java_client.MobileBy;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.subcategory.SubcategoryScreen;

import java.util.List;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosSubcategoryScreen extends SubcategoryScreen {
    private static final String BOOKS_LOCATOR = "//XCUIElementTypeCell";
    private static final String BOOK_BUTTON_XPATH = BOOKS_LOCATOR + "//XCUIElementTypeButton";
    private static final String BOOK_COVER_LOCATOR_PATTERN =
            "//XCUIElementTypeCell[.//XCUIElementTypeStaticText[contains(@name,\"%1$s\")]]";
    private static final String AUTHOR_INFO_XPATH = "//XCUIElementTypeStaticText[@name][2]";
    private static final String BOOK_NAME_XPATH =
            "//XCUIElementTypeStaticText[@name and not(.//ancestor::XCUIElementTypeButton)][1]";
    public static final String BOOK_NAME_LOCATOR_PATTERN = "//XCUIElementTypeStaticText[@name=\"%s\"]";
    public static final String AUTHOR_LABEL_LOCATOR_PATTERN = "//following-sibling::XCUIElementTypeStaticText";

    private final ILabel lblFirstBookName =
            getElementFactory().getLabel(By.xpath(BOOKS_LOCATOR + BOOK_NAME_XPATH), "First book name");
    private final ILabel lblFirstBookAuthor =
            getElementFactory().getLabel(By.xpath(BOOKS_LOCATOR + AUTHOR_INFO_XPATH), "First book author");

    public IosSubcategoryScreen() {
        super(By.xpath("//XCUIElementTypeCollectionView"));
    }

    @Override
    public List<String> getBooksInfo() {
        List<String> listOfNames = getElementFactory().findElements(By.xpath(BOOKS_LOCATOR + BOOK_NAME_XPATH), ElementType.LABEL)
                .stream()
                .map(IElement::getText)
                .collect(Collectors.toList());
        AqualityServices.getLogger().info("Found list of books - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return listOfNames;
    }

    @Override
    public List<String> getAllButtonsNames() {
        List<String> listOfNames = getValuesFromListOfLabels(BOOKS_LOCATOR + BOOK_BUTTON_XPATH);
        AqualityServices.getLogger().info("Found list of buttons names - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining("; ")));
        return listOfNames;
    }

    @Override
    public List<String> getTitlesInfo() {
        List<String> listOfNames = getValuesFromListOfLabels(BOOKS_LOCATOR + BOOK_NAME_XPATH);
        AqualityServices.getLogger().info("Found list of titles - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining("; ")));
        return listOfNames;
    }

    @Override
    public void openBook(CatalogBookModel bookInfo) {
        String title = bookInfo.getTitle();
        getElementFactory().getButton(By.xpath(String.format(BOOK_COVER_LOCATOR_PATTERN, title)), title).click();
    }

    @Override
    public CatalogBookModel openBookByName(String bookName) {
        ILabel lblBookName = getElementFactory().getLabel(MobileBy.AccessibilityId(bookName), bookName);
        lblBookName.state().waitForDisplayed();
        ILabel lblAuthor =
                getElementFactory().getLabel(By.xpath(String.format(BOOK_NAME_LOCATOR_PATTERN, bookName) + AUTHOR_LABEL_LOCATOR_PATTERN), bookName);
        lblAuthor.state().waitForDisplayed();
        CatalogBookModel bookInfo = new CatalogBookModel()
                .setTitle(lblBookName.getText())
                .setAuthor(lblAuthor.getText());
        lblBookName.click();
        return bookInfo;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public boolean isErrorButtonPresent() {
        return false;
    }

    @Override
    public List<String> getAuthorsInfo() {
        List<String> listOfNames = getValuesFromListOfLabels(BOOKS_LOCATOR + AUTHOR_INFO_XPATH);
        AqualityServices.getLogger().info("Found list of authors - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining("; ")));
        return listOfNames;
    }

    private List<String> getValuesFromListOfLabels(String xpath) {
        return getElementFactory().findElements(By.xpath(xpath), ElementType.LABEL)
                .stream()
                .map(IElement::getText)
                .collect(Collectors.toList());
    }

    @Override
    public CatalogBookModel getFirstBookInfo() {
        return new CatalogBookModel()
                .setTitle(lblFirstBookName.getText())
                .setAuthor(lblFirstBookAuthor.getText());
    }

    @Override
    public void openFirstBook() {
        lblFirstBookName.click();
    }
}
