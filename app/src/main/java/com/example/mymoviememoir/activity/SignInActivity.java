package com.example.mymoviememoir.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mymoviememoir.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private int SIGN_UP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViewById(R.id.sign_up_btn).setOnClickListener(this);
        findViewById(R.id.sign_in_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_btn:
                startActivityForResult(new Intent(this, SignUpActivity.class), SIGN_UP);
                break;
            case R.id.sign_in_btn:
                startActivity(new Intent(this, HomeActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_UP) {

        }
    }
}
