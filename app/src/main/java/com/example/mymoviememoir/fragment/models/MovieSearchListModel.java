package com.example.mymoviememoir.fragment.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.network.reponse.MovieSearchListItem;

import java.util.List;

public class MovieSearchListModel extends ViewModel {
    private MutableLiveData<List<MovieSearchListItem>> movies;

    public MovieSearchListModel() {
        movies = new MutableLiveData<>();
    }

    public void setMovies(List<MovieSearchListItem> movies) {
        this.movies.setValue(movies);
    }

    public LiveData<List<MovieSearchListItem>> getMovies() {
        return movies;
    }
}
