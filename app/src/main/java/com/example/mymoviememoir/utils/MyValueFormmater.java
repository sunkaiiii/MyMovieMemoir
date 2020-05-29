package com.example.mymoviememoir.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

/**
 * @author sunkai
 */
public class MyValueFormmater extends ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return String.valueOf((int)value);
    }
}
