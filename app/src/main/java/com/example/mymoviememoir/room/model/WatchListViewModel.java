package com.example.mymoviememoir.room.model;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.room.entity.WatchList;
import com.example.mymoviememoir.room.repository.WatchListRepository;

import java.util.List;

public class WatchListViewModel extends ViewModel {
    private WatchListRepository watchListRepository;
    private MutableLiveData<List<WatchList>> watchListData;

    public WatchListViewModel() {
        watchListData = new MutableLiveData<>();
    }

    public void setWatchList(List<WatchList> watchList) {
        watchListData.setValue(watchList);
    }

    public LiveData<List<WatchList>> getAllWatchList() {
        return watchListRepository.getAllWatchList();
    }

    public void initalizeVars(Application application) {
        watchListRepository = new WatchListRepository(application);
    }

    public void insert(WatchList watchList) {
        watchListRepository.insert(watchList);
    }

    public void insertAll(WatchList... watchLists) {
        watchListRepository.insertAll(watchLists);
    }

    public void deleteAll() {
        watchListRepository.deleteAll();
    }

    public void delete(WatchList watchList) {
        watchListRepository.delete(watchList);
    }

    public void update(WatchList... watchLists) {
        watchListRepository.updateWatchLists(watchLists);
    }

    public void findById(String movieId, WatchListRepository.OnFindSuccessListener<WatchList> listener) {
        watchListRepository.findByID(movieId, listener);
    }
}
