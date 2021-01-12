package framework.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date parseTime(String date) throws ParseException {
        return new SimpleDateFormat("HH:mm:ss").parse(date);
    }
}
