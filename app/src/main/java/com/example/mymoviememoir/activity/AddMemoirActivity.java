package com.example.mymoviememoir.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mymoviememoir.R;

public class AddMemoirActivity extends BaseRequestRestfulServiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memoir);
    }
}
