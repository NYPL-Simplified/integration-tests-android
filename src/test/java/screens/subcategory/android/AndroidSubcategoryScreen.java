package screens.subcategory.android;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import aquality.selenium.core.elements.interfaces.IElement;
import constants.application.attributes.AndroidAttributes;
import models.android.CatalogBookModel;
import org.openqa.selenium.By;
import screens.subcategory.SubcategoryScreen;

import java.util.List;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidSubcategoryScreen extends SubcategoryScreen {
    private static final String BOOKS_LOCATOR = "//android.widget.ImageView[contains(@resource-id,\"bookCellIdleCover\")]";
    public static final String BOOK_BUTTON_XPATH =
            "//android.widget.LinearLayout[contains(@resource-id,\"bookCellIdleButtons\")]/android.widget.Button";
    public static final String BOOK_COVER_LOCATOR_PATTERN =
            "//android.widget.ImageView[contains(@resource-id, \"bookCellIdleCover\") and @content-desc=\"%s\"]";
    private static final String AUTHOR_INFO_XPATH =
            "//android.widget.TextView[contains(@resource-id, \"bookCellIdleAuthor\")]";
    private static final String BOOK_NAME_XPATH =
            "//android.widget.TextView[contains(@resource-id, \"bookCellIdleTitle\")]";
    private static final String TYPE_INFO_XPATH =
            "//android.widget.TextView[contains(@resource-id, \"bookCellIdleMeta\")]";
    public static final String BOOK_WITH_GIVEN_NAME_LOCATOR_PATTERN = "//android.widget.TextView[contains(@resource-id, \"bookCellIdleTitle\") and @text=\"%s\"]";
    public static final String COVER_LABEL_LOCATOR_PART = "//preceding-sibling::android.widget.ImageView[contains(@resource-id, \"bookCellIdleCover\")]";
    public static final String AUTHOR_LABEL_LOCATOR_PART = "//following-sibling::android.widget.TextView[contains(@resource-id, \"bookCellIdleAuthor\")]";
    public static final String BOOK_TYPE_LABEL_LOCATOR_PART = "//following-sibling::android.widget.TextView[contains(@resource-id, \"bookCellIdleMeta\")]";

    private final ILabel lblFirstBookImageCover =
            getElementFactory().getLabel(By.xpath(BOOKS_LOCATOR), "First book image info");
    private final ILabel lblFirstBookTitle =
            getElementFactory().getLabel(By.xpath(BOOK_NAME_XPATH), "First book title");
    private final ILabel lblFirstBookType =
            getElementFactory().getLabel(By.xpath(TYPE_INFO_XPATH), "First book type");
    private final ILabel lblFirstBookAuthor =
            getElementFactory().getLabel(By.xpath(AUTHOR_INFO_XPATH), "First book author");

    public AndroidSubcategoryScreen() {
        super(By.xpath("//androidx.recyclerview.widget.RecyclerView[contains(@resource-id,\"feedWithoutGroupsList\")]"));
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
    public void openBook(CatalogBookModel bookInfo) {
        String imageTitle = bookInfo.getImageTitle();
        getElementFactory().getButton(By.xpath(String.format(BOOK_COVER_LOCATOR_PATTERN, imageTitle)), imageTitle).click();
    }

    @Override
    public CatalogBookModel openBookByName(String bookName) {
        String locator = String.format(BOOK_WITH_GIVEN_NAME_LOCATOR_PATTERN, bookName);

        CatalogBookModel bookInfo = new CatalogBookModel()
                .setImageTitle(getElementFactory().getButton(By.xpath(locator + COVER_LABEL_LOCATOR_PART), bookName).getAttribute(AndroidAttributes.CONTENT_DESC))
                .setTitle(bookName)
                .setAuthor(getElementFactory().getButton(By.xpath(locator + AUTHOR_LABEL_LOCATOR_PART), bookName).getText())
                .setBookType(getElementFactory().getButton(By.xpath(locator + BOOK_TYPE_LABEL_LOCATOR_PART), bookName).getText());
        getElementFactory().getButton(By.xpath(locator), bookName).click();
        return bookInfo;
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

    @Override
    public CatalogBookModel getFirstBookInfo() {
        return new CatalogBookModel()
                .setImageTitle(lblFirstBookImageCover.getAttribute(AndroidAttributes.CONTENT_DESC))
                .setTitle(lblFirstBookTitle.getText())
                .setAuthor(lblFirstBookAuthor.getText())
                .setBookType(lblFirstBookType.getText());
    }

    @Override
    public void openFirstBook() {
        lblFirstBookImageCover.click();
    }
}
