package com.example.mymoviememoir.entities;

import com.google.gson.annotations.SerializedName;

public class Credentials {

    @SerializedName("id")
    private int id;
    @SerializedName("password")
    private String password;
    @SerializedName("signupDate")
    private String signupDate;


    public Credentials(int id, String username, String password) {
        this.password = password;
        this.id = id;
        this.username = username;
    }

    @SerializedName("username")
    private String username;

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}