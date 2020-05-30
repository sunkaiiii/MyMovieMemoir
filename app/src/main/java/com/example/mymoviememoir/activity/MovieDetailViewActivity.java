package com.example.mymoviememoir.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.adapter.MovieCrewAdapter;
import com.example.mymoviememoir.adapter.MovieDetailCastAdapter;
import com.example.mymoviememoir.adapter.UserAttitudeViewPagerAdapter;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RequestHost;
import com.example.mymoviememoir.network.reponse.MovieCastResponse;
import com.example.mymoviememoir.network.reponse.MovieDetailResponse;
import com.example.mymoviememoir.network.reponse.SearchTwtitterResponse;
import com.example.mymoviememoir.network.reponse.StatusesItem;
import com.example.mymoviememoir.network.request.GetMovieDetailRequest;
import com.example.mymoviememoir.network.request.twitter.QueryTwitterRequest;
import com.example.mymoviememoir.network.request.twitter.TwitterSessionRequest;
import com.example.mymoviememoir.room.entity.WatchList;
import com.example.mymoviememoir.room.model.WatchListViewModel;
import com.example.mymoviememoir.room.repository.WatchListRepository;
import com.example.mymoviememoir.utils.BagOfWordsUtils;
import com.example.mymoviememoir.utils.ColorUtils;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.Utils;
import com.example.mymoviememoir.utils.Values;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MovieDetailViewActivity extends BaseRequestRestfulServiceActivity implements View.OnClickListener {

    public static final String MOVIE_NAME = "movie_name";
    public static final String RELEASE_DATE = "release_date";
    public static final String ID = "id";

    private String id;
    private ImageView movieImage;
    private View mainView;
    private TextView movieName;
    private TextView movieReleaseYear;
    private TextView movieDuration;
    private TextView movieGenre;
    private LinearLayout movieRatingLayout;
    private RecyclerView recyclerView;
    private RecyclerView crewRecyclerView;
    private TextView movieDescription;
    private Toolbar toolbar;
    private FrameLayout addWatchList;
    private FrameLayout addMemoir;
    private TextView productContryAndStatus;

    private MovieDetailResponse movieDetailResponse;
    private ViewPager2 userAttitudeViewPager;
    private TabLayout tabLayout;
    private LinearLayout movieCommentLayout;
    private TextView userAttitude;


    private WatchListViewModel watchListViewModel;
    private TextView addWatchListText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_view);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getStringExtra(ID);
        if (!Utils.isBlank(id)) {
            requestRestfulService(MyMovieMemoirRestfulAPI.GET_MOVIE_DETAIL, new GetMovieDetailRequest(id));
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        movieImage = findViewById(R.id.movie_image);
        mainView = findViewById(R.id.main_view);
        movieName = findViewById(R.id.movie_name);
        movieReleaseYear = findViewById(R.id.movie_release_year);
        movieDuration = findViewById(R.id.movie_duration);
        movieGenre = findViewById(R.id.movie_genre);
        movieRatingLayout = findViewById(R.id.movie_rating_layout);
        recyclerView = findViewById(R.id.recyclerView);
        movieDescription = findViewById(R.id.movie_description);
        addWatchList = findViewById(R.id.add_watch_list);
        addMemoir = findViewById(R.id.add_memoir);
        crewRecyclerView = findViewById(R.id.crew_layout);
        toolbar.setTitle("");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        crewRecyclerView.setLayoutManager(gridLayoutManager);
        addMemoir.setOnClickListener(this);
        addWatchList.setOnClickListener(this);
        productContryAndStatus = findViewById(R.id.product_contry_and_status);
        userAttitudeViewPager = findViewById(R.id.user_attitude_view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        movieCommentLayout = findViewById(R.id.movie_comment_layout);
        userAttitude = findViewById(R.id.user_attitude);
        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(getApplication());
        addWatchListText = findViewById(R.id.add_watch_list_text);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        try {
            switch (helper.getRestfulAPI()) {
                case GET_MOVIE_DETAIL:
                    movieDetailResponse = GsonUtils.fromJsonToObject(response, MovieDetailResponse.class);
                    fillInfoToView(movieDetailResponse);
                    requestCasts();
                    getWatchListInformationFromDatabase(movieDetailResponse);
                    break;
                case GET_MOVIE_CREDITS:
                    MovieCastResponse castResponse = GsonUtils.fromJsonToObject(response, MovieCastResponse.class);
                    recyclerView.setAdapter(new MovieDetailCastAdapter(castResponse.getCast()));
                    crewRecyclerView.setAdapter(new MovieCrewAdapter(castResponse.getCrew().subList(0, 10)));
                    if (!Utils.isBlank(Values.TWITTER_SESSION)) {
                        requestTwitterQuery();
                    } else {
                        requestRestfulService(MyMovieMemoirRestfulAPI.GET_TWITTER_TOKEN, new TwitterSessionRequest());
                    }
                    break;
                case GET_TWITTER_TOKEN:
                    Log.d("twitter_token", response);
                    if (!Utils.isBlank(response)) {
                        JsonObject jsonObject = GsonUtils.fromJsonToObject(response, JsonObject.class);
                        Values.TWITTER_SESSION = jsonObject.get("access_token").getAsString();
                        requestTwitterQuery();
                    }
                    break;
                case SEARCH_TWITTER:
                    Log.d("twitter_searching", response);
                    if (Utils.isBlank(response)) {
                        return;
                    }
                    SearchTwtitterResponse searchTwtitterResponse = GsonUtils.fromJsonToObject(response, SearchTwtitterResponse.class);
                    processText(searchTwtitterResponse);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWatchListInformationFromDatabase(MovieDetailResponse movieDetailResponse) {
        String movieId = movieDetailResponse.getId();
        watchListViewModel.findById(movieId, watchList -> {
            if (watchList != null) {
                addWatchListText.setText("Already In Watch List");
                addWatchList.setEnabled(false);
            } else {
                addWatchList.setOnClickListener((v) -> {
                    watchListViewModel.insert(new WatchList(movieDetailResponse.getId()
                            , movieDetailResponse.getTitle()
                            , movieDetailResponse.getReleaseDate(), Values.SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime())
                            , RequestHost.MOVIE_DB_IMAGE_HOST.getHostUrl() + movieDetailResponse.getPosterPath()));
                    addWatchListText.setText("Already In Watch List");
                    addWatchListText.setOnClickListener(null);
                    addWatchListText.setEnabled(false);
                });
            }

        });
    }


    private void requestTwitterQuery() {
        QueryTwitterRequest queryTwitterRequest = new QueryTwitterRequest(String.format("\"%s\" movie", movieDetailResponse.getTitle()), Values.TWITTER_SESSION);
        requestRestfulService(MyMovieMemoirRestfulAPI.SEARCH_TWITTER, queryTwitterRequest);
    }

    private void requestCasts() {
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_MOVIE_CREDITS, new GetMovieDetailRequest(id) {
            @Override
            public List<String> getPathParameter() {
                List<String> paramter = new ArrayList<>();
                paramter.add(String.valueOf(id));
                paramter.add("credits");
                return paramter;
            }
        });
    }

    private void fillInfoToView(MovieDetailResponse movieDetailResponse) {
        movieName.setText(getIntent().getStringExtra(MOVIE_NAME));
        movieReleaseYear.setText(movieDetailResponse.getReleaseDate());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < movieDetailResponse.getGenres().size(); i++) {
            sb.append(movieDetailResponse.getGenres().get(i).getName());
            if (i != movieDetailResponse.getGenres().size() - 1) {
                sb.append(", ");
            }
        }
        movieGenre.setText(sb.toString());
        int hour = movieDetailResponse.getRuntime() / 60;
        int minute = movieDetailResponse.getRuntime() % 60;
        movieDuration.setText(String.format(Locale.getDefault(), "%dh %dm", hour, minute));
        movieDescription.setText(movieDetailResponse.getOverview());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < movieDetailResponse.getProductionCountries().size(); i++) {
            stringBuilder.append(movieDetailResponse.getProductionCountries().get(i).getName());
            if (i < movieDetailResponse.getProductionCountries().size() - 1) {
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append("\n").append(movieDetailResponse.getStatus());
        productContryAndStatus.setText(stringBuilder.toString());
        Glide.with(this).load(RequestHost.MOVIE_DB_IMAGE_HOST.getHostUrl() + movieDetailResponse.getPosterPath()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof BitmapDrawable) {
                    final Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                    int color = ColorUtils.getDarkColor(bitmap);
                    int lighterColor = ColorUtils.getLightColor(color);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(color);
                        getWindow().setNavigationBarColor(lighterColor);
                    }
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{color, lighterColor});
                    findViewById(R.id.main_view).setBackground(gradientDrawable);
                }
                return false;
            }
        }).into(movieImage);

        double voteAverage = movieDetailResponse.getVoteAverage();
        for (int i = 0; i < 10; i += 2) {
            int sub = (int) Math.round(voteAverage - i);
            ImageView ratingImage = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ratingImage.setLayoutParams(layoutParams);
            if (sub >= 2) {
                ratingImage.setImageResource(R.drawable.baseline_star_white_24);
            } else if (sub == 1) {
                ratingImage.setImageResource(R.drawable.baseline_star_half_white_24);
            } else {
                ratingImage.setImageResource(R.drawable.baseline_star_border_white_24);
            }
            movieRatingLayout.addView(ratingImage);
        }
    }

    private void processText(SearchTwtitterResponse searchTwtitterResponse) {
        BagOfWordsUtils.requestGetPositiveAndNegativeData(this, (positiveWords, negativeWords) -> {
            Map<BagOfWordsUtils.Classification, ArrayList<StatusesItem>> divisionResult = BagOfWordsUtils.makeStringDivision(searchTwtitterResponse.getStatuses(), movieDetailResponse.getTitle(), positiveWords, negativeWords);
            setDividedDataIntoViewPager(divisionResult);
        });
    }

    private void setDividedDataIntoViewPager(Map<BagOfWordsUtils.Classification, ArrayList<StatusesItem>> divisionResult) {
        movieCommentLayout.setVisibility(View.VISIBLE);
        userAttitudeViewPager.setAdapter(new UserAttitudeViewPagerAdapter(getSupportFragmentManager(), getLifecycle(), divisionResult));
        new TabLayoutMediator(tabLayout, userAttitudeViewPager, (tab, position) -> tab.setText(BagOfWordsUtils.Classification.values()[position].toString())).attach();
        BagOfWordsUtils.Classification maxAttituteClass = null;
        for (Map.Entry<BagOfWordsUtils.Classification, ArrayList<StatusesItem>> entry : divisionResult.entrySet()) {
            if (maxAttituteClass == null) {
                maxAttituteClass = entry.getKey();
            } else if (divisionResult.get(maxAttituteClass).size() < entry.getValue().size()) {
                maxAttituteClass = entry.getKey();
            }
        }
        userAttitude.setText(String.format("%s: %s", userAttitude.getText().toString(), maxAttituteClass.toString()));
        int i = 0;
        for (; i < BagOfWordsUtils.Classification.values().length; i++) {
            if (BagOfWordsUtils.Classification.values()[i] == maxAttituteClass) {
                break;
            }
        }
        final int index = i;
        userAttitudeViewPager.post(() -> {
            userAttitudeViewPager.setCurrentItem(index);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_memoir:
                if (movieDetailResponse == null) {
                    return;
                }
                final Intent intent = new Intent(this, AddMemoirActivity.class);
                intent.putExtra(AddMemoirActivity.MOVIE_NAME, movieDetailResponse.getTitle());
                intent.putExtra(AddMemoirActivity.MOVIE_IMAGE, RequestHost.MOVIE_DB_IMAGE_HOST.getHostUrl() + movieDetailResponse.getPosterPath());
                intent.putExtra(AddMemoirActivity.MOVIE_RELEASE_DATE, movieDetailResponse.getReleaseDate());
                intent.putExtra(AddMemoirActivity.MOVIE_ID, id);
                intent.putExtra(AddMemoirActivity.PUBLIC_RATING, movieDetailResponse.getVoteAverage());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, movieImage, "movie_image").toBundle());
                } else {
                    startActivity(intent);
                }
                break;
            case R.id.add_watch_list:
                break;
            default:
                break;
        }
    }

}
