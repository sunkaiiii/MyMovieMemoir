package com.example.mymoviememoir.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mymoviememoir.room.entity.WatchList;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WatchListDAO {
    @Query("SELECT * FROM WatchList")
    LiveData<List<WatchList>> getAll();

    @Query("SELECT * FROM WatchList WHERE modie_id=:movieId LIMIT 1")
    WatchList findById(int movieId);

    @Insert
    void insertAll(WatchList... watchLists);

    @Insert
    long insert(WatchList watchList);

    @Delete
    void delete(WatchList watchList);

    @Update(onConflict = REPLACE)
    void updateWatchLists(WatchList... watchLists);

    @Query("DELETE FROM watchlist")
    void deleteAll();
}
