package com.example.mymoviememoir.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

/**
 * @author sunkai
 */
public class SuburbAxisValueFormatter extends ValueFormatter {
    List<String> month;

    public SuburbAxisValueFormatter(List<String> suburbs) {
        this.month = suburbs;
    }

    @Override
    public String getFormattedValue(float value) {
        int index = (int) value;
        if (month != null && index < month.size()) {
            return month.get(index);
        } else {
            return String.valueOf(index);
        }

    }
}
