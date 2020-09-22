package constants;

public class RegEx {
    public static final String PAGE_NUMBER_REGEX = "Page (\\d+) of \\d+ (\\((.*)\\))";
    public static final String FONT_SIZE_REGEX = "font-size: (\\d+\\.*\\d*)px";
    public static final String FONT_NAME_REGEX = "font-family: (\\S* \\S*);  } </style>";
    public static final String FONT_COLOR_REGEX = "color: (\\S* \\S*);";
    public static final String BACKGROUND_COLOR_REGEX = "background-color: (\\S* \\S*);";
}
