package constants;

public class RegEx {
    public static final String PAGE_NUMBER_REGEX = "Page (\\d+) of \\d+ (\\((.*)\\))";
    public static final String FONT_SIZE_REGEX = "font-size: (\\d+\\.*\\d*)px";
    public static final String FONT_NAME_REGEX = "font-family: (\\S* \\S*);";
    public static final String FONT_COLOR_REGEX = " color: (\\S* \\S*);";
    public static final String BACKGROUND_COLOR_REGEX = "background-color: (\\S* \\S*);";
    public static final String AUDIO_BOOK_CURRENT_CHAPTER_REGEX = "\\w+?\\s(\\d+)\\s[\\s\\w]+";
    public static final String PDF_CURRENT_PAGE_REGEX = "(\\d+)\\/\\d+";
}
