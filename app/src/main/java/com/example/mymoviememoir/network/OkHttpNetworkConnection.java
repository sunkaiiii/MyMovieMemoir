package com.example.mymoviememoir.network;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpNetworkConnection {

    private final OkHttpClient client;
    static final String SCHEME_HTTP = "http";
    static final String SCHEME_HTTPS = "https";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String PATH_SPLITER = "/";
    private static OkHttpNetworkConnection instance;
    private static final int[] HTTP_OK = {200, 201, 202, 203, 204, 205};

    private OkHttpNetworkConnection() {
        client = new OkHttpClient.Builder().build();
    }

    public synchronized static OkHttpNetworkConnection getInstance() {
        if (instance == null) {
            instance = new OkHttpNetworkConnection();
        }
        return instance;
    }

    public String requestRestfulService(RequestHelper helper) throws IOException, NullPointerException, RequestHelper.NoSuchTypeOfModelException, HTTPConnectionErrorException {
        final RestfulParameterModel requestModel = helper.getPathRequestModel();
        final RequestHost requestHost = helper.getRestfulAPI().getRequestHost();
        final MyMovieMemoirRestfulAPI restfulAPI = helper.getRestfulAPI();
        final List<String> pathParameter = requestModel.getPathParameter();
        final HttpUrl.Builder restfulRequestUrl = new HttpUrl.Builder().scheme(requestHost.getScheme()).host(requestHost.getHostUrl()).port(requestHost.getPort());
        for (String url : restfulAPI.getUrl().split(PATH_SPLITER)) {
            restfulRequestUrl.addPathSegment(url);
        }
        for (String path : pathParameter) {
            restfulRequestUrl.addPathSegment(path);
        }
        for (Map.Entry<String, String> queryParameter : requestModel.getQueryGetParameter().entrySet()) {
            restfulRequestUrl.addQueryParameter(queryParameter.getKey(), queryParameter.getValue());
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

        for (Map.Entry<String, String> header : requestModel.getHeader().entrySet()) {
            requestBuilder.addHeader(header.getKey(), header.getValue());
        }
        Log.d("Network Request", helper.getRestfulAPI().getRequestName() + ": " + restfulRequestUrl.build().toString());
        if (body != null) {
            Log.d("Network Request", helper.getBodyRequestModel().getBodyParameterJson());
        }
        final Response response = client.newCall(requestBuilder.build()).execute();
        try {
            if (Arrays.binarySearch(HTTP_OK, response.code()) < 0) {
                throw new HTTPConnectionErrorException(response.code(), response.message(), response.body().string());
            }
            String responseString = response.body().string();
            Log.d("Network Response", responseString);
            return responseString;
        } finally {
            response.body().close();
        }

    }

    public static class HTTPConnectionErrorException extends Exception {
        private String message;
        private int code;
        private String body;

        public HTTPConnectionErrorException(int code, String reason, String body) {
            this.code = code;
            this.message = reason;
            this.body = body;
        }

        @Nullable
        @Override
        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }

        public String getBody() {
            return body;
        }
    }
}
