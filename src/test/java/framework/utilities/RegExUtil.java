package framework.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {
    public static Matcher getMatcher(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text);
    }

    public static double getDoubleFromFirstMatchGroup(String text, String regex) {
        Matcher matcher = getMatcher(text, regex);
        return matcher.find() ? Double.parseDouble(matcher.group(1)) : 0;
    }

    public static String getStringFromFirstGroup(String text, String regex) {
        Matcher matcher = getMatcher(text, regex);
        return matcher.find() ? matcher.group(1) : "";
    }

    public static int getIntFromFirstGroup(String text, String regex) {
        Matcher matcher = getMatcher(text, regex);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }
}
