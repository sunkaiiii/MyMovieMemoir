package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.RestfulPostModel;
import com.google.gson.Gson;

public class SignUpCredentialRequest implements RestfulPostModel {
    private String password;
    private String username;

    public SignUpCredentialRequest(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getBodyParameterJson() {
        return new Gson().toJson(this);
    }
}
