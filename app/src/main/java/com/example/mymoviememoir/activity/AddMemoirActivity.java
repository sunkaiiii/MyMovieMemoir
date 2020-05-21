package com.example.mymoviememoir.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.network.reponse.SimpleStringResponse;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.view.RatingSelectView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunkai
 */
public class AddMemoirActivity extends BaseRequestRestfulServiceActivity {

    public static final String MOVIE_NAME = "movie_name";
    public static final String MOVIE_RELEASE_DATE = "movie_release_date";
    public static final String MOVIE_IMAGE = "movie_image";
    private ImageView movieImage;
    private TextView movieName;
    private TextView movieReleaseDate;
    private RatingSelectView movieRate;
    private View view;
    private Button watchedDate;
    private TextView watchedDateTextview;
    private TextInputLayout textInputLayout;
    private Spinner cinemaNameSpinner;
    private Spinner cinemaSuburbSpinner;
    private EditText movieComment;
    private ImageButton addCinema;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memoir);
        initView();
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_ALL_CINEMA_NAME, (RestfulGetModel) ArrayList::new);
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_CINEMA_SUBURB, (RestfulGetModel) ArrayList::new);
    }

    private void initView() {
        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        movieReleaseDate = findViewById(R.id.movie_release_date);
        movieRate = findViewById(R.id.movie_rate);
        view = findViewById(R.id.view);
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
        Glide.with(this).load(getIntent().getStringExtra(MOVIE_IMAGE)).into(movieImage);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        try {
            List<SimpleStringResponse> result = GsonUtils.fromJsonToList(response, SimpleStringResponse.class);
            List<String> adapterData = new ArrayList<>();
            for (SimpleStringResponse s : result) {
                adapterData.add(s.toString());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, adapterData);
            switch (helper.getRestfulAPI()) {
                case GET_ALL_CINEMA_NAME:
                    cinemaNameSpinner.setVisibility(View.VISIBLE);
                    cinemaNameSpinner.setAdapter(arrayAdapter);
                case GET_CINEMA_SUBURB:
                    cinemaSuburbSpinner.setVisibility(View.VISIBLE);
                    cinemaSuburbSpinner.setAdapter(arrayAdapter);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
