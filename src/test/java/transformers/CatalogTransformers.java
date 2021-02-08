package transformers;

import com.google.inject.Inject;
import io.cucumber.java.DataTableType;
import models.android.BookDetailsScreenInformationBlockModel;

import java.util.Map;

public class CatalogTransformers {

    @Inject
    public CatalogTransformers() {

    }

    @DataTableType
    public BookDetailsScreenInformationBlockModel getBookDetailsScreenInformationBlockModel(
            Map<String, String> entry) {
        return BookDetailsScreenInformationBlockModel.createBookDetailsScreenInformationBlockModel(entry);
    }
}
