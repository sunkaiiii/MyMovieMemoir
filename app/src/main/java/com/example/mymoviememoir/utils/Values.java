package com.example.mymoviememoir.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public  class Values {
    public static final int SUCCESS = 0x11;
    public static final String USER_INFO="user_info";
    public static final String PERSON="person";
    public static final String CREDENTIALS="credentials";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static final SimpleDateFormat REQUESTING_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    public static final SimpleDateFormat MAIN_FRAGMENT_DISPLAY_TIME_FORMAT = new SimpleDateFormat("d MMM yyyy  EEE");
}
