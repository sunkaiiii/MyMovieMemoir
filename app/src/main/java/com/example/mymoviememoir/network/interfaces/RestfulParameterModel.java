package com.example.mymoviememoir.network.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RestfulParameterModel {
    List<String> getPathParameter();

    default Map<String, String> getHeader() {
        return new HashMap<>();
    }

    default Map<String, String> getQueryGetParameter() {
        return new HashMap<>();
    }
}
