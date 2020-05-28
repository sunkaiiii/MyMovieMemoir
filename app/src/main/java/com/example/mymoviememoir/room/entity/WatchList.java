package com.example.mymoviememoir.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WatchList {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "modie_id")
    public String movieId;
    @ColumnInfo(name = "movie_name")
    public String movieName;
    @ColumnInfo(name = "realse_date")
    public String releaseDate;
    @ColumnInfo(name = "added_date_time")
    public String addedDateTime;
    @ColumnInfo(name = "movie_image")
    public String movieImage;

    public WatchList(String movieId, String movieName, String releaseDate, String addedDateTime, String movieImage) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addedDateTime = addedDateTime;
        this.movieImage = movieImage;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
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

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }
}
