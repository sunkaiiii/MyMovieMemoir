package com.example.mymoviememoir.utils;

import android.content.Context;

import com.example.mymoviememoir.entities.Credentials;

import static android.content.Context.MODE_PRIVATE;

public final class CredentialInfoUtils {
    public static Credentials Instance;

    public static synchronized Credentials getCredentialInstance() {
        if (Instance == null) {
            try {
                Instance = GsonUtils.fromJsonToObject(GlobalContext.getInstance().getSharedPreferences(Values.USER_INFO, MODE_PRIVATE).getString(Values.CREDENTIALS, ""), Credentials.class);
            } catch (Exception e) {
                e.printStackTrace();
                return new Credentials(0, "", "");
            }
        }
        return Instance;
    }

    public static synchronized void setInstance(Context context, Credentials newInstance) {
        context.getSharedPreferences(Values.USER_INFO, MODE_PRIVATE).edit().putString(Values.CREDENTIALS, GsonUtils.toJson(newInstance)).apply();
        Instance = newInstance;
    }

    public static int getId() {
        return getCredentialInstance().getId();
    }
}
