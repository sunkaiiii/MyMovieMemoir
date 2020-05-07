package com.example.mymoviememoir.activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymoviememoir.network.CustomNetworkAsyncTask;
import com.example.mymoviememoir.network.MyMovieMemoirRestfulAPI;
import com.example.mymoviememoir.network.RequestAction;
import com.example.mymoviememoir.network.RequestHelper;
import com.example.mymoviememoir.network.RestfulDeleteModel;
import com.example.mymoviememoir.network.RestfulGetModel;
import com.example.mymoviememoir.network.RestfulPostModel;
import com.example.mymoviememoir.network.RestfulPutModel;

/**
 * @author sunkai
 */
public class BaseRequestRestulServiceActivity extends AppCompatActivity implements RequestAction {

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

    protected void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulGetModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }

    protected void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulPostModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }

    protected void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulPutModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }

    protected void requestRestfulService(MyMovieMemoirRestfulAPI api, RestfulDeleteModel model) {
        new CustomNetworkAsyncTask(new RequestHelper(api, model), this).execute();
    }
}
