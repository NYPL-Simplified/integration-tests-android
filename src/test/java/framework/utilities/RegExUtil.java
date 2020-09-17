package framework.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {
    public static Matcher getMatcher(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text);
    }
}
