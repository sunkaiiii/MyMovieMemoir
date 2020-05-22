package com.example.mymoviememoir.utils;

import android.content.Context;

import com.example.mymoviememoir.entities.Person;

/**
 * @author sunkai
 */
public final class PersonInfoUtils {
    public static Person Instance;

    public static synchronized Person getPersonInstance() {
        if (Instance == null) {
            try {
                Instance = GsonUtils.fromJsonToObject(GlobalContext.getInstance().getSharedPreferences(Values.USER_INFO, Context.MODE_PRIVATE).getString(Values.PERSON, ""), Person.class);
            } catch (Exception e) {
                e.printStackTrace();
                return new Person();
            }
        }
        return Instance;
    }

    public static synchronized void setInstance(Person newInstance) {
        Instance = newInstance;
    }
}
