package com.example.mymoviememoir.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.palette.graphics.Palette;

public class ColorUtils {

    public static int getDarkColor(Bitmap bitmap) {
        int color = Palette.from(bitmap).generate().getDarkVibrantColor(0xffffffff);
        if (color == 0xffffffff) {
            color = Palette.from(bitmap).generate().getDarkMutedColor(0x00000000);
        }
        return color;
    }

    public static int getLightColor(int color) {
        float[] hsvArray = new float[3];
        Color.colorToHSV(color, hsvArray);
        hsvArray[0] -= 0.25;
        hsvArray[2] += 0.25;
        if (hsvArray[2] >= 1) {
            hsvArray[2] = 1;
        }
        if (hsvArray[0] <= 0) {
            hsvArray[0] = 0;
        }
        return Color.HSVToColor(hsvArray);
    }
}
