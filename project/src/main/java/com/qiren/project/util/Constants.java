package com.qiren.project.util;

import java.net.URL;

public class Constants {

    public static final URL PROPERTY_FILE =
            Constants.class.getClassLoader().getResource("database.my");

    public static String DATABASE_URL = "";
    public static String DATABASE_USERNAME = "";
    public static String DATABASE_PASSWORD = "";


}
