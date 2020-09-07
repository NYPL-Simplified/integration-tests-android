package screens.catalog.screen.books;

import aquality.appium.mobile.actions.SwipeDirection;
import aquality.appium.mobile.application.PlatformName;
import aquality.appium.mobile.elements.ElementType;
import aquality.appium.mobile.elements.interfaces.IButton;
import aquality.appium.mobile.elements.interfaces.IElement;
import aquality.appium.mobile.elements.interfaces.ILabel;
import aquality.appium.mobile.screens.screenfactory.ScreenType;
import constants.android.AndroidAttributes;
import constants.android.catalog.AndroidBookAddButtonKeys;
import models.android.AndroidCatalogBookModel;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Objects;

@ScreenType(platform = PlatformName.ANDROID)
public class AndroidCatalogBooksScreen extends CatalogBooksScreen {
    private static final String MAIN_ELEMENT = "//android.widget.TextView[contains(@content-desc, "
            + "concat(\"Search in \", "
            + "//*[@resource-id=\"org.nypl.simplified.simplye:id/mainToolbar\"]/android.widget.TextView[1]/@text))]";

    private static final String BOOK_ADD_BUTTON_PART = "//android.widget.Button[@content-desc=\"%1$s\"]";

    private static final String BOOKS_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\"]";
    private static final String BOOK_BLOCK_BY_TITLE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\" "
            + "and .//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleTitle\" and contains(@text, '%1$s')]]";

    private static final String BOOK_IMAGE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleCover\"]";
    private static final String BOOK_TITLE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleTitle\"]";
    private static final String BOOK_AUTHOR_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleAuthor\"]";
    private static final String BOOK_TYPE_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleMeta\"]";
    private static final String BOOK_ADD_BUTTON_LOC = "//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdleButtons\"]"
            + "/android.widget.Button[@content-desc=\"%1$s\"]";

    private final IButton btnReserve =
            getElementFactory().getButton(
                    By.xpath(String.format(BOOK_ADD_BUTTON_PART, AndroidBookAddButtonKeys.RESERVE.getKey())),
                    "Button reserve");
    private final ILabel bookBlockWithReserve =
            getElementFactory().getLabel(
                    By.xpath(String.format("//*[@resource-id=\"org.nypl.simplified.simplye:id/bookCellIdle\" "
                                    + "and .//android.widget.Button[@content-desc=\"%1$s\"]]",
                            AndroidBookAddButtonKeys.RESERVE.getKey())),
                    "Book jacket with reserve button");

    private final ILabel lblFirstFoundBook = getElementFactory().getLabel(
            By.xpath(BOOKS_LOC), "First found book");

    public AndroidCatalogBooksScreen() {
        super(By.xpath(MAIN_ELEMENT));
    }

    @Override
    public void selectFirstFoundBook() {
        lblFirstFoundBook.click();
    }

    private List<ILabel> getFoundBooks() {
        return getElementFactory().findElements(By.xpath(BOOKS_LOC), ElementType.LABEL);
    }

    @Override
    public int getFoundBooksCount() {
        return getFoundBooks().size();
    }

    @Override
    public AndroidCatalogBookModel getBookInfo(final String title) {
        final AndroidCatalogBookModel androidCatalogBookModel = new AndroidCatalogBookModel();
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, title);

        androidCatalogBookModel
                .setImageTitle(Objects.requireNonNull(
                        getElementFactory().getLabel(By.xpath(blockLoc + BOOK_IMAGE_LOC),
                                "Book image content description").getAttribute(AndroidAttributes.CONTENT_DESC)
                ))
                .setTitle(Objects.requireNonNull(
                        getElementFactory().getLabel(By.xpath(blockLoc + BOOK_TITLE_LOC), "Book title")
                                .getText()
                ))
                .setAuthor(Objects.requireNonNull(
                        getElementFactory().getLabel(By.xpath(blockLoc + BOOK_AUTHOR_LOC), "Book author")
                                .getText()
                ))
                .setBookType(Objects.requireNonNull(
                        getElementFactory().getLabel(By.xpath(blockLoc + BOOK_TYPE_LOC), "Book type")
                                .getText()
                ));
        return androidCatalogBookModel;
    }


    @Override
    public String reserveBook() {
        btnReserve.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        String reserveBookTitle = bookBlockWithReserve.findChildElement(By.xpath(BOOK_TITLE_LOC), ElementType.LABEL)
                .getText();
        btnReserve.click();
        return reserveBookTitle;
    }


    @Override
    public void cancelReservationOfTheBook(String title) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, title);
        final IButton btnCancel = getElementFactory().getButton(
                By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC,
                        AndroidBookAddButtonKeys.CANCEL.getKey())), "Book cancel button");
        clickOnTheBookAddBtn(btnCancel);
    }

    @Override
    public void openBookDetailsForReserve() {
        clickOnTheBookAddBtn(bookBlockWithReserve);
    }

    @Override
    public boolean isBookAddButtonTextEqualTo(String bookTitle, AndroidBookAddButtonKeys key) {
        final String blockLoc = String.format(BOOK_BLOCK_BY_TITLE_LOC, bookTitle);
        final IButton bookAddBtn = getElementFactory().getButton(
                By.xpath(blockLoc + String.format(BOOK_ADD_BUTTON_LOC, key.getKey())),
                String.format("Book %1$s button", key.getKey()));
        return bookAddBtn.state().waitForDisplayed();
    }

    private void clickOnTheBookAddBtn(IElement bookWithSpecificAddBtn) {
        bookWithSpecificAddBtn.getTouchActions().scrollToElement(SwipeDirection.DOWN);
        bookWithSpecificAddBtn.click();
    }
}
