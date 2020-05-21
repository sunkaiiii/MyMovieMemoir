package com.example.mymoviememoir.network.reponse;

import androidx.annotation.NonNull;

public class SimpleStringResponse {
    private String result;

    public SimpleStringResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;

    }

    @NonNull
    @Override
    public String toString() {
        return result;
    }
}
