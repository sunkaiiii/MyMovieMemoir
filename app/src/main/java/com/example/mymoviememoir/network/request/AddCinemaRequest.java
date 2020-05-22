package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.request.base.BasePostModel;

import java.io.Serializable;

public class AddCinemaRequest extends BasePostModel implements Serializable {
    private String cinemaName;
    private String locationSuburb;

    public AddCinemaRequest(String cinemaName, String locationSuburb) {
        this.cinemaName = cinemaName;
        this.locationSuburb = locationSuburb;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getLocationSuburb() {
        return locationSuburb;
    }
}
