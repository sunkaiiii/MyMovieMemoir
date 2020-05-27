package com.example.mymoviememoir.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WatchList {
    @PrimaryKey
    @ColumnInfo(name = "modie_id")
    public int movieId;
    @ColumnInfo(name = "movie_name")
    public String movieName;
    @ColumnInfo(name = "realse_date")
    public String releaseDate;
    @ColumnInfo(name = "added_date_time")
    public String addedDateTime;

    public WatchList(int movieId, String movieName, String releaseDate, String addedDateTime) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addedDateTime = addedDateTime;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAddedDateTime() {
        return addedDateTime;
    }

    public void setAddedDateTime(String addedDateTime) {
        this.addedDateTime = addedDateTime;
    }
}
