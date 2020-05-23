package com.example.mymoviememoir.entities;

import com.example.mymoviememoir.network.request.base.BasePostModel;
import com.google.gson.annotations.SerializedName;

public class Memoir extends BasePostModel {

    @SerializedName("movieId")
    private int movieId;

    @SerializedName("watchedDate")
    private String watchedDate;

    @SerializedName("movieReleaseDate")
    private String movieReleaseDate;

    @SerializedName("cinemaId")
    private Cinema cinemaId;

    @SerializedName("personId")
    private Person personId;

    @SerializedName("id")
    private int id;

    @SerializedName("movieName")
    private String movieName;

    @SerializedName("memoirComment")
    private String memoirComment;

    @SerializedName("movieImage")
    private String movieImage;

    @SerializedName("ratingScore")
    private double ratingScore;

    @SerializedName("watchedTime")
    private String watchedTime;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setCinemaId(Cinema cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Cinema getCinemaId() {
        return cinemaId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setRatingScore(double ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public double getRatingScore() {
        return ratingScore;
    }

    public void setWatchedTime(String watchedTime) {
        this.watchedTime = watchedTime;
    }

    public String getMemoirComment() {
        return memoirComment;
    }

    public void setMemoirComment(String memoirComment) {
        this.memoirComment = memoirComment;
    }

    public String getWatchedTime() {
        return watchedTime;
    }
}