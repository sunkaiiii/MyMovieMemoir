package com.example.mymoviememoir.network;

public class RequestHelper {
    private MyMovieMemoirRestfulAPI restfulAPI;
    private RestfulRequestModel requestModel;

    public RequestHelper(MyMovieMemoirRestfulAPI api, RestfulRequestModel model) {
        restfulAPI = api;
        requestModel = model;
    }

    public MyMovieMemoirRestfulAPI getRestfulAPI() {
        return restfulAPI;
    }

    public RestfulRequestModel getRequestModel() {
        return requestModel;
    }
}
