package com.example.mymoviememoir.network;

import com.example.mymoviememoir.network.interfaces.RestfulAPI;

/**
 * All the request APIs used in this Application should be added in this Class
 * @author sunkai
 */

public enum MyMovieMemoirRestfulAPI implements RestfulAPI {
    CHECK_USER_NAME("checkEmail", "MovieMemoir/webresources/moviememoir.credentials/findByCredentialsUsername", RequestType.GET),
    SIGN_UP_CREDENTIALS("signUpCredentials", "MovieMemoir/webresources/moviememoir.credentials/signUpCredentials", RequestType.POST),
    SIGN_UP_PERSON("signUpPerson", "MovieMemoir/webresources/moviememoir.person/signUpPerson", RequestType.POST),
    SIGN_IN("signIn", "MovieMemoir/webresources/moviememoir.credentials/signIn", RequestType.GET),
    GET_PERSON_INFORMATIOIN("getPersonInformation", "MovieMemoir/webresources/moviememoir.person/findByCredentialsID", RequestType.GET),
    GET_USER_RECENT_YEAR_HIGHEST_MOVIE_INFORMATION("getUserRecentYearHighestMovies", "MovieMemoir/webresources/moviememoir.memoir/getUserRecentYearHighestMovies", RequestType.GET),
    SEARCH_MOVIE_BY_NAME("searhMovieByName", "3/search/movie", RequestType.GET, RequestHost.MOVIE_DB_HOST),
    GET_MOVIE_DETAIL("getMovieDetail", "3/movie", RequestType.GET, RequestHost.MOVIE_DB_HOST),
    GET_MOVIE_CREDITS("getMovieCredits", "3/movie", RequestType.GET, RequestHost.MOVIE_DB_HOST),
    GET_CINEMA_SUBURB_BY_NAME("getAllCinemaSuburbByName", "MovieMemoir/webresources/moviememoir.cinema/getAllCinemaSuburbByName", RequestType.GET),
    GET_ALL_CINEMA_NAME("getAllCinemaName", "MovieMemoir/webresources/moviememoir.cinema/getAllCinemaName", RequestType.GET),
    ADD_CINEMA("addCinema", "MovieMemoir/webresources/moviememoir.cinema", RequestType.POST),
    GET_CINEMA_BY_NAME_AND_SUBURB("getCinemaByNameAndLocationSuburb", "MovieMemoir/webresources/moviememoir.cinema/getCinemaByNameAndLocationSuburb", RequestType.GET),
    ADD_MOVIE_MEMOIR("addMovieMemoir", "MovieMemoir/webresources/moviememoir.memoir", RequestType.POST),
    GET_MEMOIR_BY_ID("getMemoirById", "/MovieMemoir/webresources/moviememoir.memoir/findByPersonId/", RequestType.GET),
    GET_MEMOIR_BY_ID_ORBER_BY_RATING_SCORE("getMemoirByIdOrderByRatingScore", "/MovieMemoir/webresources/moviememoir.memoir/findByPersonIdOrderByUserRating", RequestType.GET),
    GET_MEMOIR_BY_ID_ORDER_BY_PUBLIC_SCORE("getMemoirByIdOrderByPublicScore", "/MovieMemoir/webresources/moviememoir.memoir/findByPersonIdOrderByPublicRating", RequestType.GET),
    GET_NUMBER_OF_CINEMAS_DURING_PERIOD_BY_PERSON_ID("findNumberOfCinemasDuringAPeridByPersonId", "/MovieMemoir/webresources/moviememoir.cinema/findNumberOfCinemasDuringAPeridByPersonID", RequestType.GET),
    FIND_NUMBER_OF_PERSON_WATCHED_MOVIE_OF_A_YEAR("findNumberOfPersonWatchedOfAYear","/MovieMemoir/webresources/moviememoir.memoir/findNumberOfPersonWatchedOfAYear",RequestType.GET),
    GET_ADDRESS_LAT("getAddressLAT","/maps/api/geocode/json",RequestType.GET,RequestHost.GOOGLE_MAPS),
    GET_ALL_CINEMA("getAllCinema","/MovieMemoir/webresources/moviememoir.cinema",RequestType.GET),
    GET_TWITTER_TOKEN("getTwitterToken","",RequestType.POST,RequestHost.TWITTER),
    SEARCH_TWITTER("searchTwitter","/1.1/search/tweets.json",RequestType.GET,RequestHost.TWITTER),
    ;
    private String name;
    private String url;
    private RequestType requestType;
    private RequestHost requestHost;

    MyMovieMemoirRestfulAPI(String name, String url, RequestType requestType) {
        this(name, url, requestType, RequestHost.LOCAL_HOST);
    }

    MyMovieMemoirRestfulAPI(String name, String url, RequestType requestType, RequestHost requestHost) {
        this.name = name;
        this.url = url;
        this.requestType = requestType;
        this.requestHost = requestHost;
    }

    @Override
    public String getRequestName() {
        return name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public RequestHost getRequestHost() {
        return requestHost;
    }
}
