package com.example.mymoviememoir.network.reponse;

import androidx.annotation.NonNull;

public class SimpleStringResponse {
    private String resultString;

    public SimpleStringResponse(String resultString) {
        this.resultString = resultString;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;

    }

    @NonNull
    @Override
    public String toString() {
        return resultString;
    }
}
