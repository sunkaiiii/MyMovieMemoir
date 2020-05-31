package com.example.mymoviememoir.network.request.twitter;

import com.example.mymoviememoir.network.interfaces.RestfulGetModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryTwitterRequest implements RestfulGetModel {
    private String q;
    private String token;

    public QueryTwitterRequest(String q, String token) {
        this.q = q;
        this.token = token;
    }

    public String getQ() {
        return q;
    }

    public String getToken() {
        return token;
    }

    @Override
    public List<String> getPathParameter() {
        return new ArrayList<>();
    }

    @Override
    public Map<String, String> getHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", String.format("Bearer %s", token));
        return map;
    }

    @Override
    public Map<String, String> getQueryGetParameter() {
        Map<String, String> map = new HashMap<>();
        map.put("q", q);
        map.put("lang", "en");
        map.put("result_type", "mixed");
        map.put("count", String.valueOf(10));
        return map;
    }
}
