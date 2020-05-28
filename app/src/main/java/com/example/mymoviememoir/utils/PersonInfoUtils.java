package com.example.mymoviememoir.utils;

import android.content.Context;

import com.example.mymoviememoir.entities.Person;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author sunkai
 */
public final class PersonInfoUtils {
    public static Person Instance;

    public static synchronized Person getPersonInstance() {
        if (Instance == null) {
            try {
                Instance = GsonUtils.fromJsonToObject(GlobalContext.getInstance().getSharedPreferences(Values.USER_INFO, MODE_PRIVATE).getString(Values.PERSON, ""), Person.class);
            } catch (Exception e) {
                e.printStackTrace();
                return new Person();
            }
        }
        return Instance;
    }

    public static synchronized void setInstance(Context context, Person newInstance) {
        context.getSharedPreferences(Values.USER_INFO, MODE_PRIVATE).edit().putString(Values.PERSON, newInstance.getBodyParameterJson()).apply();
        Instance = newInstance;
    }
}
