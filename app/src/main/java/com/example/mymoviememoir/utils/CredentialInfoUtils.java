package com.example.mymoviememoir.utils;

import android.content.Context;

import com.example.mymoviememoir.network.request.SignUpCredentialRequest;
import com.example.mymoviememoir.network.request.SignUpPersonRequest;

public final class CredentialInfoUtils {
    public static SignUpPersonRequest.CredentialsId Instance;

    public static synchronized SignUpPersonRequest.CredentialsId getCredentialInstance() {
        if (Instance == null) {
            try {
                Instance = GsonUtils.fromJsonToObject(GlobalContext.getInstance().getSharedPreferences(Values.USER_INFO, Context.MODE_PRIVATE).getString(Values.CREDENTIALS, ""), SignUpPersonRequest.CredentialsId.class);
            } catch (Exception e) {
                e.printStackTrace();
                return new SignUpPersonRequest.CredentialsId(0, "", "");
            }
        }
        return Instance;
    }

    public static synchronized void setInstance(SignUpPersonRequest.CredentialsId newInstance){
        Instance=newInstance;
    }

    public static int getId() {
        return getCredentialInstance().getId();
    }
}
