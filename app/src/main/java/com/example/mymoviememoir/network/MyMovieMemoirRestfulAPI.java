package com.example.mymoviememoir.network;

/**
 * @author sunkai
 */

public enum MyMovieMemoirRestfulAPI implements RestfulAPI {
    CHECK_USER_NAME("checkEmail","MovieMemoir/webresources/moviememoir.credentials/findByCredentialsUsername",RequestType.GET),
    SIGN_UP_CREDENTIALS("signUpCredentials", "MovieMemoir/webresources/moviememoir.credentials/signUpCredentials", RequestType.POST),
    SIGN_UP_PERSON("signUpPerson", "MovieMemoir/webresources/moviememoir.person", RequestType.POST),
    SIGN_IN("signIn","MovieMemoir/webresources/moviememoir.credentials/signIn",RequestType.GET),
    GET_PERSON_INFORMATIOIN("getPersonInformation","MovieMemoir/webresources/moviememoir.person/findByCredentialsID",RequestType.GET),
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
