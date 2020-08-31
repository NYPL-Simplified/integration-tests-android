package screens.catalog;

import aquality.appium.mobile.application.AqualityServices;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidCatalogScreen extends CatalogScreen {
    private final IButton btnMenu =
            getElementFactory().getButton(By.xpath("//android.widget.ImageButton[@content-desc=\"Choose another library catalog…\"]"), "Menu");
    private final String LIBRARY_BUTTON_LOCATOR_PATTERN =
            "//android.widget.TextView[@resource-id=\"org.nypl.simplified.simplye:id/accountTitle\" and @text=\"%s\"]";
    private String BOOKS_LOCATOR = "//androidx.recyclerview.widget.RecyclerView[1]//android.widget.LinearLayout[@content-desc]";
    private String INFO_ATTRIBUTE = "content-desc";

    public AndroidCatalogScreen() {
        super(By.id("feedWithGroups"));
    }

    @Override
    public List<String> getListOfBooksNames() {
        List<String> listOfNames = getElementFactory().findElements(By.xpath(BOOKS_LOCATOR), ElementType.LABEL)
                .stream()
                .map(x -> x.getAttribute("content-desc"))
                .collect(Collectors.toList());
        AqualityServices.getLogger().info("Found list of books - " + listOfNames.stream().map(Object::toString).collect(Collectors.joining(", ")));
        return listOfNames;
    }

    @Override
    public void openLibrary(String libraryName) {
        btnMenu.click();
        getElementFactory().getButton(By.xpath(String.format(LIBRARY_BUTTON_LOCATOR_PATTERN, libraryName)), "Menu").click();
    }

    @Override
    public String getBookName(int index) {
        return getBookWithGivenIndex(index).getAttribute(INFO_ATTRIBUTE);
    }

    private IButton getBookWithGivenIndex(int index) {
        return getElementFactory().getButton(By.xpath(String.format("(%s)[%d]", BOOKS_LOCATOR, index)), "Book no" + index);
    }

    @Override
    public void clickBook(int index) {
        getBookWithGivenIndex(index).click();
    }
}
