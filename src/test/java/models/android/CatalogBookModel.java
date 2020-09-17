package models.android;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CatalogBookModel {
    public CatalogBookModel() {}

    private String imageTitle;
    private String title;
    private String author;
    private String bookType;

}