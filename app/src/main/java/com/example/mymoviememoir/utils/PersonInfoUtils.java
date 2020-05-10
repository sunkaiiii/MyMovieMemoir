package com.example.mymoviememoir.utils;

import android.content.Context;

import com.example.mymoviememoir.network.request.SignUpPersonRequest;
import com.google.gson.Gson;

public final class PersonInfoUtils {
    public static SignUpPersonRequest Instance;

    public static synchronized SignUpPersonRequest getPersonInstance() {
        if (Instance == null) {
            try {
                Instance = GsonUtils.fromJsonToObject(GlobalContext.getInstance().getSharedPreferences(Values.USER_INFO, Context.MODE_PRIVATE).getString(Values.PERSON, ""), SignUpPersonRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
                return new SignUpPersonRequest();
            }
        }
        return Instance;
    }

    public static synchronized void setInstance(SignUpPersonRequest newInstance) {
        Instance = newInstance;
    }
}
