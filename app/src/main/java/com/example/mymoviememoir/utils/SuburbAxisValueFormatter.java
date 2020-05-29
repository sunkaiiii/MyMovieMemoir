package com.example.mymoviememoir.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class SuburbAxisValueFormatter extends ValueFormatter {
    List<String> month;

    public SuburbAxisValueFormatter(List<String> suburbs) {
        this.month = suburbs;
    }

    @Override
    public String getFormattedValue(float value) {
        int index = (int) value;
        return month.get(index);
    }
}
