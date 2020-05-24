package com.example.mymoviememoir.fragment.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.network.reponse.MovieSuburbResponse;

import java.util.List;

/**
 * @author sunkai
 */
public class MovieSuburbModel extends ViewModel {
    private MutableLiveData<List<MovieSuburbResponse>> mMovieSuburb;

    public MovieSuburbModel() {
        this.mMovieSuburb = new MutableLiveData<>();
    }

    public LiveData<List<MovieSuburbResponse>> getMovieSuburb() {
        return mMovieSuburb;
    }

    public void setMovieSuburb(List<MovieSuburbResponse> mMovieSuburb) {
        this.mMovieSuburb.setValue(mMovieSuburb);
    }
}
