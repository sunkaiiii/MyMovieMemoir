package com.example.mymoviememoir.network;

import java.util.List;

public interface RestfulRequestModel {
    List<String> getPathParameter();
    String getBodyParameterJson();
}
