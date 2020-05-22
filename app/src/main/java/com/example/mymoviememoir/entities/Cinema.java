package com.example.mymoviememoir.entities;

import com.google.gson.annotations.SerializedName;

public class Cinema {

    @SerializedName("id")
    private int id;

    @SerializedName("cinemaName")
    private String cinemaName;

    @SerializedName("locationSuburb")
    private String locationSuburb;

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLocationSuburb(String locationSuburb) {
        this.locationSuburb = locationSuburb;
    }

    public String getLocationSuburb() {
        return locationSuburb;
    }
}