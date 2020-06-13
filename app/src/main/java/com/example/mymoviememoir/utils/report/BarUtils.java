package com.example.mymoviememoir.utils.report;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.reponse.MovieSuburbResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        leftAxis.setGranularity(1f);
        leftAxis.setAxisMinimum(0f);
        barChart.animateY(600);

    }

    public static PieData initPieChart(Context context, List<MovieSuburbResponse> movieSuburbResponses) {
        int count = getWatchedCount(movieSuburbResponses);
        Collections.sort(movieSuburbResponses, (o1, o2) -> o1.getCount() - o2.getCount());
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (MovieSuburbResponse response : movieSuburbResponses) {
            if (response.getCount() <= 0) {
                continue;
            }
            entries.add(new PieEntry(response.getCount() / 1.0f / count,
                    response.getSuburb()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Watched Movie Per Suburb");
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new ValueFormatter() {
            /*
            To make the format of percentage string being xx.xx%, using DecimalFormat class
             */
            @Override
            public String getFormattedValue(float value) {
                DecimalFormat decimalFormat = new DecimalFormat(".00");
                return decimalFormat.format(value * 100) + "%";
            }
        });
        dataSet.setColors(getPieChartColour(context, movieSuburbResponses, count));
        data.setValueTextSize(12f);
        return data;
    }

    private static int getWatchedCount(final List<MovieSuburbResponse> movieSuburbResponses) {
        return (int) movieSuburbResponses.stream().map(MovieSuburbResponse::getCount).count();
    }

    private static List<Integer> getPieChartColour(Context context, final List<MovieSuburbResponse> movieSuburbResponses, final int totalCount) {
        /*
        The colour of each part in the pie chart is decided by the weight which is the number of percentages.
        The percentage is higher, the colour will be darker.
         */
        List<Integer> colours = new ArrayList<>();
        int secondaryLightColor = ContextCompat.getColor(context, R.color.secondaryLightColor);
        int secondaryDarkColor = ContextCompat.getColor(context, R.color.secondaryDarkColor);
        float[] lColor = new float[3];
        float[] dColor = new float[3];
        ColorUtils.colorToHSL(secondaryLightColor, lColor);
        ColorUtils.colorToHSL(secondaryDarkColor, dColor);
        //calculate the distance of luminance of two colours
        final float brightnessGap = lColor[2] - dColor[2];
        dColor[2] = lColor[2];
        for (MovieSuburbResponse response : movieSuburbResponses) {
            if (response.getCount() == 0) {
                continue;
            }
            //ensure the value of luminance of each part.
            colours.add(ColorUtils.HSLToColor(dColor));
            dColor[2] = (dColor[2] - (response.getCount() / 1.0f / totalCount) * brightnessGap);
        }
        return colours;
    }
}
