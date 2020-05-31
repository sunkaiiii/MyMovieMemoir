package com.example.mymoviememoir.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Utils {
    public static boolean isBlank(CharSequence c) {
        return c == null || TextUtils.isEmpty(c) || TextUtils.isEmpty(c.toString().trim());
    }

    /**
     * references on
     * https://stackoverflow.com/questions/49791066/tinting-the-google-map-icon
     *
     * @param source the original bitmap
     * @param color  tinting color
     * @return tint bitmap
     */
    public static Bitmap tintBitmap(Bitmap source, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth() - 1, source.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }


    public static void hideSoftKeyboard(@NonNull Context context, @NonNull IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }
}
