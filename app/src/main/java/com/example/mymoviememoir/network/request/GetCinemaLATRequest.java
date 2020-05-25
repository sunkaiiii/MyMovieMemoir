package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.request.base.BaseGoogleRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunkai
 */
public class GetCinemaLATRequest extends BaseGoogleRequest {
    private String cinemaSuburb;
    private String cinemaName;

    public GetCinemaLATRequest(String cinemaName, String cinemaSuburb) {
        this.cinemaSuburb = cinemaSuburb;
        this.cinemaName = cinemaName;
    }

    public String getCinemaSuburb() {
        return cinemaSuburb;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    @Override
    public Map<String, String> getQueryGetParameter() {
        Map<String, String> map = new HashMap<>();
        map.put("address", String.format("%s+AU", cinemaSuburb));
        map.put("key", getApi_key());
        return map;
    }

    @Override
    public List<String> getPathParameter() {
        return new ArrayList<>();
    }
}
