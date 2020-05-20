package com.example.mymoviememoir.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymoviememoir.network.DefaultRequestHttpAction;
import com.example.mymoviememoir.network.RequestHelper;

/**
 * @author sunkai
 */
public abstract class BaseRequestRestfulServiceActivity extends AppCompatActivity implements DefaultRequestHttpAction {

    @Override
    public void preExecute(RequestHelper helper) {

    }

    @Override
    public void onExecuteFailed(RequestHelper helper, String message, Exception ex) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute(RequestHelper helper, String response) {
    }

}
