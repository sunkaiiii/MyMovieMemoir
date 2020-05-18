package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.request.base.BaseMovieDBRequest;

import java.util.Collections;
import java.util.List;

public class GetMovieDetailRequest extends BaseMovieDBRequest {
    private int id;
    private String language;

    public GetMovieDetailRequest(int id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public List<String> getPathParameter() {
        return Collections.singletonList(String.valueOf(id));
    }

}
