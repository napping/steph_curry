package edu.upenn.cis.cis455.webserver.utils;

import edu.upenn.cis.cis455.webserver.exception.InvalidDateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static Date parseHttpDate(String dateString)
            throws InvalidDateException {

        Date date = null;

        SimpleDateFormat f1 = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss 'GMT'");
        try {
            date = f1.parse(dateString);

        } catch (ParseException e) {

            try {
                SimpleDateFormat f2 = new SimpleDateFormat(
                        "E, dd-MMM-yy HH:mm:ss 'GMT'");
                date = f2.parse(dateString);

            } catch (ParseException e2) {

                try {
                    SimpleDateFormat f3 = new SimpleDateFormat(
                            "EEE MMM dd HH:mm:ss yyyy");

                    date = f3.parse(dateString);

                } catch (ParseException e3) {
                    date = null;
                }
            }
        }

        if (date == null) {
            throw new InvalidDateException("Last modified/unmodified date " +
                    "has an unrecognizable format.");
        }

        return date;
    }
}
