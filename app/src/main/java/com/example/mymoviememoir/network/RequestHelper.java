package com.example.mymoviememoir.network;

public class RequestHelper {
    private MyMovieMemoirRestfulAPI restfulAPI;
    private RestfulGetModel getModel;
    private RestfulPostModel postModel;
    private RestfulPutModel putModel;
    private RestfulDeleteModel deleteModel;

    public RequestHelper(MyMovieMemoirRestfulAPI api, RestfulGetModel model) {
        restfulAPI = api;
        getModel = model;
    }

    public RequestHelper(MyMovieMemoirRestfulAPI api, RestfulPostModel model) {
        restfulAPI = api;
        postModel = model;
    }

    public RequestHelper(MyMovieMemoirRestfulAPI api, RestfulPutModel model) {
        restfulAPI = api;
        putModel = model;
    }

    public RequestHelper(MyMovieMemoirRestfulAPI api, RestfulDeleteModel model) {
        restfulAPI = api;
        deleteModel = model;
    }

    public MyMovieMemoirRestfulAPI getRestfulAPI() {
        return restfulAPI;
    }

    public RestfulParameterModel getPathRequestModel() throws NoSuchTypeOfModelException {
        switch (this.restfulAPI.getRequestType()){
            case GET:
                return getModel;
            case POST:
                return postModel;
            case PUT:
                return putModel;
            case DELETE:
                return deleteModel;
            default:
                throw new NoSuchTypeOfModelException();
        }
    }

    public RestfulBodyModel getBodyRequestModel() throws NoSuchTypeOfModelException{
        switch (restfulAPI.getRequestType()){
            case POST:
                return postModel;
            case PUT:
                return putModel;
            case GET:
            case DELETE:
                return null;
            default:
                throw new NoSuchTypeOfModelException();
        }
    }

    public static class NoSuchTypeOfModelException extends Exception{
        NoSuchTypeOfModelException(){
            super("No such type of model");
        }
    }
}
