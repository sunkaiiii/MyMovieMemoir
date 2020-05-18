package com.example.mymoviememoir.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    public static String getHashedPassword(String password) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte[] result = md.digest();
        return Base64.encodeToString(result, Base64.DEFAULT);
    }
}
