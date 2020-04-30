package com.example.mymoviememoir.network;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpNetworkConnection {

    private final OkHttpClient client;
    private static final String HOST = "127.0.0.1";
    private static final String SCHEME = "http";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final int PORT = 5000;

    private static OkHttpNetworkConnection instance;

    private OkHttpNetworkConnection() {
        client = new OkHttpClient.Builder().build();
    }

    public synchronized static OkHttpNetworkConnection getInstance() {
        if (instance == null) {
            instance = new OkHttpNetworkConnection();
        }
        return instance;
    }

    public String requestRestfulService(RequestHelper helper) throws IOException,NullPointerException {
        final RestfulRequestModel requestModel = helper.getRequestModel();
        final MyMovieMemoirRestfulAPI restfulAPI = helper.getRestfulAPI();
        final List<String> pathParameter = requestModel.getPathParameter();
        final HttpUrl.Builder restfulRequestUrl = new HttpUrl.Builder().scheme(SCHEME).host(HOST).port(PORT);
        for (String path : pathParameter) {
            restfulRequestUrl.addPathSegment(path);
        }
        final Request.Builder requestBuilder = new Request.Builder().url(restfulRequestUrl.build());
        final RequestBody body;
        if (restfulAPI.getRequestType() != RequestType.GET) {
            body = RequestBody.create(requestModel.getBodyParameterJson(),JSON);
        } else {
            body = null;
        }
        switch (restfulAPI.getRequestType()) {
            case GET:
                requestBuilder.get();
                break;
            case POST:
                requestBuilder.post(body);
                break;
            case PUT:
                requestBuilder.put(body);
                break;
            case DELETE:
                requestBuilder.delete(body);
                break;
        }
        final Response response = client.newCall(requestBuilder.build()).execute();
        return response.body().string();
    }
}
