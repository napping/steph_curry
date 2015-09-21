package edu.upenn.cis.cis455.webserver.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author brishi
 */
public class Miscellaneous {

    /* This method was copied from StackOverflow.
     * http://stackoverflow.com/questions/7707555/getting-date-in-http-format-in-java
     */
    public static String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        return dateFormat.format(calendar.getTime());
    }
}
