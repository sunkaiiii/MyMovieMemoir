package com.example.mymoviememoir.network.reponse;

import java.util.List;

public class MovieSearchListItem {
    private String overview;
    private String original_language;
    private String original_title;
    private boolean video;
    private String title;
    private List<Integer> genreIds;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private double popularity;
    private double vote_average;
    private int id;
    private boolean adult;
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public String getOriginalLanguage() {
        return original_language;
    }

    public String getOriginalTitle() {
        return original_title;
    }

    public boolean isVideo() {
        return video;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public double getPopularity() {
        return popularity;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public int getId() {
        return id;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getVoteCount() {
        return voteCount;
    }
}