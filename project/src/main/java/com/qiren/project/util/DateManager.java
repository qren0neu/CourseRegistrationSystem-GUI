package com.qiren.project.util;

import java.text.SimpleDateFormat;

public class DateManager {

    public static SimpleDateFormat getDateFormatter() {
        return InnerClass.format;
    }

    public static SimpleDateFormat getTimeFormatter() {
        return InnerClass.timeFormat;
    }

    private static class InnerClass {
        static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
