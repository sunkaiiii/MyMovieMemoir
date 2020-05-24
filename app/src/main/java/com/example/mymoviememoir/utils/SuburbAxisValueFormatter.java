package com.example.mymoviememoir.utils;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;

public class SuburbAxisValueFormatter extends ValueFormatter {
    List<String> suburbs;

    public SuburbAxisValueFormatter(List<String> suburbs) {
        this.suburbs = suburbs;
    }

    @Override
    public String getFormattedValue(float value) {
        int index = (int) value;
        return suburbs.get(index);
    }
}
