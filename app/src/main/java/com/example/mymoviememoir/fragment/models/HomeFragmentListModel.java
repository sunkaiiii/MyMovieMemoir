package com.example.mymoviememoir.fragment.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.network.reponse.MovieRatingResponse;

import java.util.List;

public class HomeFragmentListModel extends ViewModel {
    private MutableLiveData<List<MovieRatingResponse>> mMovies;

    public HomeFragmentListModel(){
        mMovies=new MutableLiveData<>();
    }

    public void setMovies(List<MovieRatingResponse> movies){
        mMovies.setValue(movies);
    }

    public LiveData<List<MovieRatingResponse>> getMovies(){
        return mMovies;
    }
}
