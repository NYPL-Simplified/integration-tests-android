package models.android;

import constants.localization.application.bookdetals.BookDetailsScreenInformationBlockKeys;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class AndroidBookDetailsScreenInformationBlockModel {
    public AndroidBookDetailsScreenInformationBlockModel() {}

    private BookDetailsScreenInformationBlockKeys key;
    private String value;

    public static AndroidBookDetailsScreenInformationBlockModel createAndroidBookDetailsScreenInformationBlockModel(
            Map<String, String> entry) {
        return new AndroidBookDetailsScreenInformationBlockModel()
                .setKey(BookDetailsScreenInformationBlockKeys.valueOf(entry.get("key")))
                .setValue(entry.get("value"));
    }
}
