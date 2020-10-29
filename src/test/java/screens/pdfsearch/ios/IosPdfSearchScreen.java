package screens.pdfsearch.ios;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.elements.interfaces.ITextBox;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import org.testng.Assert;
import screens.pdfsearch.PdfSearchScreen;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.IOS)
public class IosPdfSearchScreen extends PdfSearchScreen {
    private static final String MAIN_ELEMENT = "//XCUIElementTypeSearchField";
    private static final String SEARCHED_ELEMENTS_LOC = "//XCUIElementTypeCell";
    private static final String SEARCHED_ELEMENT_NAME_LOC = ".//XCUIElementTypeStaticText[@name][1]";
    private static final String SEARCHED_ELEMENT_PAGE_NUMBER_LOC = ".//XCUIElementTypeStaticText[@name][2]";

    private final ITextBox searchTxb = getElementFactory().getTextBox(
            By.xpath(MAIN_ELEMENT), "Search");
    private final IButton applySearchBtn = getElementFactory().getButton(
            By.xpath("//XCUIElementTypeButton[@name=\"Search\"]"), "Apply search");

    public IosPdfSearchScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void findTextInTheDocument(String textToBeFound) {
        searchTxb.sendKeys(textToBeFound);
        applySearchBtn.click();
    }

    @Override
    public boolean isFoundItemsExist() {
        return AqualityServices.getConditionalWait().waitFor(() -> getSearchedElements().size() > 0);
    }

    @Override
    public List<String> getListOfFoundItems() {
        return isFoundItemsExist()
                ? getSearchedElements().stream().map(element -> element
                .findChildElement(By.xpath(SEARCHED_ELEMENT_NAME_LOC), ElementType.LABEL)
                .getText())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public void openSearchedItemByName(final String itemName) {
        getSearchedItemByName(itemName).click();
    }

    @Override
    public String getSearchedItemPageNumber(final String itemName) {
        return getSearchedItemByName(itemName)
                .findChildElement(By.xpath(SEARCHED_ELEMENT_PAGE_NUMBER_LOC), ElementType.LABEL)
                .getText();
    }

    private ILabel getSearchedItemByName(final String itemName) {
        Assert.assertTrue(isFoundItemsExist(), "No items were found");
        ILabel targetItem = getSearchedElements()
                .stream()
                .filter(element -> element
                        .findChildElement(By.xpath(SEARCHED_ELEMENT_NAME_LOC), ElementType.LABEL)
                        .getText()
                        .equals(itemName))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(targetItem, "The item that was tried to find does not exist");
        return targetItem;
    }

    private List<ILabel> getSearchedElements() {
        return getElementFactory().findElements(By.xpath(SEARCHED_ELEMENTS_LOC), ElementType.LABEL);
    }
}
