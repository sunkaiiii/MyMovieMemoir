package com.example.mymoviememoir.utils.report;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class BarUtils {
    public static void initBarChart(BarChart barChart, ValueFormatter xFormatter, ValueFormatter yFormatter) {
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(xFormatter);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(yFormatter);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);

        barChart.animateY(600);
    }
}
