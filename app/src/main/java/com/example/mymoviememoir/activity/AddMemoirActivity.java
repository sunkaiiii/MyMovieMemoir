package com.example.mymoviememoir.activity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
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
import com.example.mymoviememoir.entities.Person;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.interfaces.RestfulGetModel;
import com.example.mymoviememoir.network.reponse.SimpleStringResponse;
import com.example.mymoviememoir.network.request.AddCinemaRequest;
import com.example.mymoviememoir.utils.ColorUtils;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Utils;
import com.example.mymoviememoir.utils.Values;
import com.example.mymoviememoir.view.RatingSelectView;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public static final int ADD_MEMOIR = 1;
    public static final int ADD_SUCCESS = 11;
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
    private LocalDateTime selectedWatchedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setBackgroundDrawableResource(R.color.light_half_transparent);
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.add_memoir_transition));
        }
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

            //Change the background colour depended on the image theme colour
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
        final AddCinemaDialog dialog = new AddCinemaDialog();
        dialog.setOnAddCinemaSuccessListener(this::onAddCinemaSuccess);
        dialog.show(getSupportFragmentManager(), AddCinemaDialog.class.getName());
    }

    private void showDatePickerDialog(View v) {
        final LocalDate today = LocalDate.now();
        new DatePickerDialog(this, this::handleOnDateSelected, today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth()).show();
    }

    private void handleOnDateSelected(View v, int year, int month, int day) {
        final LocalDate selectedDate = LocalDate.of(year, month + 1, day);
        this.selectedWatchedDate = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT);
        watchedDateTextview.setText(selectedDate.format(Values.SIMPLE_DATE_FORMAT));
    }

    private void addNewMemoir(View v) {
        /*
        Input validation
         */
        if (Utils.isBlank(movieComment.getText())) {
            showToast("Comment cannot be empty");
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
            return;
        }
        Utils.hideSoftKeyboard(this, getWindow().getDecorView().getWindowToken());
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
                    final Cinema cinema = GsonUtils.fromJsonToObject(response, Cinema.class);
                    final LocalDate releaseDate = LocalDate.from(Values.SIMPLE_DATE_FORMAT_US.parse(movieReleaseDate.getText().toString()));
                    Memoir memoir = new Memoir();
                    memoir.setCinemaId(cinema);
                    memoir.setPersonId(PersonInfoUtils.getPersonInstance().orElse(new Person()));
                    memoir.setMovieName(movieName.getText().toString());
                    memoir.setMovieReleaseDate(Values.REQUESTING_FORMAT.format(LocalDateTime.of(releaseDate,LocalTime.MIDNIGHT)));
                    memoir.setRatingScore(movieRate.getRatingScore());
                    memoir.setWatchedDate(selectedWatchedDate.format(Values.REQUESTING_FORMAT));
                    memoir.setWatchedTime(selectedWatchedDate.format(Values.REQUESTING_FORMAT));
                    memoir.setMemoirComment(movieComment.getText().toString());
                    memoir.setMovieImage(getIntent().getStringExtra(MOVIE_IMAGE));
                    memoir.setMovieId(getIntent().getStringExtra(MOVIE_ID));
                    memoir.setPublicRating(getIntent().getDoubleExtra(PUBLIC_RATING, 0.0));
                    requestRestfulService(MyMovieMemoirRestfulAPI.ADD_MOVIE_MEMOIR, memoir);
                    break;
                case ADD_MOVIE_MEMOIR:
                    Toast.makeText(this, "Add Memoir Successfully!", Toast.LENGTH_SHORT).show();
                    setResult(ADD_SUCCESS);
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
        /*
        When user has successfully added a new cinema
        make sure that the current selection of the spinner is the new cinema.
         */
        cinemaNameSpinner.setSelection(nameSpinnerAdapter.getCount() - 1, true);
        cinemaSuburbSpinner.setSelection(locationSpinnerAdapter.getCount() - 1, true);
    }
}
