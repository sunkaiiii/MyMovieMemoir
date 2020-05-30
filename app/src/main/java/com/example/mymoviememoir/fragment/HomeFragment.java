package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.adapter.MovieTopRatingInformationAdapter;
import com.example.mymoviememoir.fragment.models.HomeFragmentListModel;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.network.reponse.MovieRatingResponse;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author sunkai
 */
public class HomeFragment extends BaseRequestRestfulServiceFragment {

    private HomeFragmentListModel mMovieListModel;
    private ViewPager2 topMovieList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieListModel = new ViewModelProvider(requireActivity()).get(HomeFragmentListModel.class);
        mMovieListModel.getMovies().observe(getViewLifecycleOwner(), this::setDataInListAdapter);
        getMovieInformation();
    }

    private void getMovieInformation() {
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_USER_RECENT_YEAR_HIGHEST_MOVIE_INFORMATION,
                (RestfulGetModel) () -> Collections.singletonList(String.valueOf(PersonInfoUtils.getPersonInstance().getId())));
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        switch (helper.getRestfulAPI()) {
            case GET_USER_RECENT_YEAR_HIGHEST_MOVIE_INFORMATION:
                try {
                    mMovieListModel.setMovies(GsonUtils.fromJsonToList(response, MovieRatingResponse.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private void setDataInListAdapter(List<MovieRatingResponse> movies) {
        final MovieTopRatingInformationAdapter adapter = new MovieTopRatingInformationAdapter(movies);
        topMovieList.setAdapter(adapter);
        final int pageMarginPx = getContext().getResources().getDimensionPixelOffset(R.dimen.offset);
        final int offsetPx = getContext().getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        /*
         * Page Transform Animation
         * References on
         * https://proandroiddev.com/look-deep-into-viewpager2-13eb8e06e419
         */
        topMovieList.setPageTransformer((page, position) -> {
            final int offset = (int) (position*-(2*offsetPx+pageMarginPx));
            page.setTranslationX(offset);
            page.setTranslationY(Math.abs(position)*250f);
        });
        topMovieList.setOffscreenPageLimit(2);
    }

    private void initView(View view) {
        topMovieList = view.findViewById(R.id.top_movie_list);
    }
}
