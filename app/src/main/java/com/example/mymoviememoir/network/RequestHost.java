package com.example.mymoviememoir.network;

public enum RequestHost {
    LOCAL_HOST("192.168.0.11", OkHttpNetworkConnection.SCHEME_HTTP, 8080),
    MOVIE_DB_HOST("developers.themoviedb.org", OkHttpNetworkConnection.SCHEME_HTTPS, 443);
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
