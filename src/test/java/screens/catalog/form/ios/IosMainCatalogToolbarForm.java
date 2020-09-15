package screens.catalog.form.ios;

import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import org.openqa.selenium.By;
import screens.catalog.form.MainCatalogToolbarForm;

@ScreenType(platform = PlatformName.IOS)
public class IosMainCatalogToolbarForm extends MainCatalogToolbarForm {
    private static final String MAIN_ELEMENT_LOC = "//XCUIElementTypeNavigationBar";
    private static final String CATEGORY_INFO_LOCATOR_PART = MAIN_ELEMENT_LOC + "//XCUIElementTypeStaticText";

    private final IButton btnChooseAnotherLibrary =
            getElementFactory().getButton(
                    By.xpath(MAIN_ELEMENT_LOC +
                            "//XCUIElementTypeButton[@name=\"Change Library Account\"]"),
                    "Change library account");
    private final IButton backBtn = getElementFactory().getButton(
            By.xpath(MAIN_ELEMENT_LOC + "//XCUIElementTypeButton[@name=\"Back\"]"),
            "Back button");

    private final ILabel lblCategoryName =
            getElementFactory().getLabel(By.xpath(CATEGORY_INFO_LOCATOR_PART + "[1]"), "Category name");
    private final ILabel lblCatalogName =
            getElementFactory().getLabel(By.xpath(CATEGORY_INFO_LOCATOR_PART + "[2]"), "Catalog name"); // not defined on the ios app

    private final IButton btnSearch = getElementFactory().getButton(By.xpath(MAIN_ELEMENT_LOC +
            "//XCUIElementTypeButton[@name=\"Search\"]"), "Search");
    private final IButton btnMoreOptions = getElementFactory().getButton(
            By.xpath(MAIN_ELEMENT_LOC + " "), "Menu"); // not defined on the ios app

    public IosMainCatalogToolbarForm() {
        super(By.xpath(MAIN_ELEMENT_LOC));
    }

    @Override
    public void chooseAnotherLibrary() {
        btnChooseAnotherLibrary.click();
    }

    @Override
    public void goBack() {
        backBtn.click();
    }

    @Override
    public String getCatalogName() {
        return lblCatalogName.getText();
    }

    @Override
    public String getCategoryName() {
        return lblCategoryName.getText();
    }

    @Override
    public void openSearchModal() {
        btnSearch.click();
    }

    @Override
    public void openMoreOptions() {
        btnMoreOptions.click();
    }
}
