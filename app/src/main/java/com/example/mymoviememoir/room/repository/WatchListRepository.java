package com.example.mymoviememoir.room.repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mymoviememoir.room.dao.WatchListDAO;
import com.example.mymoviememoir.room.database.WatchListDatabase;
import com.example.mymoviememoir.room.entity.WatchList;

import java.util.List;

public class WatchListRepository {
    private WatchListDAO dao;
    private LiveData<List<WatchList>> allWatchList;
    private WatchList watchList;

    public WatchListRepository(Application application) {
        WatchListDatabase db = WatchListDatabase.getInstance(application);
        dao = db.watchListDAO();
    }

    public LiveData<List<WatchList>> getAllWatchList() {
        return dao.getAll();
    }

    public void insert(final WatchList watchList) {
        WatchListDatabase.databseWriteExecutor.execute(() -> {
            dao.insert(watchList);
        });
    }

    public void deleteAll() {
        WatchListDatabase.databseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }

    public void delete(final WatchList watchList) {
        WatchListDatabase.databseWriteExecutor.execute(() -> {
            dao.delete(watchList);
        });
    }

    public void insertAll(final WatchList... watchLists) {
        WatchListDatabase.databseWriteExecutor.execute(() -> {
            dao.insertAll(watchLists);
        });
    }

    public void updateWatchLists(final WatchList... watchLists) {
        WatchListDatabase.databseWriteExecutor.execute(() -> {
            dao.updateWatchLists(watchLists);
        });
    }

    public void findByID(final int movieId, OnFindSuccessListener<WatchList> listener) {
        final Handler handler = new Handler(Looper.getMainLooper());
        WatchListDatabase.databseWriteExecutor.execute(() -> {
            watchList = dao.findById(movieId);
            handler.post(() -> listener.onSuccess(watchList));
        });

    }

    public interface OnFindSuccessListener<T> {
        void onSuccess(T t);
    }
}
