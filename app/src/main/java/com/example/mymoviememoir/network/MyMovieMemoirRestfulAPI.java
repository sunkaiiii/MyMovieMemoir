package com.example.mymoviememoir.network;

/**
 * @author sunkai
 */

public enum MyMovieMemoirRestfulAPI implements RestfulAPI {
    SIGN_UP_CREDENTIALS("signUpCredentials", "MovieMemoir/webresources/moviememoir.credentials", RequestType.POST),
    SIGN_UP_PERSON("signUpPerson", "MovieMemoir/webresources/moviememoir.person", RequestType.POST),
    ;
    private String name;
    private String url;
    private RequestType requestType;

    MyMovieMemoirRestfulAPI(String name, String url, RequestType requestType) {
        this.name = name;
        this.url = url;
        this.requestType = requestType;
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
}
