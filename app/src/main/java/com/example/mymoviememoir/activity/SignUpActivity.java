package com.example.mymoviememoir.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mymoviememoir.R;

public class SignUpActivity extends AppCompatActivity {

    private View birthDayInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
    }

    private void initViews() {
        birthDayInput = findViewById(R.id.birthDayInput);
        birthDayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SignUpActivity.this).setView(R.layout.date_picker_layout).create().show();
            }
        });
    }
}
