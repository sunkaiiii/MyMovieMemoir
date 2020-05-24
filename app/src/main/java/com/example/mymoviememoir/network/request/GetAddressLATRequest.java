package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.request.base.BaseGoogleRequest;

import java.util.ArrayList;
import java.util.List;

public class GetAddressLATRequest extends BaseGoogleRequest {
    private String address;

    public GetAddressLATRequest(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public List<String> getPathParameter() {
        return new ArrayList<>();
    }
}
