package com.example.mymoviememoir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.entities.Credentials;
import com.example.mymoviememoir.entities.Person;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.network.request.SignInRequest;
import com.example.mymoviememoir.utils.CredentialInfoUtils;
import com.example.mymoviememoir.utils.GsonUtils;
import com.example.mymoviememoir.utils.PasswordUtils;
import com.example.mymoviememoir.utils.PersonInfoUtils;
import com.example.mymoviememoir.utils.Values;
import com.google.android.material.textfield.TextInputEditText;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;

public class SignInActivity extends BaseRequestRestfulServiceActivity implements View.OnClickListener {

    private static final int SIGN_UP = 1;
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
            default:
                break;
        }
    }

    private void requestSignIn() {
        if (!validation()) {
            return;
        }
        final String password;
        try {
            password = PasswordUtils.getHashedPassword(ePassword.getText().toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Toast.makeText(this, "an error happens during hashing the password", Toast.LENGTH_SHORT).show();
            return;
        }
        SignInRequest signInRequest = new SignInRequest(eUsername.getText().toString(), password);
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
                    if (TextUtils.isEmpty(response)) {
                        Toast.makeText(this, "username or password is error, please try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Credentials credentials = GsonUtils.fromJsonToObject(response, Credentials.class);
                    CredentialInfoUtils.setInstance(this, credentials);
                    getPersonInformation(credentials.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case GET_PERSON_INFORMATIOIN:
                try {
                    Person personInformation = GsonUtils.fromJsonToObject(response, Person.class);
                    PersonInfoUtils.setInstance(this, personInformation);
                    navigateToHomeScreen();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    private void getPersonInformation(int id) {
        requestRestfulService(MyMovieMemoirRestfulAPI.GET_PERSON_INFORMATIOIN, (RestfulGetModel) () -> Collections.singletonList(String.valueOf(id)));
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
