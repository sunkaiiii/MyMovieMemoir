package com.example.mymoviememoir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.request.SignInRequest;
import com.example.mymoviememoir.network.request.SignUpPersonRequest;
import com.example.mymoviememoir.utils.Values;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class SignInActivity extends BaseRequestRestulServiceActivity implements View.OnClickListener {

    private int SIGN_UP = 1;
    private TextInputEditText eUsername;
    private TextInputEditText ePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_btn:
                startActivityForResult(new Intent(this, SignUpActivity.class), SIGN_UP);
                break;
            case R.id.sign_in_btn:
                requestSignIn();
                break;
        }
    }

    private void requestSignIn() {
        if (!validation()) {
            return;
        }
        SignInRequest signInRequest = new SignInRequest(eUsername.getText().toString(), ePassword.getText().toString());
        requestRestfulService(MyMovieMemoirRestfulAPI.SIGN_IN, signInRequest);
    }

    private boolean validation() {
        boolean success = true;
        if (TextUtils.isEmpty(eUsername.getText())) {
            eUsername.setError("user name cannot be empty");
            success = false;
        }
        if (TextUtils.isEmpty(ePassword.getText())) {
            ePassword.setError("password cannot be empty");
            success = false;
        }
        return success;
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
        super.onPostExecute(helper, response);
        switch (helper.getRestfulAPI()) {
            case SIGN_IN:
                try {
                    SignUpPersonRequest.CredentialsId credentials = new Gson().fromJson(response, SignUpPersonRequest.CredentialsId.class);
                    getSharedPreferences(Values.USER_INFO, MODE_PRIVATE).edit().putString(Values.CREDENTIALS, new Gson().toJson(credentials)).apply();
                    navigateToHomeScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP) {
            if (resultCode == Values.SUCCESS) {
                navigateToHomeScreen();
            }
        }
    }

    private void navigateToHomeScreen() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void initView() {
        eUsername = findViewById(R.id.e_username);
        ePassword = findViewById(R.id.e_password);
        findViewById(R.id.sign_up_btn).setOnClickListener(this);
        findViewById(R.id.sign_in_btn).setOnClickListener(this);
    }
}
