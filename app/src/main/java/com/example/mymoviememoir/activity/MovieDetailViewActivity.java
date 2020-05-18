package com.example.mymoviememoir.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RequestHost;
import com.example.mymoviememoir.network.reponse.MovieDetailResponse;
import com.example.mymoviememoir.network.request.GetMovieDetailRequest;
import com.example.mymoviememoir.utils.GsonUtils;

public class MovieDetailViewActivity extends BaseRequestRestfulServiceActivity {

    public static final String MOVIE_NAME = "movie_name";
    public static final String RELEASE_DATE = "release_date";
    public static final String ID = "id";

    private ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_view);
        initView();
        int id = getIntent().getIntExtra(ID, -1);
        if (id != -1) {
            requestRestfulService(MyMovieMemoirRestfulAPI.GET_MOVIE_DETAIL, new GetMovieDetailRequest(id));
        }
    }

    private void initView() {
        movieImage = findViewById(R.id.movie_image);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        switch (helper.getRestfulAPI()) {
            case GET_MOVIE_DETAIL:
                try {
                    MovieDetailResponse movieDetailResponse = GsonUtils.fromJsonToObject(response, MovieDetailResponse.class);
                    fillInfoToView(movieDetailResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void fillInfoToView(MovieDetailResponse movieDetailResponse) {
        Glide.with(this).load(RequestHost.MOVIE_DB_IMAGE_HOST.getHostUrl() + movieDetailResponse.getPosterPath()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof BitmapDrawable) {
                    final Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                    findViewById(R.id.main_view).setBackgroundColor(Palette.from(bitmap).generate().getDarkMutedColor(0xffffffff));
                }
                return false;
            }
        }).into(movieImage);
    }
}
