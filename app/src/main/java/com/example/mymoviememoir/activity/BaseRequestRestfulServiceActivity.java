package com.example.mymoviememoir.activity;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymoviememoir.network.interfaces.DefaultRequestHttpAction;
import com.example.mymoviememoir.network.RequestHelper;

/**
 * @author sunkai
 */
public abstract class BaseRequestRestfulServiceActivity extends AppCompatActivity implements DefaultRequestHttpAction {
    /**
     * Provide an uniform api for all activities
     */

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
