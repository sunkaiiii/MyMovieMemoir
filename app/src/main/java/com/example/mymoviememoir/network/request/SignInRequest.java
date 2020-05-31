package com.example.mymoviememoir.network.request;

import com.example.mymoviememoir.network.interfaces.RestfulGetModel;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunkai
 */
public class SignInRequest implements RestfulGetModel {
    private String username;
    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    public List<String> getPathParameter() {
        return Arrays.asList(username, password);
    }
}
