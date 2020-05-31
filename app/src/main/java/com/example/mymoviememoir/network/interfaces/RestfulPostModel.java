package com.example.mymoviememoir.network.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * A data model belong to a Post request should implement this interface
 * @author sunkai
 */
public interface RestfulPostModel extends RestfulBodyModel {
    default List<String> getPathParameter() {
        return new ArrayList<>();
    }
}
