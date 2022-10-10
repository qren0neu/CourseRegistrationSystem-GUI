package com.qiren.project.util;

import com.qiren.project.exceptions.PropertyFileNotFoundException;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CoreUtils {

    public static void initDatabaseProperties() {
        // extract property files
        File property = null;
        try {
            property = new File(Constants.PROPERTY_FILE.toURI());
        } catch (URISyntaxException e) {
            ExceptionCenter.handleException(new PropertyFileNotFoundException());
        }
        try {
            if (null == property) {
                return;
            }
            Scanner sc = new Scanner(property);
            // read properties into map
            Map<String, String> propertyMap = new HashMap<>();
            while (sc.hasNextLine()) {
                String l = sc.nextLine();
                String[] mapping = l.split("=");
                if (mapping.length == 2) {
                    String key = mapping[0];
                    String value = mapping[1];
                    propertyMap.put(key, value);
                }
            }
            // store properties into constants
            Constants.DATABASE_URL = propertyMap.get("url");
            Constants.DATABASE_USERNAME = propertyMap.get("username");
            Constants.DATABASE_PASSWORD = propertyMap.get("password");
            sc.close();
        } catch (FileNotFoundException e) {
            ExceptionCenter.handleException(new PropertyFileNotFoundException());
        }
    }

    public static void initExceptionHandlers() {

    }

    public static void showInfoDialog(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorDialog(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, "Warning", JOptionPane.ERROR_MESSAGE);
    }

    public static ImageIcon createImageIcon(Object o, String path) {
        java.net.URL imgURL = o.getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            LoggingCenter.error("Cannot find Icon: " + path);
            return null;
        }
    }

    public static String getTimeInDay() {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (6 < hour && hour < 12)
            return "morning";
        else if (12 <= hour && hour < 18)
            return "Afternoon";
        else
            return "Evening";
    }

    /**
     * check email pattern (follow gmail rules)
     *
     * @param s email string
     * @return matches or not
     */
    public static boolean checkEmail(String s) {
        return s.matches("[a-zA-Z0-9]+\\.?[a-zA-Z0-9]+@[a-zA-Z0-9]{3,67}\\.[a-zA-Z]{2,}");
    }

    /**
     * Check phone pattern:
     * referenced from:
     * <a href="https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number">https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number</a>
     *
     * @param s phone string
     * @return matches or not
     */
    public static boolean checkPhone(String s) {
        return s.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");
    }

    /**
     * separate long string into multiple lines.
     *
     * @param longString original string
     * @return long string in lines
     */
    public static String stringSeperation(String longString) {
        int max = 80;
        if (longString.length() < max) {
            return longString;
        }
        StringBuilder sb = new StringBuilder();
        longString = longString.replaceAll("\\n", "");
        for (int i = max; i < longString.length(); i += max) {
            if (longString.length() - i < max) {
                sb.append(longString.substring(i)).append("\n");
            } else {
                sb.append(longString, i - max, i).append("\n");
            }
        }
        return sb.toString();
    }

    public static String customFormat(String... format) {
        if (format.length == 0) {
            return "";
        }
        if (format.length < 2) {
            return format[0];
        }
        String ori = format[0];
        for (int i = 1; i < format.length; i++) {
            ori = ori.replace("%replace" + i + "%", format[i]);
        }
        return ori;
    }

    public static List<String> getWeekInPeriod(String weekDay,
                                               String startDate,
                                               String toDate,
                                               String fromTime,
                                               String toTime) {
        SimpleDateFormat df = DateManager.getDateFormatter();
        Period weekPeriod = new Period().withWeeks(1);
        DateTime start = new DateTime(startDate);
        DateTime endDate = new DateTime(toDate);
        Interval i = new Interval(start, weekPeriod);
        List<String> weekList = new ArrayList<>();
        int count = 1;
        while (i.getEnd().isBefore(endDate)) {
            String html = """
                <html>
                <b>Week:&nbsp;</b>%replace1%<br>
                <b>WeekDay:&nbsp;</b>%replace2%<br>
                <b>WeekStart:&nbsp;</b>%replace3%<br>
                <b>WeekEnd:&nbsp;</b>%replace4%<br>
                <b>TimePeriod:&nbsp;</b>%replace5%<br>
                </html>
                """;
            html = customFormat(html,
                    count + "",
                    weekDay,
                    df.format(i.getStart().toDate()),
                    df.format(i.getEnd().plusDays(1).toDate()),
                    fromTime + " - " + toTime);
            weekList.add(html);
            i = new Interval(i.getStart().plus(weekPeriod), weekPeriod);
            count ++;
        }
        return weekList;
    }
}
