package com.qiren.project.util;

import java.util.Date;

public class LoggingCenter {

    public static void error(String errorMessage) {
        log("\u001B[31m", "Error", errorMessage);
    }

    public static void info(String info) {
        log("", "Info", info);
    }

    public static void log(String colorPrefix, String tag, String message) {
//        System.out.println(colorPrefix + DateManager.getTimeFormatter()
//                .format(new Date(System.currentTimeMillis()))
//                + " " + tag + ": " + message);
        if (null != colorPrefix && !colorPrefix.isBlank()) {
            System.err.println(DateManager.getTimeFormatter()
                .format(new Date(System.currentTimeMillis()))
                + " " + tag + ": " + message);
        } else {
            System.out.println(DateManager.getTimeFormatter()
                    .format(new Date(System.currentTimeMillis()))
                    + " " + tag + ": " + message);
        }
    }
}
