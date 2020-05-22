package com.example.mymoviememoir.utils;

import android.text.TextUtils;

public class Utils {
    public static boolean isBlank(CharSequence c) {
        return c == null || TextUtils.isEmpty(c) || TextUtils.isEmpty(c.toString().trim());
    }
}
