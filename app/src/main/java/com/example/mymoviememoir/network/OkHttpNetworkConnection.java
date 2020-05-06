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
    private static final String HOST = "192.168.0.189";
    private static final String SCHEME = "http";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final int PORT = 13219;
    private static final String PATH_SPLITER = "/";
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

    public String requestRestfulService(RequestHelper helper) throws IOException, NullPointerException, RequestHelper.NoSuchTypeOfModelException {
        final RestfulPathParameterModel requestModel = helper.getPathRequestModel();
        final MyMovieMemoirRestfulAPI restfulAPI = helper.getRestfulAPI();
        final List<String> pathParameter = requestModel.getPathParameter();
        final HttpUrl.Builder restfulRequestUrl = new HttpUrl.Builder().scheme(SCHEME).host(HOST).port(PORT);
        for (String url : restfulAPI.getUrl().split(PATH_SPLITER)) {
            restfulRequestUrl.addPathSegment(url);
        }
        for (String path : pathParameter) {
            restfulRequestUrl.addPathSegment(path);
        }
        final Request.Builder requestBuilder = new Request.Builder().url(restfulRequestUrl.build());

        final RequestBody body;
        if (restfulAPI.getRequestType() != RequestType.GET) {
            body = RequestBody.create(helper.getBodyRequestModel().getBodyParameterJson(), JSON);
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
                requestBuilder.delete();
                break;
        }
        final Response response = client.newCall(requestBuilder.build()).execute();
        return response.body().string();
    }
}
