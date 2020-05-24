package com.example.mymoviememoir.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.fragment.models.MoviePerYearModel;
import com.example.mymoviememoir.fragment.models.MovieSuburbModel;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.reponse.MoviePerYearResponse;
import com.example.mymoviememoir.network.reponse.MovieSuburbResponse;
import com.example.mymoviememoir.network.request.FindNumberOfPersonWatchedOfAYearRequest;
import com.example.mymoviememoir.network.request.MovieSuburbRequest;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.MyValueFormmater;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.SuburbAxisValueFormatter;
import com.example.mymoviememoir.utils.Values;
import com.example.mymoviememoir.utils.report.BarUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author sunkai
 */
public class ReportFragment extends BaseRequestRestfulServiceFragment {


    private Button startingDateBtn;
    private Button endingDateBtn;
    private TextView startingDateTv;
    private TextView endingDateTv;
    private PieChart pieChart;
    private BarChart barChart;

    private Calendar startingDay;
    private Calendar endingDay;

    private MovieSuburbModel movieSuburbModel;
    private MoviePerYearModel moviePerYearModel;
    private Spinner pieSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        movieSuburbModel = new MovieSuburbModel();
        moviePerYearModel = new MoviePerYearModel();
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view);
    }

    private void initView(View view) {
        startingDateBtn = view.findViewById(R.id.starting_date_btn);
        endingDateBtn = view.findViewById(R.id.ending_date_btn);
        startingDateTv = view.findViewById(R.id.starting_date_tv);
        endingDateTv = view.findViewById(R.id.ending_date_tv);
        pieChart = view.findViewById(R.id.pie_chart);
        barChart = view.findViewById(R.id.bar_chart);
        pieSpinner = view.findViewById(R.id.pie_spinner);
        startingDateBtn.setOnClickListener(this::showDatePickerDialog);
        endingDateBtn.setOnClickListener(this::showDatePickerDialog);
        movieSuburbModel.getMovieSuburb().observe(getViewLifecycleOwner(), this::createBarChart);
        moviePerYearModel.getMovies().observe(getViewLifecycleOwner(), this::createPieChart);
        pieSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                String[] yearArray = getResources().getStringArray(R.array.pie_years);
                String year = yearArray[position];
                FindNumberOfPersonWatchedOfAYearRequest request = new FindNumberOfPersonWatchedOfAYearRequest(PersonInfoUtils.getPersonInstance().getId(), year);
                requestRestfulService(MyMovieMemoirRestfulAPI.FIND_NUMBER_OF_PERSON_WATCHED_MOVIE_OF_A_YEAR, request);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void createBarChart(List<MovieSuburbResponse> movieSuburbResponses) {
        final int count = getSuburbCount(movieSuburbResponses);
        List<BarEntry> values = new ArrayList<>();
        List<String> suburbs = new ArrayList<>();
        int index = 0;
        for (MovieSuburbResponse m : movieSuburbResponses) {
            if (m.getCount() <= 0) {
                continue;
            }
            float percentage = m.getCount() * 1f / count;
            values.add(new BarEntry(index++, percentage));
            suburbs.add(m.getSuburb());
        }
        BarUtils.initBarChart(barChart, new SuburbAxisValueFormatter(suburbs), new MyValueFormmater());
        BarDataSet set;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "The number of watched movie per suburb");
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set);
            BarData data = new BarData(dataSets);
            barChart.setData(data);
            barChart.invalidate();
        }

    }

    private void createPieChart(List<MoviePerYearResponse> moviePerYearResponses) {
        int count = getWatchedCount(moviePerYearResponses);
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (MoviePerYearResponse response : moviePerYearResponses) {
            if (response.getNumber() <= 0) {
                continue;
            }
            entries.add(new PieEntry(response.getNumber() / 1.0f / count));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Watched Movie Per Month");
        PieData data = new PieData(dataSet);
        pieChart.animateXY(500,500);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    private int getSuburbCount(final List<MovieSuburbResponse> movieSuburbResponses) {
        int count = 0;
        for (MovieSuburbResponse m : movieSuburbResponses) {
            count += m.getCount();
        }
        return count;
    }

    private int getWatchedCount(final List<MoviePerYearResponse> moviePerYearResponses) {
        int count = 0;
        for (MoviePerYearResponse response : moviePerYearResponses) {
            count += response.getNumber();
        }
        return count;
    }

    private void showDatePickerDialog(final View v) {
        Calendar calendar = Calendar.getInstance();
        final Context context = getContext();
        if (context == null) {
            return;
        }
        new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            Calendar selectDay = Calendar.getInstance();
            selectDay.set(year, month, dayOfMonth);
            switch (v.getId()) {
                case R.id.starting_date_btn:
                    this.startingDay = selectDay;
                    startingDateTv.setText(Values.SIMPLE_DATE_FORMAT.format(selectDay.getTime()));
                    break;
                case R.id.ending_date_btn:
                    endingDateTv.setText(Values.SIMPLE_DATE_FORMAT.format(selectDay.getTime()));
                    this.endingDay = selectDay;
                    break;
                default:
                    break;
            }
            tryToRequestHttp();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void tryToRequestHttp() {
        if (startingDay == null || endingDay == null) {
            return;
        }
        MovieSuburbRequest movieSuburbRequest = new MovieSuburbRequest(PersonInfoUtils.getPersonInstance().getId(), Values.SIMPLE_DATE_FORMAT.format(startingDay.getTime()), Values.SIMPLE_DATE_FORMAT.format(endingDay.getTime()));
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_NUMBER_OF_CINEMAS_DURING_PERIOD_BY_PERSON_ID, movieSuburbRequest);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        try {
            switch (helper.getRestfulAPI()) {
                case GET_NUMBER_OF_CINEMAS_DURING_PERIOD_BY_PERSON_ID:
                    List<MovieSuburbResponse> movieSuburbResponseList = GsonUtils.fromJsonToList(response, MovieSuburbResponse.class);
                    movieSuburbModel.setMovieSuburb(movieSuburbResponseList);
                    break;
                case FIND_NUMBER_OF_PERSON_WATCHED_MOVIE_OF_A_YEAR:
                    List<MoviePerYearResponse> moviePerYearResponses = GsonUtils.fromJsonToList(response, MoviePerYearResponse.class);
                    moviePerYearModel.setMovies(moviePerYearResponses);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
