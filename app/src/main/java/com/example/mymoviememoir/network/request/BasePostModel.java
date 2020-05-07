package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.RestfulPostModel;
import com.google.gson.Gson;

public class BasePostModel implements RestfulPostModel {
    @Override
    public String getBodyParameterJson() {
        return new Gson().toJson(this);
    }
}
