package com.example.mymoviememoir.network;

import java.util.ArrayList;
import java.util.List;

public interface RestfulPostModel extends RestfulBodyModel {
    default List<String> getPathParameter() {
        return new ArrayList<>();
    }
}
