package transformers;

import com.google.inject.Inject;
import io.cucumber.java.DataTableType;
import models.android.AndroidBookDetailsScreenInformationBlockModel;

import java.util.Map;

public class CatalogTransformers {

    @Inject
    public CatalogTransformers() {

    }

    @DataTableType
    public AndroidBookDetailsScreenInformationBlockModel getAndroidBookDetailsScreenInformationBlockModel(
            Map<String, String> entry) {
        return AndroidBookDetailsScreenInformationBlockModel.createAndroidBookDetailsScreenInformationBlockModel(entry);
    }
}
