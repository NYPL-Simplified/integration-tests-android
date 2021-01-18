package framework.utilities;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class DateUtils {
    public static Duration getDuration(String date) {
        String formattedString = "";
        List<String> parts = Arrays.asList(date.split(":"));
        if (parts.size() > 2) {
            formattedString = "PT" + parts.get(0) + "H" + parts.get(1) + "M" + parts.get(2) + "S";
        } else if (parts.size() > 1) {
            formattedString = "PT" + parts.get(0) + "M" + parts.get(1) + "S";
        }
        return Duration.parse(formattedString);
    }
}
