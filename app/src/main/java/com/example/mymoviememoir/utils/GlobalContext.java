package com.example.mymoviememoir.utils;

import android.app.Application;
import android.content.Context;

import com.google.android.libraries.places.api.Places;

/**
 * @author sunkai
 */
public class GlobalContext extends Application {
    private static Application Instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;
        Places.initialize(this,"Your API KEY");
    }

    public static Context getInstance() {
        return Instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Instance = null;
    }
}
