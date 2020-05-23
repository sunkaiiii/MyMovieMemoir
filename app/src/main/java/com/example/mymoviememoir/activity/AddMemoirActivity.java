package com.example.mymoviememoir.activity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.dialog.AddCinemaDialog;
import com.example.mymoviememoir.entities.Cinema;
import com.example.mymoviememoir.entities.Memoir;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.network.reponse.SimpleStringResponse;
import com.example.mymoviememoir.network.request.AddCinemaRequest;
import com.example.mymoviememoir.utils.ColorUtils;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Utils;
import com.example.mymoviememoir.utils.Values;
import com.example.mymoviememoir.view.RatingSelectView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author sunkai
 */
public class AddMemoirActivity extends BaseRequestRestfulServiceActivity {

    public static final String MOVIE_ID = "movie id";
    public static final String MOVIE_NAME = "movie_name";
    public static final String MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String MOVIE_IMAGE = "movie_image";
    public static final String PUBLIC_RATING = "public rating";
    private View parentView;
    private ImageView movieImage;
    private TextView movieName;
    private TextView movieReleaseDate;
    private RatingSelectView movieRate;
    private Button watchedDate;
    private TextView watchedDateTextview;
    private TextInputLayout textInputLayout;
    private Spinner cinemaNameSpinner;
    private Spinner cinemaSuburbSpinner;
    private EditText movieComment;
    private ImageButton addCinema;
    private Button ok;
    private Toolbar toolbar;
    private Calendar selectedWatchedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memoir);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_ALL_CINEMA_NAME, (RestfulGetModel) ArrayList::new);
    }

    private void initView() {
        parentView = findViewById(R.id.parent_view);
        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        movieReleaseDate = findViewById(R.id.movie_release_date);
        movieRate = findViewById(R.id.movie_rate);
        watchedDate = findViewById(R.id.watched_date);
        watchedDateTextview = findViewById(R.id.watched_date_textview);
        textInputLayout = findViewById(R.id.textInputLayout);
        cinemaNameSpinner = findViewById(R.id.cinema_name_spinner);
        cinemaSuburbSpinner = findViewById(R.id.spinner2);
        movieComment = findViewById(R.id.movie_comment);
        addCinema = findViewById(R.id.add_cinema);
        ok = findViewById(R.id.ok);
        movieName.setText(getIntent().getStringExtra(MOVIE_NAME));
        movieReleaseDate.setText(getIntent().getStringExtra(MOVIE_RELEASE_DATE));
        Glide.with(this).load(getIntent().getStringExtra(MOVIE_IMAGE)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof BitmapDrawable) {
                    final Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                    int color = ColorUtils.getDarkColor(bitmap);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(color);
                    }
                    parentView.setBackgroundColor(color);
                }
                return false;
            }
        }).into(movieImage);
        toolbar = findViewById(R.id.toolbar2);
        toolbar.setTitle("");

        findViewById(R.id.textView8).setOnClickListener(this::showAddCinemaDialog);
        addCinema.setOnClickListener(this::showAddCinemaDialog);
        watchedDate.setOnClickListener(this::showDatePickerDialog);
        ok.setOnClickListener(this::addNewMemoir);
    }

    private void showAddCinemaDialog(View v) {
        AddCinemaDialog dialog = new AddCinemaDialog();
        dialog.setOnAddCinemaSuccessListener(this::onAddCinemaSuccess);
        dialog.show(getSupportFragmentManager(), AddCinemaDialog.class.getName());
    }

    private void showDatePickerDialog(View v) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, this::handleOnDateSelected, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void handleOnDateSelected(View v, int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        selectedWatchedDate = calendar;
        calendar.set(year, month, day);
        watchedDateTextview.setText(Values.SIMPLE_DATE_FORMAT.format(calendar.getTime()));
    }

    private void addNewMemoir(View v) {
        if (Utils.isBlank(movieComment.getText())) {
            showToast("Comment cannot be empty");
            ;
            return;
        }
        if (selectedWatchedDate == null) {
            showToast("Please select a watched date");
            return;
        }
        if (movieRate.getRatingScore() < 0) {
            showToast("Please select a rating score");
            return;
        }
        if (checkSpinnerIsInvalid(cinemaNameSpinner)) {
            showToast("Please select a cinema");
            return;
        }
        if (checkSpinnerIsInvalid(cinemaSuburbSpinner)) {
            showToast("please select a suburb");
        }

        requestRestfulService(MyMovieMemoirRestfulAPI.GET_CINEMA_BY_NAME_AND_SUBURB, (RestfulGetModel) ()
                -> Arrays.asList(cinemaNameSpinner.getSelectedItem().toString(), cinemaSuburbSpinner.getSelectedItem().toString()));
    }

    private void showToast(final String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean checkSpinnerIsInvalid(Spinner s) {
        return (s.getAdapter() == null || s.getAdapter().getCount() == 0 || s.getSelectedItemPosition() == -1);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        try {
            List<SimpleStringResponse> result;
            List<String> adapterData;
            ArrayAdapter<String> arrayAdapter;
            switch (helper.getRestfulAPI()) {
                case GET_ALL_CINEMA_NAME:
                    result = GsonUtils.fromJsonToList(response, SimpleStringResponse.class);
                    adapterData = new ArrayList<>();
                    for (SimpleStringResponse s : result) {
                        adapterData.add(s.toString());
                    }
                    arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, adapterData);
                    cinemaNameSpinner.setVisibility(View.VISIBLE);
                    cinemaNameSpinner.setAdapter(arrayAdapter);
                    cinemaNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            requestRestfulService(MyMovieMemoirRestfulAPI.GET_CINEMA_SUBURB_BY_NAME,
                                    (RestfulGetModel) () -> Collections.singletonList(cinemaNameSpinner.getItemAtPosition(position).toString()));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    break;
                case GET_CINEMA_SUBURB_BY_NAME:
                    result = GsonUtils.fromJsonToList(response, SimpleStringResponse.class);
                    adapterData = new ArrayList<>();
                    for (SimpleStringResponse s : result) {
                        adapterData.add(s.toString());
                    }
                    arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, adapterData);
                    cinemaSuburbSpinner.setVisibility(View.VISIBLE);
                    cinemaSuburbSpinner.setAdapter(arrayAdapter);
                    break;
                case GET_CINEMA_BY_NAME_AND_SUBURB:
                    if (Utils.isBlank(response)) {
                        Toast.makeText(this, "The cinema does not exist, please choose again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Cinema cinema = GsonUtils.fromJsonToObject(response, Cinema.class);
                    Memoir memoir = new Memoir();
                    memoir.setCinemaId(cinema);
                    memoir.setPersonId(PersonInfoUtils.getPersonInstance());
                    memoir.setMovieName(movieName.getText().toString());
                    memoir.setMovieReleaseDate(Values.REQUESTING_FORMAT.format(Values.SIMPLE_DATE_FORMAT_US.parse(movieReleaseDate.getText().toString())));
                    memoir.setRatingScore(movieRate.getRatingScore());
                    memoir.setWatchedDate(Values.REQUESTING_FORMAT.format(selectedWatchedDate.getTime()));
                    memoir.setWatchedTime(Values.REQUESTING_FORMAT.format(selectedWatchedDate.getTime()));
                    memoir.setMemoirComment(movieComment.getText().toString());
                    memoir.setMovieImage(getIntent().getStringExtra(MOVIE_IMAGE));
                    memoir.setMovieId(getIntent().getIntExtra(MOVIE_ID, -1));
                    memoir.setPublicRating(getIntent().getDoubleExtra(PUBLIC_RATING, 0.0));
                    requestRestfulService(MyMovieMemoirRestfulAPI.ADD_MOVIE_MEMOIR, memoir);
                    break;
                case ADD_MOVIE_MEMOIR:
                    Toast.makeText(this, "Add Memoir Successfully!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onAddCinemaSuccess(AddCinemaRequest request) {
        Toast.makeText(this, "Add cinema successfully", Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> nameSpinnerAdapter = (ArrayAdapter<String>) cinemaNameSpinner.getAdapter();
        nameSpinnerAdapter.add(request.getCinemaName());
        ArrayAdapter<String> locationSpinnerAdapter = (ArrayAdapter<String>) cinemaSuburbSpinner.getAdapter();
        locationSpinnerAdapter.add(request.getLocationSuburb());
        cinemaNameSpinner.setSelection(nameSpinnerAdapter.getCount() - 1, true);
        cinemaSuburbSpinner.setSelection(locationSpinnerAdapter.getCount() - 1, true);
    }
}
