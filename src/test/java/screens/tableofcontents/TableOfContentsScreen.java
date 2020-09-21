package screens.tableofcontents;

import aquality.appium.mobile.screens.Screen;
import org.openqa.selenium.By;

import java.util.Set;

public abstract class TableOfContentsScreen extends Screen {
    protected TableOfContentsScreen(By locator) {
        super(locator, "Table of Contents");
    }

    public abstract Set<String> getListOfBookChapters();
}
