package models.android;

import constants.android.bookdetals.AndroidBookDetailsScreenInformationBlockKeys;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class AndroidBookDetailsScreenInformationBlockModel {
    public AndroidBookDetailsScreenInformationBlockModel() {}

    private AndroidBookDetailsScreenInformationBlockKeys key;
    private String value;

    public static AndroidBookDetailsScreenInformationBlockModel createAndroidBookDetailsScreenInformationBlockModel(
            Map<String, String> entry) {
        return new AndroidBookDetailsScreenInformationBlockModel()
                .setKey(AndroidBookDetailsScreenInformationBlockKeys.valueOf(entry.get("key")))
                .setValue(entry.get("value"));
    }
}
