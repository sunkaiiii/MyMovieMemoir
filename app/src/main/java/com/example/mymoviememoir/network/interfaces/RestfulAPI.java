package com.example.mymoviememoir.network.interfaces;

import com.example.mymoviememoir.network.RequestHost;
import com.example.mymoviememoir.network.RequestType;

/**
 * To get the full information of a restful api, this interface should be implemented.
 */
public interface RestfulAPI {
    String getRequestName();
    String getUrl();
    RequestType getRequestType();
    RequestHost getRequestHost();
}
