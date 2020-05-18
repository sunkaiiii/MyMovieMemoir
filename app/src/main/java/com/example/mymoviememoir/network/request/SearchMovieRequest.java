package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.request.base.BaseMovieDBRequest;

public class SearchMovieRequest extends BaseMovieDBRequest {
    private String query;
    private int page =1;
    private String language = "en-US";
    private Boolean include_adult=false;
    private String region;
    private Integer year;
    private Integer primary_release_year;


    public void setQuery(String query) {
        this.query = query;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setInclude_adult(Boolean include_adult) {
        this.include_adult = include_adult;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setPrimary_release_year(Integer primary_release_year) {
        this.primary_release_year = primary_release_year;
    }
}
