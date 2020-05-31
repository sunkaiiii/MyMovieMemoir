package com.example.mymoviememoir.network.request.base;

import com.example.mymoviememoir.network.interfaces.RestfulPostModel;
import com.example.mymoviememoir.utils.GsonUtils;

public class BasePostModel implements RestfulPostModel {
    @Override
    public String getBodyParameterJson() {
        return GsonUtils.toJson(this);
    }
}
