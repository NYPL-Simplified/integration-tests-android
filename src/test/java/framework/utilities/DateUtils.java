package framework.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date parseTime(String date) throws ParseException {
        return new SimpleDateFormat("HH:mm:ss").parse(date);
    }

    public static Date parseSmallTime(String date) throws ParseException {
        return new SimpleDateFormat("mm:ss").parse(date);
    }
}
