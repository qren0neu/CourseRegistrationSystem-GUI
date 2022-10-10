package com.qiren.project.util;

public class CourseConstants {

    public static final int CURRENT_SUBJECT_COUNT = 3;
    public static final int DEFAULT_SEAT_COUNT = 15;

    public static final String INFORMATION_SYSTEMS = "INFO"; // 0
    public static final String INFORMATION_SYSTEMS_FULLNAME = "Information Systems";
    public static final String DATA_MANAGEMENT = "DAMG"; // 2
    public static final String DATA_MANAGEMENT_FULLNAME = "Data Management";
    public static final String COMPUTER_SYSTEM_ENGINEERING = "CSYE"; // 1
    public static final String COMPUTER_SYSTEM_ENGINEERING_FULLNAME = "Computer Systems Engineering";

    public static String covertStatus(String status) {
        return switch (status) {
            case "t" -> "open";
            case "c" -> "cancelled";
            case "a" -> "closed";
            default -> "open";
        };
    }
}
