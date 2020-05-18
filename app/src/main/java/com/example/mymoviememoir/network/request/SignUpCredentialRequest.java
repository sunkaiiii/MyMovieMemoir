package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.request.base.BasePostModel;

public class SignUpCredentialRequest extends BasePostModel {
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
}
