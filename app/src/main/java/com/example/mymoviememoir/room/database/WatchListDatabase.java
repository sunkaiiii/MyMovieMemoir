package com.example.mymoviememoir.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymoviememoir.room.dao.WatchListDAO;
import com.example.mymoviememoir.room.entity.WatchList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {WatchList.class}, version = 2, exportSchema = false)
public abstract class WatchListDatabase extends RoomDatabase {
    public abstract WatchListDAO watchListDAO();

    private static WatchListDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized WatchListDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                    , WatchListDatabase.class, "WatchListDatabase").fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
