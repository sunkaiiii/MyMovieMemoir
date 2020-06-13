package com.example.mymoviememoir.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Values {
    public static final int SUCCESS = 0x11;
    public static final String USER_INFO = "user_info";
    public static final String PERSON = "person";
    public static final String CREDENTIALS = "credentials";
    public static final DateTimeFormatter SIMPLE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter SIMPLE_DATE_FORMAT_US = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter REQUESTING_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter RESPONSE_FORMAT = DateTimeFormatter.ISO_INSTANT;
    public static final DateTimeFormatter MAIN_FRAGMENT_DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("d MMM yyyy  EEE");
    public static String TWITTER_SESSION = "";
}
