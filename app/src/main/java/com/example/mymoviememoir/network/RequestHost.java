package com.example.mymoviememoir.network;

/**
 * @author sunkai
 */

public enum RequestHost {
    LOCAL_HOST("moviememoir.duckylife.net", OkHttpNetworkConnection.SCHEME_HTTP, 8080),
    MOVIE_DB_HOST("api.themoviedb.org", OkHttpNetworkConnection.SCHEME_HTTPS, 443),
    MOVIE_DB_IMAGE_HOST("https://image.tmdb.org/t/p/w500", OkHttpNetworkConnection.SCHEME_HTTPS, 443),
    GOOGLE_MAPS("maps.googleapis.com", OkHttpNetworkConnection.SCHEME_HTTPS, 443),
    TWITTER("api.twitter.com", OkHttpNetworkConnection.SCHEME_HTTPS, 443),
    ;
    private String hostUrl;
    private int port;
    private String scheme;


    RequestHost(String url, String scheme, int port) {
        hostUrl = url;
        this.scheme = scheme;
        this.port = port;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public int getPort() {
        return port;
    }

    public String getScheme() {
        return scheme;
    }
}
