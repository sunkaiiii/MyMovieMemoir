package com.example.mymoviememoir.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.adapter.SearchMovieListAdapter;
import com.example.mymoviememoir.fragment.models.MovieSearchListModel;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.reponse.MovieSearchListItem;
import com.example.mymoviememoir.network.reponse.MovieSearchListResponse;
import com.example.mymoviememoir.network.request.SearchMovieRequest;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.Utils;

import java.util.List;

/**
 * @author sunkai
 */
public class MovieSearchFragment extends BaseRequestRestfulServiceFragment {


    private ImageView searchBtn;
    private EditText searchText;
    private RecyclerView searchMovieList;
    private MovieSearchListModel movieSearchListModel;
    private CardView searchContainerCard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        movieSearchListModel = new MovieSearchListModel();
        initView(view);
        return view;
    }

    private void initView(View view) {
        searchBtn = view.findViewById(R.id.search_btn);
        searchText = view.findViewById(R.id.search_edit_text);
        searchMovieList = view.findViewById(R.id.search_movie_result_list);
        searchContainerCard = view.findViewById(R.id.search_container_view);
        searchBtn.setOnClickListener((v) -> {
            String movieName = searchText.getText().toString();
            SearchMovieRequest searchMovieRequest = new SearchMovieRequest();
            searchMovieRequest.setQuery(movieName);
            requestRestfulService(MyMovieMemoirRestfulAPI.SEARCH_MOVIE_BY_NAME, searchMovieRequest);
            Utils.hideSoftKeyboard(getContext(),getView().getRootView().getWindowToken());
        });
        searchMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                final Animation animation;
                if (dy > 0) {
                    if (searchContainerCard.getVisibility() != View.GONE) {
                        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out);
                        searchContainerCard.setVisibility(View.GONE);
                        searchContainerCard.startAnimation(animation);
                    }
                } else {

                    if (searchContainerCard.getVisibility() != View.VISIBLE) {
                        animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
                        searchContainerCard.setVisibility(View.VISIBLE);
                        searchContainerCard.startAnimation(animation);
                    }
                }

            }
        });
        movieSearchListModel.getMovies().observe(getViewLifecycleOwner(), this::setDataIntoListView);
    }

    private void setDataIntoListView(List<MovieSearchListItem> movies) {
        SearchMovieListAdapter adapter = new SearchMovieListAdapter(movies);
        searchMovieList.setAdapter(adapter);
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        switch (helper.getRestfulAPI()) {
            case SEARCH_MOVIE_BY_NAME:
                try {
                    MovieSearchListResponse responses = GsonUtils.fromJsonToObject(response, MovieSearchListResponse.class);
                    movieSearchListModel.setMovies(responses.getResults());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
