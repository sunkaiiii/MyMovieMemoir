package com.example.mymoviememoir.network;

public enum MyMovieMemoirRestfulAPI implements RestfulAPI {
    ;
    private String name;
    private String url;
    private RequestType requestType;

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
}
