package com.example.mymoviememoir.fragment.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.network.reponse.MoviePerYearResponse;

import java.util.List;

/**
 * @author sunkai
 */
public class MoviePerYearModel extends ViewModel {
    private MutableLiveData<List<MoviePerYearResponse>> movies;

    public MoviePerYearModel() {
        this.movies = new MutableLiveData<>();
    }

    public LiveData<List<MoviePerYearResponse>> getMovies() {
        return movies;
    }

    public void setMovies(List<MoviePerYearResponse> movies) {
        this.movies.setValue(movies);
    }
}
