package models.android;

import constants.localization.application.bookdetals.BookDetailsScreenInformationBlockKeys;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class BookDetailsScreenInformationBlockModel {
    public BookDetailsScreenInformationBlockModel() {}

    private BookDetailsScreenInformationBlockKeys key;
    private String value;

    public static BookDetailsScreenInformationBlockModel createBookDetailsScreenInformationBlockModel(
            Map<String, String> entry) {
        return new BookDetailsScreenInformationBlockModel()
                .setKey(BookDetailsScreenInformationBlockKeys.valueOf(entry.get("key")))
                .setValue(entry.get("value"));
    }
}
