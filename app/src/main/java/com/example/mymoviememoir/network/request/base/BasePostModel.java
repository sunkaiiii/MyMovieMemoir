package com.example.mymoviememoir.network.request.base;

import com.example.mymoviememoir.network.RestfulPostModel;
import com.example.mymoviememoir.utils.GsonUtils;
import com.google.gson.Gson;

public class BasePostModel implements RestfulPostModel {
    @Override
    public String getBodyParameterJson() {
        return GsonUtils.toJson(this);
    }
}
