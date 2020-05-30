package com.example.mymoviememoir.network.reponse;

import java.util.Date;

public class MovieRatingResponse {
    private String movieName;
    private Date releaseDate;
    private double ratingScore;

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    private String movieImage;

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRatingScore(double ratingScore) {
        this.ratingScore = ratingScore;
    }

    public String getMovieName() {
        return movieName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public double getRatingScore() {
        return ratingScore;
    }
}
