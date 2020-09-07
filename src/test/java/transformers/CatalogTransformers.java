package transformers;

import com.google.inject.Inject;
import constants.android.catalog.AndroidBookAddButtonKeys;
import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
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
