package com.example.mymoviememoir.network.request.twitter;

import android.util.Base64;

import com.example.mymoviememoir.network.RestfulPostModel;
import com.example.mymoviememoir.network.request.base.BasePostModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwitterSessionRequest implements RestfulPostModel {
    private final String apiKey = "";
    private final String privateSecretKey = "";

    @Override
    public String getBodyParameterJson() {
        return "";
    }

    @Override
    public List<String> getPathParameter() {
        return Arrays.asList("oauth2", "token");
    }

    @Override
    public Map<String, String> getQueryGetParameter() {
        final Map<String, String> query = new HashMap<>();
        query.put("grant_type", "client_credentials");
        return query;
    }

    @Override
    public Map<String, String> getHeader() {
        final String keySecret = String.format("%s:%s", apiKey, privateSecretKey);
        final String based64EncodeKey = Base64.encodeToString(keySecret.getBytes(), Base64.NO_WRAP);
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("Authorization", String.format("Basic %s", based64EncodeKey));
        return headers;
    }
}
